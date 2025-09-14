package com.joalvarez.sendemails.service;

import com.joalvarez.sendemails.data.dto.EmailRequest;
import com.joalvarez.shared.LoggerHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements LoggerHelper {

	private final JavaMailSender sender;

	public EmailService(JavaMailSender sender) {
		this.sender = sender;
	}

	public void sendEmail(EmailRequest request) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(request.to());
		message.setSubject(request.subject());
		message.setText(request.text());

		this.sender.send(message);

	}

	public void sendHtmlContentEmail(EmailRequest request) {

	}

}
