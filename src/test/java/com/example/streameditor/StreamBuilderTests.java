package com.example.streameditor;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


class StreamBuilderTests {

    StreamBuilder streamBuilder = new StreamBuilder();

    String inputFilePath = "src/test/java/com/example/streameditor/testInputFile.txt";
    String outputFilePath = "src/test/java/com/example/streameditor/testOutputFile.txt";


    @Test
    void readContent_ShouldReturnContent() throws Exception {
        File testInputFile = new File(inputFilePath);
        String line = "Hello, hello, hello";
        Files.write(testInputFile.toPath(), Collections.singleton(line));
        FileInputStream inputStream = new FileInputStream(testInputFile);
        try {
            FileChannel inputChannel = inputStream.getChannel();
            FileLock sharedLock = inputChannel.lock(0, Long.MAX_VALUE, true);
            assertTrue(testInputFile.canRead());

            ByteBuffer buf = ByteBuffer.allocate((int) (inputChannel.size() - 1));
            inputChannel.read(buf, 0);
            String content = new String(buf.array());
            String result = StringUtils.chop(content);
            assertEquals(line, result);

            buf.clear();
            sharedLock.release();
            inputChannel.close();
            inputStream.close();
        } catch (IOException e) {}
    }

    @Test
    void readContentFromLockedFile_ShouldThrowOverlappingFileLockException() throws IOException {
        File testInputFile = new File(inputFilePath);
        FileInputStream inputStream = new FileInputStream(testInputFile);
        FileChannel inputChannel = inputStream.getChannel();
        FileLock sharedLock = inputChannel.lock(0, Long.MAX_VALUE, true);
        assertThrows(OverlappingFileLockException.class, () -> {
            FileLock sharedLock2 = inputChannel.lock(0, Long.MAX_VALUE, true);
        });
        sharedLock.release();
        inputChannel.close();
        inputStream.close();

    }

    @Test
    void readContentFromMissingFile_ShouldThrowFileNotFoundException() throws IOException {
        assertThrows(FileNotFoundException.class, () -> {
            FileInputStream inputStream = new FileInputStream("src/test/java/com/example/streameditor/missingFile.txt");
        });
    }
    @Test
    void writeContentOnOutputStream_ShouldWriteOnOutputStream() throws IOException {
        File testOutputFile = new File(outputFilePath);
        String content = "Hello, hello, hello";
        FileOutputStream streamOut = new FileOutputStream(testOutputFile);
        FileChannel outputChannel = streamOut.getChannel();
        try {
            streamBuilder.writeContent(outputChannel, content);
            streamOut.close();
            outputChannel.close();
        } catch (IOException e) {
        }

        BufferedReader readerOutput = new BufferedReader(new FileReader(testOutputFile));
        String result = readerOutput.readLine();
        readerOutput.close();

        assertEquals(content, result);
    }

    @Test
    void writeContentInPlace_ShouldWriteOnInputFile() throws IOException {
        File testInputFile = new File(inputFilePath);
        String content = "Hello, nice to meet you.";
        try {
            RandomAccessFile writer = new RandomAccessFile(testInputFile, "rw");
            FileChannel channel = writer.getChannel();
            streamBuilder.writeContent(channel, content);
            writer.close();
            channel.close();
        } catch (IOException e) {
        }

        BufferedReader readerOutput = new BufferedReader(new FileReader(testInputFile));
        String result = readerOutput.readLine();
        readerOutput.close();

        assertEquals(content, result);
    }

    @Test
    void writeContent_shouldWriteContent() throws IOException {
        String content = "The sun is shining.";
        File testInputFile = new File(inputFilePath);
        try {
            RandomAccessFile writer = new RandomAccessFile(testInputFile, "rw");
            FileChannel channel = writer.getChannel();
            FileLock exclusiveLock = channel.lock();
            ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
            channel.write(buffer);
            buffer.clear();
            exclusiveLock.release();
        } catch (IOException e) {}
        BufferedReader readerOutput = new BufferedReader(new FileReader(testInputFile));
        String result = readerOutput.readLine();
        readerOutput.close();

        assertEquals(content, result);
    }

    @Test
    void writeContentOnLockedFile_ShouldThrowOverlappingFileLockException() throws IOException {
        File testInputFile = new File(inputFilePath);
        RandomAccessFile writer = new RandomAccessFile(testInputFile, "rw");
        FileChannel channel = writer.getChannel();
        FileLock exclusiveLock = channel.lock();
        assertThrows(OverlappingFileLockException.class, () -> {
            FileLock exclusiveLock2 = channel.lock();
        });
        exclusiveLock.release();
        channel.close();
        writer.close();
    }
}
