package com.commerce.ecommerceapp.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.commerce.ecommerceapp.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	/*
	 * generate jwt token
	 * add three dependency jjwt-impl dependency, jjwt-api dependency, jjwt-jackson dependency
	 * with the help of SECRETKEY we have to generate encrypted key 
	 * with help of mail id and id and encrypted key generate a jwt token
	 * decide expairy duration - 24 hrs
	 * send back that token with response body
	 */
	
	@Value("${jwt.secret.key}")
	private String secerKay;
	
	private int JWT_TOKEN_VALIDITY = 24*60*60*1000; 
	
	private Key encryptedSecretKey() {
		
		//this method used for generate encrypted secretKey,
		//for encryption  we used hmacShaKeyFor() algorithm
		return Keys.hmacShaKeyFor(secerKay.getBytes());
	}
	
	public String generateJwtToken(User userData) {
		
		Date tokenGenerationDate= new Date();
		
		Date tokenExpairyDate= new Date( tokenGenerationDate.getTime() + JWT_TOKEN_VALIDITY);
		
		Map<String, Object> userMap= new HashMap<String, Object>();
		
		userMap.put("id", userData.getId());
		userMap.put("name", userData.getName());
		userMap.put("email", userData.getEmail());
		userMap.put("role", "ADMIN");
		
		String jwtToken= Jwts.builder()
						  .claims()
						  .add(userMap)
						  .and()
						  .subject(userData.getEmail())
						  .issuedAt(tokenExpairyDate)
						  .signWith(encryptedSecretKey())
						  .expiration(tokenExpairyDate)
						  .compact();
		
		return jwtToken;
	}
	
	/*
	 * how to token is valid or not
	 * Try to claims -> if successful, Okay else throw error
	 * check if it is not expired
	 */
	
	public Claims getJwtClaim(String token) {
		
		SecretKey secretKey = new SecretKeySpec(secerKay.getBytes(), "HmacSHA256");
		
		Claims claims= Jwts.parser()
							.verifyWith(secretKey)
							.build()
							.parseSignedClaims(token)
							.getPayload();
		
		return claims;
		
	}
	
	public Boolean verifyJwtToken(String token) {
		
		Claims claims=getJwtClaim(token);
		
		Boolean isVlaid= claims.getExpiration().after(new Date());
		
		return isVlaid;
	}
}
