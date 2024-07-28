package com.example.palindrome;

import com.example.palindrome.exception.PalindromeServiceException;
import com.example.palindrome.model.Palindrome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class PalindromeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalindromeApplication.class, args);
	}

	@Autowired
	private CacheManager cacheManager;

	@Bean
	public List<Palindrome> palindromes() {
		return new LinkedList<>();
	}
}