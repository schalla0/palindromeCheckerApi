package com.example.palindrome.util;

import com.example.palindrome.model.Palindrome;
import com.example.palindrome.model.PalindromeResponse;

public class PalindromeUtil {
    public static PalindromeResponse buildResponse(Palindrome palindrome) {
        return PalindromeResponse.builder()
                .id(palindrome.getId())
                .text(palindrome.getText())
                .palindrome(palindrome.isPalindrome()).build();
    }

}
