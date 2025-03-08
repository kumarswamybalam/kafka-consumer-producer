package com.balam.kafka.services.kafkaconsumerproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@Import(RootConfig.class)
@SpringBootApplication
public class KafkaConsumerProducerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		setProfile();
		SpringApplication.run(KafkaConsumerProducerApplication.class, args);
	}

	private static void setProfile() {
		if(System.getProperty("environment") == null || System.getProperty("environment").equals(null)) {
			System.setProperty("environment", "local");
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		setProfile();
		return builder.sources(KafkaConsumerProducerApplication.class);
	}
}
