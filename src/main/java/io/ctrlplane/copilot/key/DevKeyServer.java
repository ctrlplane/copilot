package io.ctrlplane.copilot.key;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.vault.support.VaultResponseSupport;

import io.ctrlplane.copilot.configuration.DevTemplate;
import io.ctrlplane.copilot.model.FileKeyResponse;
import io.ctrlplane.copilot.model.VaultKeyResponse;

@Component
@ConditionalOnProperty(
    value="spring.file.enabled", 
    havingValue = "true")
/** Simulates a response from vault. */
public class DevKeyServer implements KeyServer<FileKeyResponse> {

    @Autowired
    private DevTemplate devTemplate;

    @Override
    public FileKeyResponse read(String path) {
        return devTemplate.read();
    }
}
