package com.commerce.ecommerceapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ecommerceapp.entity.User;
import com.commerce.ecommerceapp.pojo.ForgotPasswordApiData;
import com.commerce.ecommerceapp.pojo.IpDataClass;
import com.commerce.ecommerceapp.pojo.LoginApiData;
import com.commerce.ecommerceapp.pojo.ResetPasswordApiData;
import com.commerce.ecommerceapp.pojo.SignUpApiData;
import com.commerce.ecommerceapp.responses.ApiPayloads;
import com.commerce.ecommerceapp.service.AuthService;
import com.commerce.ecommerceapp.service.EmailService;
import com.commerce.ecommerceapp.service.ForgotPasswordService;
import com.commerce.ecommerceapp.service.IpInfoService;
import com.commerce.ecommerceapp.utils.ResponseData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Authentication APIs", description = "In this authentication apis we implementated all authentication and authorization related APIs")
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private IpInfoService ipInfoService;
	
	
	@PostMapping("signup")
	@Operation(description = "signup used for create user account",summary = "Create user")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200",description = "signUp Success", headers = @Header(name = "Authorization",example = "kdbsuagwfhbleuwrlihfgwh")),
					@ApiResponse(responseCode = "400",description = "signUp unsuccessful")
			}
			
	)
	public ResponseEntity<Map<String, Object>> signUpApi(@Valid @RequestBody SignUpApiData signUpApiData) throws Exception {
		
		User user= authService.createUser(signUpApiData);
		
		Map<String, Object> responseMap=new HashMap<String, Object>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.DATA, user);
	
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
	}
	
	
	/* Login functionality
	   email and password
	   check email is exist or not -> if exists validate password -> else user not registered with us, please try again
	   if user email and password both are matching -> return user data
	 */
	@PostMapping("login")
	public ResponseEntity<?> loginApi(@Valid @RequestBody LoginApiData loginApiData,HttpServletRequest httpServletRequest) throws Exception {
		
		IpDataClass ipDataClass = ipInfoService.ipAddressInfo(httpServletRequest.getRemoteAddr());
		
		Map<String, Object> responseMap= new HashMap<String, Object>();
		
		Map<String, Object> user= authService.loginServie(loginApiData);
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.DATA, user);
		responseMap.put("ipAddressData", ipDataClass);
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Authorization",user.get("token").toString());
		
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseMap);
	}
	
	@PostMapping("loginV2")
	public ResponseEntity<?> loginApiV2(@Valid @RequestBody LoginApiData loginApiData){
		
//		Map<String, Object> responseMap= new HashMap<String, Object>();
//		
//		Map<String, Object> user= authService.loginServie(loginApiData);
//		
//		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
//		responseMap.put(ResponseData.DATA, user);
		
		Map<String, Object> user= authService.loginServie(loginApiData);
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Authorization",user.get("token").toString());
		
		ApiPayloads<Map<String, Object>> response = new ApiPayloads<>("Success", "OK", user);
		
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
	}
	
	
	@GetMapping("send-email")
	public ResponseEntity<?> sendEmail() throws Exception{
		
		String fromAdreess="reddyymahesh76@gmail.com";
		String toAdress="vamshikrishnareddy1133@gmail.com";
//		String subject="Testing Mail";
//		String mailBody="Hi Vamshi </br>"
//						+"<p>This mail is regarding testing purpose</p></br>"
//						+"<p><b>Rgards,</b></p></br>"
//						+"<p><b>Mahesh</b></p>";
		
		
		//emailService.plainTextEmailService(fromAdreess, toAdress, subject, mailBody);
		
		String subject="Frontend development course details..";
		
		String filename="simplemail";
		
		//emailService.htmlEmailService(fromAdreess, toAdress, subject, mailBody);
		
		emailService.templateEmailService(fromAdreess, toAdress, subject, filename);
		Map<String, String> responseMap=new HashMap<String, String>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.MESSAGE, "Email Sent");
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
		
	}
	
	
	/*
	  create path
	  we need email id for checking user exist or not -> if yes proceed 
	  to create random linkId for reset the password and send mail to the user with linkId 
	  and save it in the database table for that email id
	 */
	@PostMapping("forgot-password")
	public ResponseEntity<?> sendResetPasswordMail(@Valid @RequestBody ForgotPasswordApiData forgotPasswordApiData) throws Exception {
		
		//System.out.println(forgotPasswordApiData.getEmail());
		
		forgotPasswordService.forgotPasswordSendMail(forgotPasswordApiData);
	
		Map<String, String> responseMap=new HashMap<String, String>();
	
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.MESSAGE, "Chesck your mail for password reset link..");
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	/*
	 create path
	 we need password, confirm password,linkId for validation with database and updation
	 get the user details with that linkId -> user object is not empty then proceed to LinkId validation
	 check linkId with db linkId matching or not-> if yes ->proceed to validate password and confirm password
	 if both passwords are correct then update password in db table and empty the linkId in the db table 
	 */
	
	@PostMapping("reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordApiData resetPasswordApiData) throws Exception {
		
		forgotPasswordService.resetPassword(resetPasswordApiData);
		Map<String, String> responseMap=new HashMap<String, String>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.MESSAGE, "Password updated successfully, please login again..");
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
}
