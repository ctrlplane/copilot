package io.ctrlplane.copilot.key;

/** The main server to talk to vault or another server type. */
public interface KeyServer<R> {

    /**
     * A read response from vault.
     * 
     * @param path The path to the key.
     */
    R getKmsResponse(String path);
}
