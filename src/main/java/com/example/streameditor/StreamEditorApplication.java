package com.example.streameditor;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StreamEditorApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamEditorApplication.class, args);
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("data-public-routes")
				.pathsToMatch("/**")
				.build();
	}

}
