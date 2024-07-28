package com.example.palindrome.controller;

import com.example.palindrome.exception.PalindromeServiceException;
import com.example.palindrome.model.Palindrome;
import com.example.palindrome.model.PalindromeRequest;
import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.service.PalindromeService;
import com.example.palindrome.util.PalindromeUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/v1/palindrome-checker")
public class PalindromeController {
    private static final Logger log = LoggerFactory.getLogger(PalindromeController.class);

    private PalindromeService palindromeService;

    @Autowired
    public PalindromeController(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }

    @PostMapping()
    public ResponseEntity<PalindromeResponse> palindromeChecker(@Valid @RequestBody PalindromeRequest palindromeRequest) {
        Optional<Palindrome> existingPalindrome = palindromeService.getPalindromeByUserNameAndText(palindromeRequest.getUserName(), palindromeRequest.getText());
        PalindromeResponse palindromeResponse;
        if (existingPalindrome.isPresent()) {
            Palindrome palindrome = existingPalindrome.get();
            log.info("retrieved palindrome from cache for {}", palindrome.getId());
            palindromeResponse = PalindromeUtil.buildResponse(palindrome);
        } else {
            log.info("palindrome checker initiated.");
            palindromeResponse = palindromeService.palindromeChecker(palindromeRequest);
        }
        return ResponseEntity.ok(palindromeResponse);
    }


    @GetMapping("/{userName}")
    ResponseEntity<PalindromeResponse> palindromeByUserName(@Valid @NotNull @PathVariable String userName) {
        Optional<Palindrome> existingPalindrome = palindromeService.getPalindromeByUserName(userName);
        if(existingPalindrome.isPresent()) {
            return ResponseEntity.ok(PalindromeUtil.buildResponse(existingPalindrome.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with name " + userName + " not found");
    }

}
