package io.ctrlplane.copilot.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/** A REST API for key requests. */
@RestController
public class RequestController {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(RequestController.class);

    /** A static encryption key. */
    private static final byte[] KEY =
            "supersecretkey!!".getBytes(StandardCharsets.UTF_8);

    /** The repository for storing key request records. */
    private final RequestRepository requestRepository;

    /**
     * Constructor.
     *
     * @param requestRepository The request repository.
     */
    @Autowired
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
        LOG.debug("Received request for kekID {}", kekId);
        this.requestRepository.save(new RequestRecord(kekId));
        return ResponseEntity.ok(KEY);
    }

}
