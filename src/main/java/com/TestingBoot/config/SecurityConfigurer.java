package com.TestingBoot.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/*
 * we use this class to configure spring security customization.
 * As WebSecurityConfigurerAdapter is deprecated , instead of extending that class
 *  lets use modern security filter chains 
 */
@Configuration
public class SecurityConfigurer{

	/*
	 * This is a simple in memory based authentication
	 * here we hard code credentials.
	 * we can also use next method instead of this alternative
	 * commenting out this code to access next method
	 */
	//	@Bean
	//	public InMemoryUserDetailsManager ourUserDetailsManager() {
	//		
	//		UserDetails user = User.withDefaultPasswordEncoder()
	//								.username("mahendra")
	//								.password("12345")
	//								.roles("USER")
	//								.build();
	//		
	//		return new InMemoryUserDetailsManager(user);
	//		
	//	}

	@Bean
	public UserDetailsService userDetailsService() throws Exception {

		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

		manager.createUser(User
				.withUsername("user")
				.password(encoder().encode("12345"))
				.roles("USER").build());

		manager.createUser(User
				.withUsername("admin")
				.password(encoder().encode("12345"))
				.roles("ADMIN","USER").build());

		return manager;
	}

	/*
	 * This method defines authorization of all roles.
	 */
	@Bean
	public SecurityFilterChain ourFilterChain(HttpSecurity http) throws Exception {
		http
		/*
		 * this httpBasic() enables to access urls from apps like postman
		 * without this when we hit url in postman it returns HTML login page of spring security
		 */
		.httpBasic()
		.and()
		/*
		 * this securityMatcher allows all end points starting from / to be hit.
		 */
		.securityMatcher(AntPathRequestMatcher.antMatcher("/**"))
		.authorizeHttpRequests((auth) ->
		/*
		 * I have set the roles from least security to highest (general < USER <ADMIN)
		 * When I did reverse it didn't work out , only admin was working
		 * when I put in this order everything worked well.
		 */
		auth.requestMatchers(AntPathRequestMatcher.antMatcher("/app/welcome/**")).permitAll()
			.requestMatchers(AntPathRequestMatcher.antMatcher("/app/g")).hasRole("USER")
			.requestMatchers(AntPathRequestMatcher.antMatcher("/**")).hasRole("ADMIN")
			.anyRequest().authenticated()
			)
		/*
		 * when we hit url from browser this enables us to view login and logout 
		 * pages of spring security
		 */
		.formLogin();
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and()
//		.exceptionHandling()
//		.authenticationEntryPoint(new AuthenticationEntryPoint())
//		.accessDeniedHandler(new AccessDeniedHandlerImpl())
//		.and()
//		.csrf().disable()
//		.build();
		return http.build();
	}



	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
