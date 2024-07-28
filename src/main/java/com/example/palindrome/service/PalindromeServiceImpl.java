package com.example.palindrome.service;

import com.example.palindrome.model.Palindrome;
import com.example.palindrome.model.PalindromeRequest;
import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.repository.PalindromeFileRepositoryImpl;
import com.example.palindrome.util.PalindromeUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class PalindromeServiceImpl implements PalindromeService {

    private final static Logger log = LoggerFactory.getLogger(PalindromeServiceImpl.class);
    private PalindromeFileRepositoryImpl storeFilePalindromeRepository;

    @Autowired
    public PalindromeServiceImpl(PalindromeFileRepositoryImpl storeFilePalindromeRepository) {
        this.storeFilePalindromeRepository = storeFilePalindromeRepository;
    }

    @PostConstruct
    public void init(){
        log.info("startup the cache from the permanent storage");
        List<Palindrome> allPalindrome = getAllPalindrome();
        for (Palindrome palindrome : allPalindrome) {
            getPalindromeByUserNameAndText(palindrome.getUserName(), palindrome.getText());
        }
    }
    @Override
    public PalindromeResponse palindromeChecker(PalindromeRequest request) {
        boolean isPalindrome = isPalindrome(request.getText());
        Palindrome palindrome = new Palindrome();
        palindrome.setText(request.getText());
        palindrome.setUserName(request.getUserName());
        if (isPalindrome) {
            log.info("User: {} Text: {} is palindrome, storing.", request.getUserName(), request.getText());
            palindrome.setPalindrome(isPalindrome);
            palindrome = storeFilePalindromeRepository.save(palindrome);
            return PalindromeUtil.buildResponse(palindrome);
        }
        log.info("User: {} Text: {} is not a palindrome", request.getUserName(), request.getText());
        return PalindromeUtil.buildResponse(palindrome);
    }

    @Override
    @Cacheable(cacheNames="palindromeCache", key="#userName+'_'+#text")
    public Optional<Palindrome> getPalindromeByUserNameAndText(String userName, String text)  {
        log.info("get palindrome from storage for userName {}", userName);
        return storeFilePalindromeRepository.getPalindromeByUserNameAndText(userName, text);
    }

    @Override
    @Cacheable(cacheNames="palindromeCache",  key="#p0", unless="#result == null")
    public Optional<Palindrome> getPalindromeByUserName(String userName)  {
        log.info("get palindrome from storage for userName {}", userName);
        return storeFilePalindromeRepository.getPalindromeByUserName(userName);
    }

    @Override
    public List<Palindrome> getAllPalindrome() {
        return storeFilePalindromeRepository.getAllPalindrome();
    }

    public  boolean isPalindrome(String originalString) {
        log.info("Palindrome check for {} ", originalString);
        String tempString = originalString.replaceAll("\\s+", "").toLowerCase();
        return IntStream.range(0, tempString.length() / 2).noneMatch(i -> tempString.charAt(i) != tempString.charAt(tempString.length() - i - 1));
    }
}
