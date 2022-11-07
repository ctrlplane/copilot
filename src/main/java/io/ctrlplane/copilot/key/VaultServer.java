package io.ctrlplane.copilot.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.ReactiveVaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import io.ctrlplane.copilot.model.VaultKeyResponse;

@Component
@ConditionalOnProperty(
    value="spring.cloud.vault.enabled", 
    havingValue = "true")
/** The main vault server. */
public class VaultServer implements KeyServer<VaultResponseSupport<VaultKeyResponse>> {

    @Autowired
    private ReactiveVaultTemplate reactiveVaultTemplate;
    
    @Override
    public VaultResponseSupport<VaultKeyResponse> read(String path) {
        return this.reactiveVaultTemplate
        .read(path, VaultKeyResponse.class).block();
    }
    
}
