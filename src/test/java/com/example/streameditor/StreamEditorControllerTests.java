package com.example.streameditor;

import com.example.streameditor.streamEditor.FileStreamEditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@WebAppConfiguration
class StreamEditorControllerTests {
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private FileStreamEditor fileStreamEditor;


    @BeforeEach
    public void setUp() {mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}

    @Test
    void substitute_shouldReturnOK() throws Exception {
        String wordToBeReplaced = "hello";
        String wordToReplace = "world";
        String endpoint = "/s/{wordToBeReplaced}/{wordToReplace}";
        StreamingResponseBody stream = out -> fileStreamEditor.substituteInOutput(wordToBeReplaced, wordToReplace);

        MvcResult response = mockMvc.perform(get(endpoint, wordToBeReplaced, wordToReplace)).andExpect(status().isOk()).andReturn();
        assertEquals(200, response.getResponse().getStatus());


    }

    @Test
    void inFile_shouldReturnOK() throws Exception {
        String wordToBeReplaced = "hello";
        String wordToReplace = "world";
        String endpoint = "/i/s/{wordToBeReplaced}/{wordToReplace}";
        StreamingResponseBody stream = in -> fileStreamEditor.substituteInPlace(wordToBeReplaced, wordToReplace);

        MvcResult response = mockMvc.perform(get(endpoint, wordToBeReplaced, wordToReplace)).andExpect(status().isOk()).andReturn();
        assertEquals(200, response.getResponse().getStatus());
    }
}
