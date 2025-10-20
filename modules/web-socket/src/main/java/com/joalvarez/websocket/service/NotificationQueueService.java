package com.joalvarez.websocket.service;

import com.joalvarez.websocket.data.dto.NotificationMessage;
import com.joalvarez.websocket.data.entity.PendingNotification;
import com.joalvarez.websocket.data.repository.PendingNotificationRepository;
import com.joalvarez.websocket.data.type.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NotificationQueueService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationQueueService.class);

	private final PendingNotificationRepository repository;

	public NotificationQueueService(PendingNotificationRepository repository) {
		this.repository = repository;
	}

	public void addPendingNotification(NotificationMessage message) {
		try {
			PendingNotification entity = new PendingNotification(
				message.tenantId(),
				message.userId(),
				message.type().getValue(),
				message.title(),
				message.message(),
				message.data()
			);
			
			repository.save(entity);
			logger.info("Notificación guardada en cola: tenantId={}, userId={}, type={}", 
					   message.tenantId(), message.userId(), message.type());
		} catch (Exception e) {
			logger.error("Error guardando notificación en cola: tenantId={}, userId={}", 
						message.tenantId(), message.userId(), e);
		}
	}

	@Transactional(readOnly = true)
	public List<NotificationMessage> getPendingNotifications(String tenantId, String userId) {
		try {
			List<PendingNotification> pendingEntities = repository
				.findByTenantIdAndUserIdAndDeliveredFalseOrderByCreatedAtAsc(tenantId, userId);

			List<NotificationMessage> notifications = pendingEntities.stream()
				.map(this::mapToNotificationMessage)
				.toList();

			if (!notifications.isEmpty()) {
				markAsDelivered(tenantId, userId);
			}

			return notifications;
		} catch (Exception e) {
			logger.error("Error obteniendo notificaciones pendientes: tenantId={}, userId={}", 
						tenantId, userId, e);
			return List.of();
		}
	}

	public void markAsDelivered(String tenantId, String userId) {
		try {
			int updatedCount = repository.markAsDelivered(tenantId, userId, LocalDateTime.now());
			logger.info("Marcadas {} notificaciones como entregadas: tenantId={}, userId={}", 
					   updatedCount, tenantId, userId);
		} catch (Exception e) {
			logger.error("Error marcando notificaciones como entregadas: tenantId={}, userId={}", 
						tenantId, userId, e);
		}
	}

	@Transactional(readOnly = true)
	public long getPendingNotificationCount(String tenantId, String userId) {
		try {
			return repository.countPendingByTenantAndUser(tenantId, userId);
		} catch (Exception e) {
			logger.error("Error contando notificaciones pendientes: tenantId={}, userId={}", 
						tenantId, userId, e);
			return 0;
		}
	}

	@Scheduled(cron = "0 0 2 * * ?")
	public void cleanupOldDeliveredNotifications() {
		try {
			LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
			int deletedCount = repository.deleteOldDeliveredNotifications(cutoffDate);
			logger.info("Limpieza de notificaciones antiguas: {} notificaciones eliminadas", deletedCount);
		} catch (Exception e) {
			logger.error("Error en limpieza de notificaciones antiguas", e);
		}
	}

	private NotificationMessage mapToNotificationMessage(PendingNotification entity) {
		NotificationType type = NotificationType.valueOf(entity.getNotificationType().toUpperCase());
		
		return new NotificationMessage(
			entity.getTenantId(),
			entity.getUserId(),
			type,
			entity.getTitle(),
			entity.getMessage(),
			entity.getData(),
			entity.getCreatedAt(),
			true
		);
	}
}