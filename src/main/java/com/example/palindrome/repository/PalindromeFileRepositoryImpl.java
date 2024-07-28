package com.example.palindrome.repository;

import com.example.palindrome.model.Palindrome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PalindromeFileRepositoryImpl implements PalindromeRepository{

    private final Logger log = LoggerFactory.getLogger(PalindromeFileRepositoryImpl.class);

    private PalindromeFileRepository palindromeFileRepository;
    @Autowired
    public PalindromeFileRepositoryImpl(PalindromeFileRepository palindromeFileRepository) {
        this.palindromeFileRepository = palindromeFileRepository;
    }

    @Override
    public Palindrome save(Palindrome palindrome) {
        return palindromeFileRepository.save(palindrome);
    }

    @Override
    public List<Palindrome> getAllPalindrome() {
        return palindromeFileRepository.getAllPalindrome();
    }

    @Override
    public Optional<Palindrome> getPalindromeByUserNameAndText(String userName, String text) {
        return palindromeFileRepository.getPalindromeByUserNameAndText(userName, text);
    }

    @Override
    public Optional<Palindrome> getPalindromeByUserName(String userName) {
        return palindromeFileRepository.getPalindromeByUserName(userName);
    }

}
