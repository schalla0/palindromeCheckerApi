package com.example.palindrome.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PalindromeRequest {

    @Valid
    @NotBlank(message = "user must be present")
    private String userName;

    @Valid
    @NotBlank(message = "text must be present")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "text should not contain any spaces or digits")
    private String text;
}
