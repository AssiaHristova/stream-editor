package com.example.streameditor.textEditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WordSubstitutor implements TextEditor{
    private final Logger logger = LoggerFactory.getLogger(WordSubstitutor.class);

    public String substituteWords(String content, String wordToBeReplaced, String wordToReplace) {
        final String regex = "\\b" + wordToBeReplaced + "\\b";
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(content);
        logger.info("Replacing all occurrences of " + wordToBeReplaced + " with " + wordToReplace);

        if (matcher.find()) {
            return content.replaceAll(regex, wordToReplace);
        } else return content;
    }
}
