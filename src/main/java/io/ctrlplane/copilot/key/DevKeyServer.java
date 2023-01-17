package io.ctrlplane.copilot.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import io.ctrlplane.copilot.configuration.DevTemplate;
import io.ctrlplane.copilot.model.FileKeyResponse;

/** Simulates a response from vault. */
@Component
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
        return devTemplate.read();
    }

}
