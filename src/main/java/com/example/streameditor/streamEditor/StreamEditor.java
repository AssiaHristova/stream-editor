package com.example.streameditor.streamEditor;



public interface StreamEditor {
    void substituteInOutput(String wordToBeReplaced, String wordToReplace) throws Exception;
    void substituteInPlace(String wordToBeReplaced, String wordToReplace) throws Exception;
}
