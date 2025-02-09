package com.commerce.ecommerceapp.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.commerce.ecommerceapp.entity.User;
import com.commerce.ecommerceapp.pojo.ForgotPasswordApiData;
import com.commerce.ecommerceapp.pojo.ResetPasswordApiData;
import com.commerce.ecommerceapp.repository.UserRepository;

@Service
public class ForgotPasswordService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	public void forgotPasswordSendMail(ForgotPasswordApiData forgotPasswordApiData) throws Exception{
		
		Optional<User> userOptional=userRepository.findByEmail(forgotPasswordApiData.getEmail());
		if (userOptional.isEmpty()) {
			throw new Exception("Your email id was not registred with us, please create account");
		}else {
			String passwordResetKey=UUID.randomUUID().toString();
			User user=userOptional.get();
			user.setLinkId(passwordResetKey);
			userRepository.save(user);
			String fromAdreess="mahesh.yedamakanti@gmail.com";
			String toAdress=user.getEmail();
			String subject="Password reset link";
			String filename="Hi "+user.getName()+"<br/>"+
							"Please find the attached link for password reset <br/>"+
							"Please click here:<a href='http://localhost:8080/forgot-password-ui?linkId="+passwordResetKey+"'>reset link</a><br/>"+
							"<b>Regards,<br/>Spring boot App</b>";
			emailService.htmlEmailService(fromAdreess,toAdress,subject,filename);
		}
	}
	
	public void resetPassword(ResetPasswordApiData resetPasswordApiData) throws Exception {
		
		Optional<User> userOptional= userRepository.findByLinkId(resetPasswordApiData.getPasswordResetKey());
		if(userOptional.isEmpty()) {
			throw new Exception("passwordResetKey is invalid or expaired");
		}else {
			if (resetPasswordApiData.getPassword().equals(resetPasswordApiData.getConfirmPssword())) {
				User user=userOptional.get();
				if(user.getLinkId().equals(resetPasswordApiData.getPasswordResetKey())) {
					user.setPassword(passwordEncoder.encode(resetPasswordApiData.getPassword()));
					user.setLinkId("");
					userRepository.save(user);
				}
			}else {
				throw new Exception("password and confirm-password was not matching, please try again");
			}
			
		}
		
	}
}
