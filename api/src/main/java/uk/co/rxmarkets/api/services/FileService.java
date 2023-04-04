package uk.co.rxmarkets.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.smallrye.mutiny.Uni;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.rxmarkets.api.entities.scoring.Scoreboard;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@ApplicationScoped
@Getter
@Slf4j
public class FileService {

    private final ObjectMapper objectMapper;
    private final String location;

    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.location = System.getProperty("user.home") + "/scores";
    }

    public Uni<Long> save(String market, String ticker, Scoreboard scoreboard) {
        final String fileName = ticker + "_" + LocalDate.now();
        final Path outputPath = Paths.get(location, fileName + ".json");
        try {
            Files.createDirectories(outputPath.getParent());
            Files.createFile(outputPath);
            objectMapper.writeValue(outputPath.toFile(), scoreboard);
            log.info("File created at: {}", outputPath);
            return Uni.createFrom().item(123L); // FIXME | Blocking IO should be replaced with async(!)
        } catch (IOException e) {
            log.error("Error creating file: {}", e.getMessage());
            return Uni.createFrom().failure(e);
        }
    }

}

