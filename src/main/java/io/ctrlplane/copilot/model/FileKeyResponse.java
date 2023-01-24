package io.ctrlplane.copilot.model;

import java.util.Map;

import lombok.Data;

@Data
public class FileKeyResponse implements Response {
    /** The response data. */
    private Map<String, String> data;
}
