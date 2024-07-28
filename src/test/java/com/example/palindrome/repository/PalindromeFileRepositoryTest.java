package com.example.palindrome.repository;

import com.example.palindrome.exception.PalindromeServiceException;
import com.example.palindrome.model.Palindrome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PalindromeFileRepositoryTest {

    @InjectMocks
    private PalindromeFileRepository sut;

    List<Palindrome> palindromeList = new LinkedList<>();


    @BeforeEach
    void setUp() {
        sut = new PalindromeFileRepository(palindromeList);
    }

    @Test
    void save() {
        Palindrome palindrome = mockPalindrome();
        Palindrome results = sut.save(palindrome);
        assertNotNull(results);
        assertNotNull(results.getId());
    }

    @Test
    void getPalindromeByUserName() {
        List<Palindrome> allPalindrome = sut.getAllPalindrome();
        assertTrue(allPalindrome.size() > 0);
    }

    @Test
    void getPalindromeByUserNameShouldThrowAnException() {
        sut.setFileName("invalid");
        PalindromeServiceException palindromeServiceException = assertThrows(PalindromeServiceException.class, () ->
                        sut.getAllPalindrome(), "An Exception occurred while reading palindromes");
    }

    @Test
    void getAllPalindrome() {
        Optional<Palindrome> palindromeByUserName = sut.getPalindromeByUserNameAndText("user1", "kayak");
        assertNotNull(palindromeByUserName.get());
    }

    @Test
    void getByUserName() {
        Optional<Palindrome> palindromeByUserName = sut.getPalindromeByUserName("user1");
        assertNotNull(palindromeByUserName.get());
    }

    private Palindrome mockPalindrome() {
        Palindrome palindrome = new Palindrome();
        palindrome.setText("kayak");
        palindrome.setUserName("user1");
        palindrome.setPalindrome(true);
        return palindrome;
    }

}