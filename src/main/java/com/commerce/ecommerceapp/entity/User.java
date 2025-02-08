package com.commerce.ecommerceapp.entity;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String mobile;
	
	private String linkId;
	
	private Date createdOn= new Date();
	
	private Boolean isActive=true;
	
	private Boolean isEmailVerified=false;
	
	private String accountNumber;
	
	private Double amount;
}
