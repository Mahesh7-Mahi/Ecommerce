package com.commerce.ecommerceapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.ecommerceapp.entity.User;
import java.util.List;




@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByLinkId(String linkId);
	
	Optional<User> findByAccountNumber(String accountNumber);
	
}
