package com.example.palindrome.repository;

import com.example.palindrome.model.Palindrome;

import java.util.List;
import java.util.Optional;

public interface PalindromeRepository {

    public Palindrome save(Palindrome palindrome);

    public List<Palindrome> getAllPalindrome();

    public Optional<Palindrome> getPalindromeByUserNameAndText(String userName, String text);
    public Optional<Palindrome> getPalindromeByUserName(String userName);
}
