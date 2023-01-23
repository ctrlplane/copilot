package io.ctrlplane.copilot.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import io.ctrlplane.copilot.model.FileKeyResponse;
import lombok.extern.slf4j.Slf4j;

/** The template for handling file responses. */
@Component
@Slf4j
@ConditionalOnProperty(value = "spring.file.enabled", havingValue = "true")
public class DevTemplate {

    @Value("${spring.file.path}")
    private String path;

    /** Reads a file and returns the {@link FileKeyResponse}. */
    public FileKeyResponse read() {
        final FileKeyResponse fileKeyResponse = new FileKeyResponse();
        try {
            final File resource = new ClassPathResource(path).getFile();
            final String content = new String(Files.readAllBytes(resource.toPath()));
            fileKeyResponse.setData(Map.of("key", content));
            log.info("Set test content to {}", content);
        } catch (final IOException e) {
            log.error("Error loading test key from path {}\n {}", path, e.getLocalizedMessage());
        }
        return fileKeyResponse;
    }
}
