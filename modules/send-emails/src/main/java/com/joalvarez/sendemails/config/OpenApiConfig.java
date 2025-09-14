package com.joalvarez.sendemails.config;

import com.joalvarez.shared.config.properties.AppBaseProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	private final AppBaseProperties properties;

	public OpenApiConfig(AppBaseProperties properties) {
		this.properties = properties;
	}

	@Bean
	public OpenAPI api() {
		final License license = new License()
			.name("license")
			.url("licenseUrl");

		final Contact contact = new Contact();

		final Info info = new Info()
			.title(this.properties.name())
			.description(this.properties.description())
			.contact(contact)
			.license(license)
			.version(this.properties.version());

		return new OpenAPI()
			.info(info);
	}
}