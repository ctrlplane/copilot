package io.ctrlplane.copilot.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.ReactiveVaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import io.ctrlplane.copilot.model.VaultKeyResponse;

/** The main vault server. */
@Component
@ConditionalOnProperty(value = "spring.cloud.vault.enabled", havingValue = "true")
public class VaultServer implements KeyServer<VaultResponseSupport<VaultKeyResponse>> {

    /** The template to communicate with vault. */
    @Autowired
    private ReactiveVaultTemplate reactiveVaultTemplate;

    /**
     * Reads the response from vault.
     * 
     * @param path The path where the key is located.
     */
    @Override
    public VaultResponseSupport<VaultKeyResponse> getKmsResponse(String path) {
        return this.reactiveVaultTemplate
                .read(path, VaultKeyResponse.class).block();
    }

}
