package com.joalvarez.websocket.data.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.joalvarez.shared.data.dto.general.BaseDTO;
import com.joalvarez.websocket.data.type.MessageType;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatMessage(
	MessageType type,
	String content,
	String sender
) implements BaseDTO {}
