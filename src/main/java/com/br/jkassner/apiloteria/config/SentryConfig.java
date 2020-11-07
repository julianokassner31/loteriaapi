package com.br.jkassner.apiloteria.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;

@Configuration
public class SentryConfig {

	@Bean
	public SentryClient sentryClient() {
		SentryClient sentryClient = SentryClientFactory
				.sentryClient("https://7e8c09c0df7b473dacc4ea4770643207@sentry.io/1827696");

		return sentryClient;
	}
	
	@Bean
	public HandlerExceptionResolver sentryExceptionResolver() {
	    return new io.sentry.spring.SentryExceptionResolver();
	}
	
	@Bean
	public ServletContextInitializer sentryServletContextInitializer() {
	    return new io.sentry.spring.SentryServletContextInitializer();
	}
}
