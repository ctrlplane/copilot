package io.ctrlplane.copilot.request;

import org.springframework.data.mongodb.repository.MongoRepository;

/** A repository interface for storing requests. */
public interface RequestRepository
        extends MongoRepository<RequestRecord, Long> {
    // No extra methods needed
}
