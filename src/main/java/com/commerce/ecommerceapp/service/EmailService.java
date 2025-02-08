package com.commerce.ecommerceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	@Autowired
	public TemplateEngine templateEngine;

	public void plainTextEmailService(String fromAdress,String toAdress,String subject,String mailBody) {
		
		SimpleMailMessage message=new SimpleMailMessage();
		
		message.setFrom(fromAdress);
		message.setTo(toAdress);
		message.setSubject(subject);
		message.setText(mailBody);
		
		javaMailSender.send(message);		
		
	}
	
	
	public void htmlEmailService(String fromAdress,String toAdress,String subject,String mailBody) throws Exception {
		
		MimeMessage message=javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper=new MimeMessageHelper(message, true, "UTF-8");
		
		helper.setFrom(fromAdress);
		helper.setTo(toAdress);
		helper.setSubject(subject);
		helper.setText(mailBody, true);
		
		javaMailSender.send(message);	
		
	}
	
	public void templateEmailService(String fromAdress,String toAdress,String subject,String filename) throws Exception {
		
		Context context=new Context();
		context.setVariable("name", "Vmashi Reddy");
		
		String mailBody=templateEngine.process(filename, context);
		
		MimeMessage message=javaMailSender.createMimeMessage();
				
		MimeMessageHelper helper=new MimeMessageHelper(message, true, "UTF-8");
				
		helper.setFrom(fromAdress);
		helper.setTo(toAdress);
		helper.setSubject(subject);
		helper.setText(mailBody, true);
		
		javaMailSender.send(message);
	}
	
}
