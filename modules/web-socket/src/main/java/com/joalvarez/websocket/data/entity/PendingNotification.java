package com.joalvarez.websocket.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pending_notifications")
public class PendingNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tenant_id", nullable = false)
	private String tenantId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "notification_type", nullable = false)
	private String notificationType;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "message", nullable = false, columnDefinition = "TEXT")
	private String message;

	@Column(name = "data", columnDefinition = "TEXT")
	private String data;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "delivered", nullable = false)
	private boolean delivered = false;

	@Column(name = "delivered_at")
	private LocalDateTime deliveredAt;

	public PendingNotification() {
		this.createdAt = LocalDateTime.now();
	}

	public PendingNotification(String tenantId, String userId, String notificationType, 
							  String title, String message, String data) {
		this();
		this.tenantId = tenantId;
		this.userId = userId;
		this.notificationType = notificationType;
		this.title = title;
		this.message = message;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
		if (delivered && this.deliveredAt == null) {
			this.deliveredAt = LocalDateTime.now();
		}
	}

	public LocalDateTime getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(LocalDateTime deliveredAt) {
		this.deliveredAt = deliveredAt;
	}
}