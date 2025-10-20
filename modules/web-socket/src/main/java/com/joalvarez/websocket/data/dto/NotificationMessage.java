package com.joalvarez.websocket.data.dto;

import com.joalvarez.shared.data.dto.general.BaseDTO;
import com.joalvarez.websocket.data.type.NotificationType;

import java.time.LocalDateTime;

public record NotificationMessage(
	String tenantId,
	String userId,
	NotificationType type,
	String title,
	String message,
	String data,
	LocalDateTime timestamp,
	boolean isUnread
) implements BaseDTO {

	public NotificationMessage(String tenantId, String userId, NotificationType type, 
							  String title, String message) {
		this(tenantId, userId, type, title, message, null, LocalDateTime.now(), true);
	}

	public NotificationMessage(String tenantId, String userId, NotificationType type, 
							  String title, String message, String data) {
		this(tenantId, userId, type, title, message, data, LocalDateTime.now(), true);
	}
}