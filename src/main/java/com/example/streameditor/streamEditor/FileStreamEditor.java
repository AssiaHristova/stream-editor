package com.example.streameditor.streamEditor;

import java.io.*;

import com.example.streameditor.StreamBuilder;
import com.example.streameditor.textEditor.WordSubstitutor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class FileStreamEditor implements StreamEditor {

    private final StreamBuilder streamBuilder;
    private final WordSubstitutor wordSubstitutor;

    @Override
    public void substituteInOutput(String wordToBeReplaced, String wordToReplace) throws IOException {
        final String inputText = streamBuilder.readContent();
        final String outputText = wordSubstitutor.substituteWords(inputText, wordToBeReplaced, wordToReplace);
        streamBuilder.writeContentOnOutputStream(outputText);
    }

    @Override
    public void substituteInPlace(String wordToBeReplaced, String wordToReplace) throws IOException {
        final String inText = streamBuilder.readContent();
        final String outText = wordSubstitutor.substituteWords(inText, wordToBeReplaced, wordToReplace);
        streamBuilder.writeContentInPlace(outText);
    }


}
