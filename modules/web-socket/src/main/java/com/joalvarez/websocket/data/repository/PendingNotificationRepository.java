package com.joalvarez.websocket.data.repository;

import com.joalvarez.websocket.data.entity.PendingNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PendingNotificationRepository extends JpaRepository<PendingNotification, Long> {

	List<PendingNotification> findByTenantIdAndUserIdAndDeliveredFalseOrderByCreatedAtAsc(
		String tenantId, String userId
	);

	@Modifying
	@Query("UPDATE PendingNotification p SET p.delivered = true, p.deliveredAt = :deliveredAt " +
		   "WHERE p.tenantId = :tenantId AND p.userId = :userId AND p.delivered = false")
	int markAsDelivered(@Param("tenantId") String tenantId, 
					   @Param("userId") String userId, 
					   @Param("deliveredAt") LocalDateTime deliveredAt);

	@Query("SELECT COUNT(p) FROM PendingNotification p " +
		   "WHERE p.tenantId = :tenantId AND p.userId = :userId AND p.delivered = false")
	long countPendingByTenantAndUser(@Param("tenantId") String tenantId, 
									@Param("userId") String userId);

	@Modifying
	@Query("DELETE FROM PendingNotification p " +
		   "WHERE p.delivered = true AND p.deliveredAt < :cutoffDate")
	int deleteOldDeliveredNotifications(@Param("cutoffDate") LocalDateTime cutoffDate);

	List<PendingNotification> findByTenantIdAndDeliveredFalse(String tenantId);
}