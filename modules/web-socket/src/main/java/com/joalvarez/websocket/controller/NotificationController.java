package com.joalvarez.websocket.controller;

import com.joalvarez.shared.LoggerHelper;
import com.joalvarez.websocket.data.dto.NotificationMessage;
import com.joalvarez.websocket.data.dto.NotificationSubscription;
import com.joalvarez.websocket.data.type.NotificationType;
import com.joalvarez.websocket.service.WebSocketSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController implements LoggerHelper {

	private final WebSocketSessionManager sessionManager;

	public NotificationController(WebSocketSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@MessageMapping("/notification.subscribe")
	@SendToUser("/queue/notifications")
	public NotificationMessage subscribeToNotifications(
		@Payload NotificationSubscription subscription,
		SimpMessageHeaderAccessor headerAccessor
	) {
		String sessionId = headerAccessor.getSessionId();
		String tenantId = subscription.tenantId();
		String userId = subscription.userId();

		this.info("Nueva suscripción a notificaciones: tenantId={}, userId={}, sessionId={}",
				   tenantId, userId, sessionId);

		sessionManager.registerUser(tenantId, userId, sessionId);

		sessionManager.sendPendingNotifications(tenantId, userId);

		return new NotificationMessage(
			tenantId,
			userId,
			NotificationType.SUCCESS,
			"Conectado",
			"Te has suscrito correctamente a las notificaciones"
		);
	}

	@MessageMapping("/notification.test")
	@SendToUser("/queue/notifications")
	public NotificationMessage sendTestNotification(
		@Payload NotificationSubscription subscription
	) {
		this.info("=== INICIO TEST NOTIFICATION ===");
		this.info("Subscription recibida: {}", subscription);
		
		String tenantId = subscription.tenantId();
		String userId = subscription.userId();

		this.info("tenantId: {}, userId: {}", tenantId, userId);
		
		if (tenantId == null || userId == null) {
			this.error("ERROR: tenantId o userId son null!");
			return new NotificationMessage(
				"error",
				"error", 
				NotificationType.ERROR,
				"Error",
				"Datos incompletos"
			);
		}

		NotificationMessage testNotification = new NotificationMessage(
			tenantId,
			userId,
			NotificationType.BELL_NOTIFICATION,
			"🔔 Notificación de Prueba",
			"Esta es una notificación de prueba para verificar que el sistema funciona correctamente",
			"{\"test\": true, \"timestamp\": \"" + System.currentTimeMillis() + "\"}"
		);

		this.info("Enviando notificación: {}", testNotification);
		this.info("=== FIN TEST NOTIFICATION ===");
		
		return testNotification;
	}

	@MessageMapping("/notification.markAsRead")
	public void markNotificationAsRead(
		@Header("tenantId") String tenantId,
		@Header("userId") String userId,
		@Header("notificationId") String notificationId
	) {
		this.info("Marcando notificación como leída: tenantId={}, userId={}, notificationId={}",
				   tenantId, userId, notificationId);
	}

	@MessageMapping("/notification.getCount")
	@SendToUser("/queue/notification-count")
	public Long getPendingNotificationCount(
		@Payload NotificationSubscription subscription
	) {
		String tenantId = subscription.tenantId();
		String userId = subscription.userId();

		this.info("Solicitando contador de notificaciones: tenantId={}, userId={}", tenantId, userId);

		return sessionManager.notificationQueueService.getPendingNotificationCount(tenantId, userId);
	}
}