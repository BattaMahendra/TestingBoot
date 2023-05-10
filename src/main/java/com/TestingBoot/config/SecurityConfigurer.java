package com.TestingBoot.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
@EnableWebSecurity
public class SecurityConfigurer{
	
	

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

/*
 * Different types of Authentications
 * 1. InMemory Authentication - by hard coding the user details in this configuration class
 * 2. JDBC Authentication - by getting user details from DB
 * 
 */

	/*
	 * This is a simple in memory based authentication
	 * here we hard code credentials.
	 * we can also use next method instead of this alternative
	 * commenting out this code to access next method
	 */
//		@Bean
//		public InMemoryUserDetailsManager ourUserDetailsManager() {
			
//			UserDetails user = CustomUser.withDefaultPasswordEncoder()
//									.username("mahendra")
//									.password("12345")
//									.roles("USER")
//									.build();
//			
//			return new InMemoryUserDetailsManager(user);
			
//		}

	/*
	 * just an alternative of above method , you can use anything.
	 */
//	@Bean
//	public UserDetailsService userDetailsService() throws Exception {

//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//		manager.createUser(CustomUser
//				.withUsername("user")
//				.password(encoder().encode("12345"))
//				.roles("USER").build());
//
//		manager.createUser(CustomUser
//				.withUsername("admin")
//				.password(encoder().encode("12345"))
//				.roles("ADMIN","USER").build());
//
//		return manager;
		
//		return userDetailsService;
		
//	}


	/*
	 * JDBC Authentication
	 */

	@Autowired
	DataSource dataSource;
	
	/**
	 * we can either create a bean or auto wire it
	 * for in memory database I hard coded it and for jdbc security I created 
	 * a java package for specific classes of UserDetailsService and auto wired it from there
	 */
	@Autowired
	UserDetailsService userDetailsService;
	
	
	@Bean
	public PasswordEncoder encoder() {
//		return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}

}
