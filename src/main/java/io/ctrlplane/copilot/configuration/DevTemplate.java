package io.ctrlplane.copilot.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import io.ctrlplane.copilot.model.FileKeyResponse;

@Component
@ConditionalOnProperty(
    value="spring.file.enabled", 
    havingValue = "true")
public class DevTemplate {

    public FileKeyResponse read() {
        return null;
    }
}
