package com.joalvarez.sendemails.data.dto;

import com.joalvarez.shared.data.dto.general.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
	@NotBlank(message = "El destinatario es obligatorio")
	@Email(message = "formato email invalido")
	String to,
	@NotBlank(message = "el asunto es obligatorio")
	String subject,
	@NotBlank(message = "contenido obligatorio")
	String text,
	String htmlContent
) implements BaseDTO {}
