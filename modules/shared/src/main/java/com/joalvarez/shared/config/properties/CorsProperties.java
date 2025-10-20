package com.joalvarez.shared.config.properties;

import com.joalvarez.shared.data.dto.general.BaseDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "cors-configuration")
//@ConditionalOnProperty("cors-configuration")
public record CorsProperties(
	List<String> origins,
	List<String> methods,
	List<String> headers,
	boolean credentials,
	long maxAge
) implements BaseDTO {}
