package com.joalvarez.shared.config.properties;

import com.joalvarez.shared.data.dto.general.BaseDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppBaseProperties(
	String version,
	String description,
	String name
) implements BaseDTO {}
