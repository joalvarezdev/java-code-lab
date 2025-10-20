package com.joalvarez.websocket.data.dto;

import com.joalvarez.shared.data.dto.general.BaseDTO;

public record NotificationSubscription(
	String tenantId,
	String userId,
	String sessionId
) implements BaseDTO {}