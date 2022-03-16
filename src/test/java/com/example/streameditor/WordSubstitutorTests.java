package com.example.streameditor;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class WordSubstitutorTests {
    @Test
    void substituteWords_shouldSubstitute() {
        String content = "Hello, hello, hello";
        String wordToBeReplaced = "hello";
        String wordToReplace = "hi";
        String regex = "\\b" + wordToBeReplaced + "\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);

        String result;
        if (matcher.find()) {
            result = content.replaceAll(regex, wordToReplace);
            assertEquals("Hello, hi, hi", result);
        } else {
            result = content;
            assertEquals(content, result);
    }}
}
