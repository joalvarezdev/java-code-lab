package com.joalvarez.sendemails.controller;

import com.joalvarez.sendemails.data.dto.EmailRequest;
import com.joalvarez.sendemails.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("emails")
public class EmailController {

	private final EmailService service;

	public EmailController(EmailService service) {
		this.service = service;
	}

	@PostMapping("simple")
	@ResponseStatus(HttpStatus.CREATED)
	public void sendSimpleEmail(@Valid @RequestBody EmailRequest request) {
		this.service.sendEmail(request);
	}

	@PostMapping("html")
	@ResponseStatus(HttpStatus.CREATED)
	public void sendHtmlEmail(@Valid @RequestBody EmailRequest request) {
		this.service.sendHtmlContentEmail(request);
	}

}
