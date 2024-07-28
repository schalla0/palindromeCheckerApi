package com.example.palindrome.repository;

import com.example.palindrome.exception.PalindromeServiceException;
import com.example.palindrome.model.Palindrome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PalindromeFileRepository {

    private final static Logger log = LoggerFactory.getLogger(PalindromeFileRepository.class);
    private final List<Palindrome> palindromes;
    private  String FILE_STORE = "palindrome.dat";

  @Autowired
    public PalindromeFileRepository(List<Palindrome> palindromes) {
        this.palindromes = palindromes;
    }

    public Palindrome save(Palindrome palindrome) throws PalindromeServiceException {
        try {
            Optional<Palindrome> optionalPalindrome = getPalindromeByUserNameAndText(palindrome.getUserName(), palindrome.getText());
           if(optionalPalindrome.isPresent()) {
               log.info("User: {}  is already exists in storage", palindrome.getUserName());
               return optionalPalindrome.get();
           }
            log.info("User{}: Text: {} is palindrome, storing.", palindrome.getUserName(),  palindrome.getText());
            ObjectOutputStream output = new ObjectOutputStream((new FileOutputStream(FILE_STORE)));
            String id = UUID.randomUUID().toString();
            palindrome.setId(id);
            palindromes.add(palindrome);
            output.writeObject(palindromes);
            output.close();
            log.info("user: {} details are stored successfully, and id: {}  ", palindrome.getUserName(), palindrome.getId());
        } catch (IOException e) {
            throw new PalindromeServiceException("An exception happened when storing the details of a palindrome in a storage", e);
        }
        return palindrome;
    }

    public  Optional<Palindrome> getPalindromeByUserNameAndText(String userName, String text) {
        log.info("getPalindromeByUserNameAndText initiated.");
        Optional<Palindrome> palindromeOptional=Optional.empty();
        try {
            FileInputStream fis = new FileInputStream(FILE_STORE);
            if (fis.available() > 0) {
                ObjectInputStream input = new ObjectInputStream(fis);
                List<Palindrome> palindromes = (List<Palindrome>) input.readObject();
                palindromeOptional = palindromes
                        .stream()
                        .filter(palindrome -> palindrome.getUserName().equals(userName)
                                && palindrome.getText().equals(text))
                        .findAny();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PalindromeServiceException("Exception occurred  while reading a palindrome", e);
        }
        return palindromeOptional;
    }

    public  Optional<Palindrome> getPalindromeByUserName(String userName) {
        log.info("getPalindromeByUserName initiated.");
        Optional<Palindrome> palindromeOptional = Optional.empty();
        try {
            FileInputStream fis = new FileInputStream(FILE_STORE);
            if (fis.available() > 0) {
                ObjectInputStream input = new ObjectInputStream(fis);
                List<Palindrome> palindromes = (List<Palindrome>) input.readObject();
                palindromeOptional = palindromes
                        .stream()
                        .filter(palindrome -> palindrome.getUserName().equals(userName))
                        .findAny();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PalindromeServiceException("Exception occurred  while reading a palindrome", e);
        }
        return palindromeOptional;
    }

    public  List<Palindrome> getAllPalindrome() {
        log.info("getAllPalindrome initiated.");
        List<Palindrome> palindromes= new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(FILE_STORE);
            if (fis.available() > 0) {
                ObjectInputStream input = new ObjectInputStream(fis);
                palindromes = (List<Palindrome>) input.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PalindromeServiceException("An Exception occurred while reading palindromes", e);
        }
        return
                palindromes;
    }

    public void setFileName(String fileName) {
      FILE_STORE = fileName;
    }

}
