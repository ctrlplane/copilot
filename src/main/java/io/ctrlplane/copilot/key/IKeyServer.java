package io.ctrlplane.copilot.key;

import org.springframework.vault.support.VaultResponseSupport;
import io.ctrlplane.copilot.model.VaultKeyResponse;

/** The main server to talk to vault or another server type. */
public interface IKeyServer {

    /** A read response from vault. */
    VaultResponseSupport<VaultKeyResponse> read(String path);
}
