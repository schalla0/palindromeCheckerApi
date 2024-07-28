package com.example.palindrome.controller;

import com.example.palindrome.PalindromeApplication;
import com.example.palindrome.model.Palindrome;
import com.example.palindrome.model.PalindromeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(classes = PalindromeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableCaching
public class PalindromeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CacheManager cacheManager;

    @Test
    public void palindromeCheckerShouldReturnSuccessfulResponse() {
        Palindrome palindrome = mockPalindrome();
        ResponseEntity<PalindromeResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:"+port+"/v1/palindrome-checker", palindrome, PalindromeResponse.class);
        assertEquals(200 , responseEntity.getStatusCode().value());
        assertTrue(palindrome.isPalindrome());
    }

    @Test
    public void palindromeCheckerShouldReturnBadRequest() {
        Palindrome palindrome = mockPalindrome();
        palindrome.setText("invalid 23 @text#");
        palindrome.setUserName(null);
        ResponseEntity<PalindromeResponse> responseEntity = this.restTemplate
                .postForEntity("http://localhost:"+port+"/v1/palindrome-checker", palindrome, PalindromeResponse.class);
        assertEquals(400 , responseEntity.getStatusCode().value());
    }

    @Test
    public void getPalindromeByUserName() {
        Palindrome palindrome = mockPalindrome();
        ResponseEntity<PalindromeResponse> responseEntity = this.restTemplate
                .getForEntity("http://localhost:"+port+"/v1/palindrome-checker/user1", PalindromeResponse.class);
        assertEquals(200 , responseEntity.getStatusCode().value());
        assertTrue(palindrome.isPalindrome());
        assertEquals(palindrome.getUserName(), "user1");
        assertEquals(palindrome.getText(), "kayak");
    }

    @Test
    public void getPalindromeByUserNameWithUnknownUser() {
        ResponseEntity<PalindromeResponse> responseEntity = this.restTemplate
                .getForEntity("http://localhost:"+port+"/v1/palindrome-checker/unknown", PalindromeResponse.class);
        assertEquals(404 , responseEntity.getStatusCode().value());
    }

    @Test
    public void getCachedPalindromeShouldReturnFromCache() {
        Optional<Palindrome> palindrome = ofNullable(cacheManager.getCache("palindromeCache"))
                .map(cache -> cache.get("user1_kayak",Palindrome.class));
        assertNotNull(palindrome);
    }

    private Palindrome mockPalindrome() {
        Palindrome palindrome = new Palindrome();
        palindrome.setText("kayak");
        palindrome.setUserName("user1");
        palindrome.setPalindrome(true);
        return palindrome;
    }
}