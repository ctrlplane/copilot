package io.ctrlplane.copilot.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.ctrlplane.copilot.key.IKeyServer;
import io.ctrlplane.copilot.model.VaultKeyResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** A REST API for key requests. */
@RestController
public class RequestController {

    @Autowired
    private IKeyServer reactiveVaultTemplate;

    /** The logger for this class. */
    private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);

    /** Decoder for input. */
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    /** The repository for storing key request records. */
    private final RequestRepository requestRepository;

    /**
     * Constructor.
     *
     * @param requestRepository The request repository.
     */
    public RequestController(
            final RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * Gets the key encryption key with the given ID.
     *
     * @param kekId The key ID to retrieve.
     *
     * @return The key.
     */
    @GetMapping(value = "/api/v1/key/{kekId}")
    public ResponseEntity<byte[]> getKey(
            @PathVariable String kekId) {
        ResponseEntity<byte[]> result = ResponseEntity.notFound().build();
        try {
            final String path = new String(BASE64_DECODER.decode(kekId)).replace("\n", "");
            LOG.debug("Received request for kekID {}", path);
            final VaultResponseSupport<VaultKeyResponse> vaultResponse = this.reactiveVaultTemplate.read(path);
            if (vaultResponse != null) {
                this.requestRepository.save(new RequestRecord(kekId));
                final VaultKeyResponse key = vaultResponse.getRequiredData();
                final byte[] keyContent = key.getData().get("key").getBytes(StandardCharsets.UTF_8);
                result = ResponseEntity.ok(keyContent);
            }
        } catch (Exception e) {
            LOG.error("Something went wrong in getting key for path: {}\n{}", kekId, e.getLocalizedMessage());
        }
        return result;
    }

}
