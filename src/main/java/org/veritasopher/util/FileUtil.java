package org.veritasopher.util;

import com.sun.jdi.InternalException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    public static String readFromFileInResource(String filePath) {
        String fileContent;
        try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath)) {
            assert inputStream != null;
            fileContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return fileContent;
        } catch (IOException e) {
            throw new InternalException("Cannot read file (%s).".formatted(filePath));
        }
    }

}
