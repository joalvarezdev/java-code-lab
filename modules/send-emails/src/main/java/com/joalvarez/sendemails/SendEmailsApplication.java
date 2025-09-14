package com.joalvarez.sendemails;

import com.joalvarez.shared.config.GlobalConfig;
import com.joalvarez.shared.config.properties.AppBaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = GlobalConfig.BASE_PACKAGE)
@EnableConfigurationProperties({AppBaseProperties.class})
public class SendEmailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendEmailsApplication.class, args);
    }
}