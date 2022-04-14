package io.ctrlplane.copilot.request;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

/** A key request record. */
@Data
public class RequestRecord {

    /** The ID of the key encryption key.*/
    private final String kekId;

    /** The time of the request. */
    @CreatedDate
    private Date requestTime;

}
