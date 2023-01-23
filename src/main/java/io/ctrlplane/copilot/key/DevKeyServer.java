package io.ctrlplane.copilot.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import io.ctrlplane.copilot.configuration.DevTemplate;
import io.ctrlplane.copilot.model.FileKeyResponse;
import lombok.extern.slf4j.Slf4j;

/** Simulates a response from vault. */
@Component
@Slf4j
@ConditionalOnProperty(value = "spring.file.enabled", havingValue = "true")
public class DevKeyServer implements KeyServer<FileKeyResponse> {

    /** The template that allows communication with file server. */
    @Autowired
    private DevTemplate devTemplate;

    /**
     * Reads the content of the file.
     * 
     * @param path The path to the key.
     */
    @Override
    public FileKeyResponse getKmsResponse(String path) {
        log.info("Initializing kms dev server.");
        return devTemplate.read();
    }

}
