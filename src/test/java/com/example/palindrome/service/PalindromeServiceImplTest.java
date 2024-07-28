package com.example.palindrome.service;

import com.example.palindrome.model.Palindrome;
import com.example.palindrome.model.PalindromeRequest;
import com.example.palindrome.model.PalindromeResponse;
import com.example.palindrome.repository.PalindromeFileRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PalindromeServiceImplTest {

    @Mock
    private PalindromeFileRepositoryImpl storeFilePalindromeRepository;
    @InjectMocks
    private PalindromeServiceImpl sut;
    private  Palindrome palindrome;

    @BeforeEach
    void seetUp() {
        palindrome = buildPalindrome();
    }

    @Test
    void init() {
        when(storeFilePalindromeRepository.getPalindromeByUserNameAndText(palindrome.getUserName(), palindrome.getText())).thenReturn(Optional.of(palindrome));
        when(storeFilePalindromeRepository.getAllPalindrome()).thenReturn(List.of(palindrome));
        sut.init();
        verify(storeFilePalindromeRepository, times(1)).getAllPalindrome();
        verify(storeFilePalindromeRepository, times(1)).getPalindromeByUserNameAndText(palindrome.getUserName(), palindrome.getText());
    }

    @Test
    void palindromeCheckerShouldCheckAndStorePalindrome() {
        PalindromeRequest request = new PalindromeRequest();
        request.setText("kayak");
        request.setUserName("user1");
        when(storeFilePalindromeRepository.save(any(Palindrome.class))).thenReturn(palindrome);
        PalindromeResponse results = sut.palindromeChecker(request);
        assertThat(results.getId(), is(palindrome.getId()));
        assertTrue(results.isPalindrome());
        verify(storeFilePalindromeRepository, times(1)).save(any(Palindrome.class));
    }

    @Test
    void palindromeCheckerShouldNotSavePalindromeDetails() {
        PalindromeRequest request = new PalindromeRequest();
        request.setText("invalid");
        request.setUserName("user1");
        PalindromeResponse results = sut.palindromeChecker(request);
        assertNull(results.getId());
        assertFalse(results.isPalindrome());
        verify(storeFilePalindromeRepository, times(0)).save(any(Palindrome.class));
    }

    @Test
    void getPalindromeByUserNameShouldReturnPalindrome() {
        when(storeFilePalindromeRepository.getPalindromeByUserNameAndText(palindrome.getUserName(), palindrome.getText())).thenReturn(Optional.of(palindrome));
        Optional<Palindrome> results = sut.getPalindromeByUserNameAndText(palindrome.getUserName(), palindrome.getText());
        assertThat(results.get().getId(), is(palindrome.getId()));
    }

    @Test
    void getAllPalindromeShouldReturnPalindromes() {
        when(storeFilePalindromeRepository.getAllPalindrome()).thenReturn(List.of(palindrome));
        List<Palindrome> results = sut.getAllPalindrome();
        assertThat(results, hasSize(1));
    }

    @Test
    void getByUserNameShouldReturnPalindrome() {
        String user = "kayak";
        when(storeFilePalindromeRepository.getPalindromeByUserName(user)).thenReturn(Optional.of(palindrome));
        Optional<Palindrome> results = sut.getPalindromeByUserName(user);
        assertEquals(results.get().getUserName(), palindrome.getUserName());
        assertEquals(results.get().getId(), palindrome.getId());
        assertEquals(results.get().getText(), palindrome.getText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"kayak", "kaYak","KAYAK", "MadaM","maDAM"})
    void isPalindromeShouldRetrunTrue(String originalString) {
        boolean isPalindrome = sut.isPalindrome(originalString);
        assertTrue(isPalindrome);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test2", "kaYak1","ka3-4", "3334","#3+3"})
    void isPalindromeShoudReturnFalse(String originalString) {
        boolean isPalindrome = sut.isPalindrome(originalString);
        assertFalse(isPalindrome);
    }

    private Palindrome buildPalindrome() {
        Palindrome palindrome = new Palindrome();
        palindrome.setText("kayak");
        palindrome.setUserName("user1");
        palindrome.setPalindrome(true);
        palindrome.setId("testId");
        return palindrome;
    }
}