package com.aek56.microservice.auth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        logger.info("token is " + authToken);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        logger.info("checking authentication for user " + username);
        if (username != null && jwtTokenUtil.validateToken(authToken)) {
        	if (SecurityContextHolder.getContext().getAuthentication() == null) {
//        		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        		String redisToken = this.jwtTokenUtil.getUserDetailsString(authToken);
        		Gson gson = new Gson();
        		UserDetails userDetails = gson.fromJson(redisToken, UserDetails.class);
        		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        		logger.info("authenticated user " + username + ", setting security context");
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        	}
        }

        chain.doFilter(request, response);
    }
}