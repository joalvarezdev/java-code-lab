package com.joalvarez.websocket.data.type;

public enum NotificationType {
	CHAT_MESSAGE("chat_message", "💬"),
	USER_JOINED("user_joined", "👋"),
	USER_LEFT("user_left", "👋"),
	SYSTEM_ALERT("system_alert", "⚠️"),
	INFO("info", "ℹ️"),
	SUCCESS("success", "✅"),
	WARNING("warning", "⚠️"),
	ERROR("error", "❌"),
	BELL_NOTIFICATION("bell_notification", "🔔");

	private final String value;
	private final String icon;

	NotificationType(String value, String icon) {
		this.value = value;
		this.icon = icon;
	}

	public String getValue() {
		return value;
	}

	public String getIcon() {
		return icon;
	}
}