package com.example.palindrome.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Palindrome implements Serializable {

    private String id;
    private String userName;
    private String text;
    private boolean isPalindrome;

}
