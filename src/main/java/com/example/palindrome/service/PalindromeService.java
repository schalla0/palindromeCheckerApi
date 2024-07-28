package com.example.palindrome.service;

import com.example.palindrome.model.Palindrome;
import com.example.palindrome.model.PalindromeRequest;
import com.example.palindrome.model.PalindromeResponse;

import java.util.List;
import java.util.Optional;

public interface PalindromeService {

    public PalindromeResponse palindromeChecker(PalindromeRequest request);

    public List<Palindrome> getAllPalindrome();

    public Optional<Palindrome> getPalindromeByUserNameAndText(String userName, String text);

    public Optional<Palindrome> getPalindromeByUserName(String userName);

}
