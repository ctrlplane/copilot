package io.ctrlplane.copilot.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** Represents response from vault. */
public class VaultKeyResponse implements Response {
    /** The authentication information. */
    private String auth;

    /** The key data. */
    private Map<String, String> data;

    /** Duration information. */
    private int leaseDuration;

    /** The lease id. */
    private String leaseId;

    /** Metadata associated with key. */
    private Object metadata;

    /** Auth information. */
    private boolean renewable;

    /** Request id. */
    private String requestId;

    /** Any warnings encountered in request. */
    private Object warnings;
    
    /** The wrapped key info. */
    private Object wrapInfo;
}
