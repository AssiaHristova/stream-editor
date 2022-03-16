package com.example.streameditor;

import com.example.streameditor.streamEditor.FileStreamEditor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RequiredArgsConstructor
@RestController
public class StreamEditorController {

    private final FileStreamEditor fileStreamEditor;

    @GetMapping ("/s/{wordToBeReplaced}/{wordToReplace}")
    public ResponseEntity<StreamingResponseBody> substitute(@PathVariable String wordToBeReplaced,
                                                            @PathVariable String wordToReplace) {
        StreamingResponseBody stream = out -> fileStreamEditor.substituteInOutput(wordToBeReplaced, wordToReplace);
        return new ResponseEntity<>(stream, HttpStatus.OK);
    }

    @GetMapping ("/i/s/{wordToBeReplaced}/{wordToReplace}")
    public ResponseEntity<StreamingResponseBody> inFile(@PathVariable String wordToBeReplaced,
                                                            @PathVariable String wordToReplace) {
        StreamingResponseBody stream = in -> fileStreamEditor.substituteInPlace(wordToBeReplaced, wordToReplace);
        return new ResponseEntity<>(stream, HttpStatus.OK);
    }
}
