package com.TestingBoot.filter;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.TestingBoot.user.details.CustomUserDetailsService;
import com.TestingBoot.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
 * Here we are creating our own custom filter to process the incoming jwt from the end user
 * We have extended the OncePerRequestFilter of SpringSecurity which makes sure the filter is run for every request.
 * We have provided our implementation to the overridden method doFilterInternal() of the OncePerRequestFilter class.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /*
     * This class used to filter the JWT token sent by the client
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

    	/*
    	 * getting the header from the request sent by user.
    	 */
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        	/*
        	 * JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        	 * using substring method to remove "Bearer "(7 characters) and collect 
        	 * remaining JWT token to further process.
        	 */
            jwt = authorizationHeader.substring(7);
            /*
             * extracting the username from JWT by using JwtUtil class
             */
            username = jwtUtil.extractUsername(jwt);
        }

        /*
         * checks if whether username is null or not and 
         * checks the principal for the present running object. Verifies that there are no 
         * authentication details present in the current security context holder.
         */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        	/*
        	 * getting userdetails from our UserDetailsService using loadUserByUsername method which takes the username
        	 * we got from above method
        	 */
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                /*
                 * setting up the auth token in security context holder
                 */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        /*
         * let the code proceed to the next filter in our filter chain.
         * so that the authentication process continues after this class
         */
        chain.doFilter(request, response);
    }

	

}