package com.commerce.ecommerceapp.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.ecommerceapp.entity.User;
import com.commerce.ecommerceapp.exceptions.LoginCredetailsInvalidException;
import com.commerce.ecommerceapp.exceptions.UserNotFoundException;
import com.commerce.ecommerceapp.pojo.LoginApiData;
import com.commerce.ecommerceapp.pojo.MoneyTransferAccountDetailsApiData;
import com.commerce.ecommerceapp.pojo.SignUpApiData;
import com.commerce.ecommerceapp.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtService jwtService;
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	//@Transactional(rollbackFor = Exception.class) -> for checked exceptions(compileTime)
	//@Transactional -> for runtime unchecked exceptions
	@Transactional
	public Map<String, Object> moneyTransferHandlingService(MoneyTransferAccountDetailsApiData moneyTransferAccountDetailsApiData) throws Exception {
		
		Optional<User> userOptional = userRepository.findByAccountNumber(moneyTransferAccountDetailsApiData.getFromAccount());
		
		if(userOptional.isEmpty()) {
			throw new RuntimeException("fromAccount not found");
		}
		User user = userOptional.get();
		Double balance = user.getAmount() - moneyTransferAccountDetailsApiData.getAmount();
		
		user.setAmount(balance);
		userRepository.save(user);
		
		Optional<User> userOptional2 = userRepository.findByAccountNumber(moneyTransferAccountDetailsApiData.getToAccount());
		
		if(userOptional2.isEmpty()) {
			throw new RuntimeException("toAccount not found");
		}
		User user2 = userOptional2.get();
		Double balance2 = user2.getAmount() + moneyTransferAccountDetailsApiData.getAmount();
		
		user2.setAmount(balance2);
		userRepository.save(user2);
		
		Map<String, Object> senderAndReciverAccountData = new HashMap<String, Object>();
		
		senderAndReciverAccountData.put("fromAccountData", user );
		senderAndReciverAccountData.put("toAccountData", user2);
		
		return senderAndReciverAccountData;
		
		
	}
	
	

	public User createUser(SignUpApiData signUpApiData) throws Exception {
		
		Optional<User> userOptional= userRepository.findByEmail(signUpApiData.getEmail());
		
		if(userOptional.isEmpty()) {
			User user=new User();
			
			//signUpApiData.getPassword()
			
			user.setName(signUpApiData.getName());
			user.setEmail(signUpApiData.getEmail());
			user.setPassword(passwordEncoder.encode(signUpApiData.getPassword()));
			user.setMobile(signUpApiData.getMobile());
			
			logger.info("User Account Created: email = {}",signUpApiData.getEmail());
			return userRepository.save(user);
		}else {
			logger.error("User already exist, please login email = {}",signUpApiData.getEmail());
			throw new Exception("User already registred wit us. please login..");
		}
	}
	
	public Map<String, Object> loginServie(LoginApiData loginApiData){
		
		Optional<User> dbData= userRepository.findByEmail(loginApiData.getEmail());
		if(dbData.isPresent()) {
			User user=dbData.get();
			
			Boolean isPasswordValid=passwordEncoder.matches(loginApiData.getPassword(),user.getPassword());
			if(isPasswordValid) {
				String jwtToken = jwtService.generateJwtToken(user);
				Map<String, Object> response = new HashMap<String, Object>();
				response.put("token", jwtToken);
				response.put("userData", user);
				return response;
			}else {
				throw new LoginCredetailsInvalidException("Password is invalid, please try again",loginApiData.getEmail());
			}
		}else {
			throw new UserNotFoundException("Your email not registred with us, please signup and try again", loginApiData.getEmail());
		}
		
	}
}
