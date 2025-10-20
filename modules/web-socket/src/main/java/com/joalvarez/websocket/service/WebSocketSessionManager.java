package com.joalvarez.websocket.service;

import com.joalvarez.websocket.data.dto.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionManager {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionManager.class);

	private final SimpMessagingTemplate messagingTemplate;
	public final NotificationQueueService notificationQueueService;

	private final Map<String, Map<String, String>> connectedUsers = new ConcurrentHashMap<>();

	public WebSocketSessionManager(SimpMessagingTemplate messagingTemplate,
								  NotificationQueueService notificationQueueService) {
		this.messagingTemplate = messagingTemplate;
		this.notificationQueueService = notificationQueueService;
	}

	public void registerUser(String tenantId, String userId, String sessionId) {
		connectedUsers.computeIfAbsent(tenantId, k -> new ConcurrentHashMap<>())
					  .put(userId, sessionId);

		logger.info("Usuario registrado: tenantId={}, userId={}, sessionId={}",
				   tenantId, userId, sessionId);
	}

	public void unregisterUser(String tenantId, String userId) {
		Map<String, String> tenantUsers = connectedUsers.get(tenantId);
		if (tenantUsers != null) {
			String removedSessionId = tenantUsers.remove(userId);
			logger.info("Usuario desregistrado: tenantId={}, userId={}, sessionId={}",
					   tenantId, userId, removedSessionId);

			if (tenantUsers.isEmpty()) {
				connectedUsers.remove(tenantId);
			}
		}
	}

	public void unregisterBySessionId(String sessionId) {
		connectedUsers.entrySet().removeIf(tenantEntry -> {
			Map<String, String> tenantUsers = tenantEntry.getValue();
			boolean removed = tenantUsers.entrySet().removeIf(userEntry ->
				userEntry.getValue().equals(sessionId)
			);

			if (removed) {
				logger.info("Usuario desregistrado por sessionId: sessionId={}", sessionId);
			}

			return tenantUsers.isEmpty();
		});
	}

	public boolean isUserConnected(String tenantId, String userId) {
		return connectedUsers.getOrDefault(tenantId, Map.of()).containsKey(userId);
	}

	public void sendNotificationOrQueue(String tenantId, String userId, NotificationMessage message) {
		if (isUserConnected(tenantId, userId)) {
			sendNotificationImmediately(tenantId, userId, message);
		} else {
			notificationQueueService.addPendingNotification(message);
			logger.info("Notificación agregada a cola: tenantId={}, userId={}", tenantId, userId);
		}
	}

	public void sendNotificationImmediately(String tenantId, String userId, NotificationMessage message) {
		try {
			String destination = "/queue/notifications";
			logger.info("Enviando a destination: {} para usuario: {}", destination, userId);
			messagingTemplate.convertAndSendToUser(userId, destination, message);
			logger.info("Notificación enviada inmediatamente: tenantId={}, userId={}", tenantId, userId);
		} catch (Exception e) {
			logger.error("Error enviando notificación inmediata: tenantId={}, userId={}",
						tenantId, userId, e);
			notificationQueueService.addPendingNotification(message);
		}
	}

	public void sendPendingNotifications(String tenantId, String userId) {
		try {
			var pendingNotifications = notificationQueueService.getPendingNotifications(tenantId, userId);

			for (NotificationMessage notification : pendingNotifications) {
				sendNotificationImmediately(tenantId, userId, notification);
			}

			if (!pendingNotifications.isEmpty()) {
				logger.info("Enviadas {} notificaciones pendientes: tenantId={}, userId={}",
						   pendingNotifications.size(), tenantId, userId);
			}
		} catch (Exception e) {
			logger.error("Error enviando notificaciones pendientes: tenantId={}, userId={}",
						tenantId, userId, e);
		}
	}

	public Map<String, Integer> getConnectedUsersCount() {
		return connectedUsers.entrySet().stream()
			.collect(java.util.stream.Collectors.toMap(
				Map.Entry::getKey,
				entry -> entry.getValue().size()
			));
	}

	public int getTotalConnectedUsers() {
		return connectedUsers.values().stream()
			.mapToInt(Map::size)
			.sum();
	}
}