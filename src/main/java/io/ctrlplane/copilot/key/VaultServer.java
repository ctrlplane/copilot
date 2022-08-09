package io.ctrlplane.copilot.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.ReactiveVaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

import io.ctrlplane.copilot.model.VaultKeyResponse;

@Profile("stage")
@Component
/** The main vault server. */
public class VaultServer implements IKeyServer {

    @Autowired
    private ReactiveVaultTemplate reactiveVaultTemplate;
    
    @Override
    public VaultResponseSupport<VaultKeyResponse> read(String path) {
        return this.reactiveVaultTemplate
        .read(path, VaultKeyResponse.class).block();
    }
    
}
