package com.commerce.ecommerceapp.filter;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.commerce.ecommerceapp.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer")) {
			
			authHeader = authHeader.substring(7);
			
			//System.out.println(jwtTokenHeader);
			
			Boolean isValidToken = jwtService.verifyJwtToken(authHeader);
			
			System.out.println(isValidToken);
			
			Claims claims = jwtService.getJwtClaim(authHeader);
			
//			System.out.println(claims.get("id"));
//			
//			System.out.println(claims.get("email"));
//			
//			System.out.println(claims.get("name"));
			
			
			if (isValidToken) {
				
				UserDetails userDetails = User.builder().username(claims.get("email").toString()).password("").roles(claims.get("role").toString()).build();
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						
						userDetails,null,userDetails.getAuthorities()
						);
				
				authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	

}
