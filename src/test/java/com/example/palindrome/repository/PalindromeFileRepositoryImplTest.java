package com.example.palindrome.repository;

import com.example.palindrome.model.Palindrome;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PalindromeFileRepositoryImplTest {

    @Mock
    private PalindromeFileRepository palindromeFileRepository;
    @InjectMocks
    private PalindromeFileRepositoryImpl sut;


    @BeforeEach
    void setUp() {
        sut = new PalindromeFileRepositoryImpl(palindromeFileRepository);
    }
    @Test
    void save() {
        Palindrome palindrome = buildPalindrome();
        when(palindromeFileRepository.save(palindrome)).thenReturn(palindrome);
        Palindrome results = sut.save(palindrome);
        assertTrue(results.isPalindrome());
        assertThat(results, CoreMatchers.is(notNullValue()));
        verify(palindromeFileRepository, times(1)).save(palindrome);
    }

    @Test
    void getAllPalindrome() {
        Palindrome palindrome = buildPalindrome();
        when(palindromeFileRepository.getAllPalindrome()).thenReturn(List.of(palindrome));
        List<Palindrome> results = sut.getAllPalindrome();
        assertThat(results, hasSize(1));
    }

    @Test
    void getByUserName() {
        Palindrome palindrome = buildPalindrome();
        String user = "user1";
        when(palindromeFileRepository.getPalindromeByUserName(user)).thenReturn(Optional.of(palindrome));
        Optional<Palindrome> results = sut.getPalindromeByUserName(user);
        Palindrome response = results.get();
        assertThat(response.getId(), is(palindrome.getId()));
        assertThat(response.getUserName(), is(palindrome.getUserName()));
        assertThat(response.getText(), is(palindrome.getText()));
    }

    @Test
    void getPalindromeByUserName() {
        Palindrome palindrome = buildPalindrome();
        String user = "user1";
        String text = "kayak";
        when(palindromeFileRepository.getPalindromeByUserNameAndText(user, text)).thenReturn(Optional.of(palindrome));
        Optional<Palindrome> results = sut.getPalindromeByUserNameAndText(user, text);
        assertThat(results.get().getId(), is(palindrome.getId()));
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