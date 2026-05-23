package com.shekhar.shopeasy.config;

import com.shekhar.shopeasy.security.CustomAuthenticationEntryPoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	public SecurityConfig(CustomAuthenticationEntryPoint authenticationEntryPoint) {

		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http

				// =========================
				// CORS
				// =========================

				.cors(Customizer.withDefaults())

				// =========================
				// CSRF DISABLE
				// =========================

				.csrf(csrf -> csrf.disable())

				// =========================
				// NO SESSION
				// =========================

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// =========================
				// URL SECURITY
				// =========================

				.authorizeHttpRequests(auth -> auth

						// AUTH API
						.requestMatchers("/api/v1/auth/**").permitAll()

						// HTML PAGES
						.requestMatchers("/", "/register", "/login", "/template/**", "/css/**", "/js/**", "/images/**",
								"/webjars/**")
						.permitAll()

						// H2
						.requestMatchers("/h2-console/**").permitAll()

						// EVERYTHING ELSE
						.anyRequest().authenticated())

				// =========================
				// DISABLE DEFAULT LOGIN FORM
				// =========================

				.formLogin(form -> form.disable())

				// =========================
				// DISABLE HTTP BASIC
				// =========================

				.httpBasic(basic -> basic.disable())

				// =========================
				// EXCEPTION HANDLER
				// =========================

				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint));

		// =========================
		// H2 CONSOLE FIX
		// =========================

		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

		return http.build();
	}
}