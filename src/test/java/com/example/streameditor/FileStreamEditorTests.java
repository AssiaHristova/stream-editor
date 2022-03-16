package com.example.streameditor;

import com.example.streameditor.streamEditor.FileStreamEditor;
import com.example.streameditor.textEditor.WordSubstitutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class FileStreamEditorTests {
    @Mock
    StreamBuilder streamBuilder;

    @Mock
    WordSubstitutor wordSubstitutor;

    @InjectMocks
    FileStreamEditor fileStreamEditor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void substituteInOutput_ShouldSubstituteInOutputFile() throws IOException {
        String wordToBeReplaced = "hello";
        String wordToReplace = "hi";

        when(streamBuilder.readContent()).thenReturn("Hello, hello, hello");
        String inputText = streamBuilder.readContent();
        assertEquals("Hello, hello, hello", inputText);

        when(wordSubstitutor.substituteWords(inputText, wordToBeReplaced, wordToReplace)).thenReturn("Hello, hi, hi");
        String outputText = wordSubstitutor.substituteWords(inputText, wordToBeReplaced, wordToReplace);
        assertEquals("Hello, hi, hi", outputText);

        streamBuilder.writeContentOnOutputStream(outputText);
    }

    @Test
    void substitutePartOfAWord_ShouldNotSubstituteInOutputFile() throws IOException {
        String wordToBeReplaced = "hell";
        String wordToReplace = "hi";

        when(streamBuilder.readContent()).thenReturn("Hello, hello, hello");
        String inputText = streamBuilder.readContent();
        assertEquals("Hello, hello, hello", inputText);

        when(wordSubstitutor.substituteWords(inputText, wordToBeReplaced, wordToReplace)).thenReturn("Hello, hello, hello");
        String outputText = wordSubstitutor.substituteWords(inputText, wordToBeReplaced, wordToReplace);
        assertEquals("Hello, hello, hello", outputText);

        streamBuilder.writeContentOnOutputStream(outputText);

    }

    @Test
    void substituteInPlace_ShouldSubstituteInInputFile() throws IOException {
        String wordToBeReplaced = "hello";
        String wordToReplace = "hi";

        when(streamBuilder.readContent()).thenReturn("Hello, hello, hello");
        String inputText = streamBuilder.readContent();
        assertEquals("Hello, hello, hello", inputText);

        when(wordSubstitutor.substituteWords(inputText, wordToBeReplaced, wordToReplace)).thenReturn("Hello, hi, hi");
        String outputText = wordSubstitutor.substituteWords(inputText, wordToBeReplaced, wordToReplace);
        assertEquals("Hello, hi, hi", outputText);

        streamBuilder.writeContentInPlace(outputText);
    }

}
