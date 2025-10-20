package com.joalvarez.shared.config;

import com.joalvarez.shared.config.properties.CorsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@ConditionalOnProperty("cors-configuration")
public class CorsConfig {

	private final CorsProperties properties;

	public CorsConfig(CorsProperties properties) {
		this.properties = properties;
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(this.properties.origins());

		configuration.setAllowedMethods(this.properties.methods());

		configuration.setAllowedHeaders(this.properties.headers());

		configuration.setAllowCredentials(this.properties.credentials());

		configuration.setMaxAge(this.properties.maxAge());

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return new CorsFilter(source);
	}

}
