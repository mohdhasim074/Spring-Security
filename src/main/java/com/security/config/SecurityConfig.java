package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

	@Bean
//	Authentication :UserDetailsService :-
	UserDetailsService detailsService() {
//		for admin
		UserDetails admin = User.withUsername("user").password("admin").roles("ADMIN").build();
//		for  user
		UserDetails user = User.withUsername("user").password("user").roles("USER").build();

//		return : for giving userDetails.
//		we use InMemoryUserDetailsManager to store user details and credentials.
		return new InMemoryUserDetailsManager(admin, user);

	}

//	Authorization :SecurityFilterChain :-

	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/security/home").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/security/**").authenticated().and().formLogin().and()
				.build();

	}

//	for using PasswordEncoder 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
