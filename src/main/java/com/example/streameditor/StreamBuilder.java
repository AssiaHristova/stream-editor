package com.example.streameditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

@Component
public class StreamBuilder {
    private final Logger logger = LoggerFactory.getLogger(StreamBuilder.class);

    @Value("${app.inputFilePath}")
    public String inputFilePath;

    @Value("${app.outputFilePath}")
    private String outputFilePath;

    public String readContent() throws IOException {
        String content = "";
        try {
            File inFile = new File(inputFilePath);
            FileInputStream inputStream = new FileInputStream(inFile);
            FileChannel inputChannel = inputStream.getChannel();
            FileLock sharedLock = inputChannel.lock(0, Long.MAX_VALUE, true);
            ByteBuffer buf = ByteBuffer.allocate((int) (inputChannel.size()));
            inputChannel.read(buf, 0);
            content = new String(buf.array());

            buf.clear();
            sharedLock.release();
            inputChannel.close();
            inputStream.close();
            return content;
        } catch (IOException e) {
            logger.error("Error while streaming.", e);
        }
        return content;
    }

    public void writeContentOnOutputStream(String content) throws IOException {
        try {
            File outputFile = new File(outputFilePath);
            FileOutputStream streamOut = new FileOutputStream(outputFile);
            FileChannel outputChannel = streamOut.getChannel();
            writeContent(outputChannel, content);
            streamOut.close();
        } catch (IOException e) {
            logger.error("Error while streaming.", e);
        }
    }

    public void writeContentInPlace(String content) throws IOException {
        try {
            File inputFile = new File(inputFilePath);
            RandomAccessFile writer = new RandomAccessFile(inputFile, "rw");
            FileChannel channel = writer.getChannel();
            writeContent(channel, content);
            writer.close();
        } catch (NonWritableChannelException e) {
            logger.error("Error in channel.", e);
        }
    }

    public void writeContent(FileChannel channel, String content) throws IOException {
        try {
            FileLock exclusiveLock = channel.lock();
            ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
            channel.write(buffer);
            buffer.clear();
            exclusiveLock.release();
            } catch (IOException e) {
                logger.error("Error in channel.", e);
            }
        }
    
    }
