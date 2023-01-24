package io.ctrlplane.copilot.request;

import org.springframework.util.SerializationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.ctrlplane.copilot.key.DevKeyServer;
import io.ctrlplane.copilot.key.VaultServer;
import io.ctrlplane.copilot.model.FileKeyResponse;
import io.ctrlplane.copilot.model.Request;
import io.ctrlplane.copilot.model.VaultKeyResponse;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/** A REST API for key requests. */
@RestController
@Slf4j
public class RequestController {

    /** The application context to get beans. */
    private final ApplicationContext context;

    /** Decoder for input. */
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    /** The repository for storing key request records. */
    private final RequestRepository requestRepository;

    /** Transforms the response to and from json. */
    private static final Gson gson = new Gson();

    /**
     * Constructor.
     *
     * @param requestRepository The request repository.
     * @param context           The context to get the beans from.
     */
    public RequestController(
            final RequestRepository requestRepository,
            final ApplicationContext context) {
        this.requestRepository = requestRepository;
        this.context = context;
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
            @PathVariable final String kekId) {
        ResponseEntity<byte[]> result = ResponseEntity.notFound().build();
        try {
            final String kmsRequest = new String(BASE64_DECODER.decode(kekId)).replace("\n", "");
            final Request kms = gson.fromJson(kmsRequest, Request.class);
            log.debug("Received request for kekID {}", kms.getKekId());

            switch (kms.getProvider()) {
                case "vault" -> result = ResponseEntity.ok(handleVault(kms));
                case "dev" -> result = ResponseEntity.ok(handleDev(kms));
                default -> log.warn("Unkown kms request type {}", kms.getProvider());
            }

        } catch (Exception e) {
            log.error("Something went wrong in getting key for path: {}\n{}", kekId, e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * Handles responses from a dev server.
     * 
     * @param kms The request information.
     * @return The byte contents of the key response.
     */
    private byte[] handleDev(final Request kms) {
        log.info("Handling dev server response.");
        final DevKeyServer fileResponse = context.getBean(DevKeyServer.class);
        this.requestRepository.save(new RequestRecord(kms.getKekId()));
        final FileKeyResponse key = fileResponse.getKmsResponse(kms.getKmsProviderPath());
        return key.getData().get("key").getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Handles responses from vault.
     * 
     * @param kms The request information.
     * @return The byte contents of the key response.
     */
    private byte[] handleVault(final Request kms) {
        final VaultServer vaultServer = context.getBean(VaultServer.class);
        this.requestRepository.save(new RequestRecord(kms.getKekId()));
        final VaultResponseSupport<VaultKeyResponse> vaultResponse = vaultServer.getKmsResponse(kms.getKmsProviderPath());
        final VaultKeyResponse key = vaultResponse.getRequiredData();
        return key.getData().get("key").getBytes(StandardCharsets.UTF_8);
    }

}
