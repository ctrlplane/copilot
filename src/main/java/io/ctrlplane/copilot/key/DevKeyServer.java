package io.ctrlplane.copilot.key;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.vault.support.VaultResponseSupport;
import io.ctrlplane.copilot.model.VaultKeyResponse;

@Profile("dev")
@Component
/** Simulates a response from vault. */
public class DevKeyServer implements IKeyServer {

    @Override
    public VaultResponseSupport<VaultKeyResponse> read(String path) {
        final VaultResponseSupport<VaultKeyResponse> response = new VaultResponseSupport<>();
        VaultKeyResponse vaultKeyResponse = new VaultKeyResponse();
        vaultKeyResponse.setData(Map.of("key", "thisisasupersecretkey"));
        response.setData(vaultKeyResponse);
        return response;
    }
    
}
