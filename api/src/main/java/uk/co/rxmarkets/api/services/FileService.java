package uk.co.rxmarkets.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@ApplicationScoped
@Slf4j
public class FileService {

    private final ObjectMapper objectMapper;

    public void saveObjectToJson(Object object, String fileName) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String userHome = System.getProperty("user.home");
        Path outputPath = Paths.get(userHome, "scores", fileName + ".json");
        try {
            Files.createDirectories(outputPath.getParent());
            Files.createFile(outputPath);
            objectMapper.writeValue(outputPath.toFile(), object);
            log.info("File created at: {}", outputPath);
        } catch (IOException e) {
            log.error("Error creating file: {}", e.getMessage());
        }
    }
}

