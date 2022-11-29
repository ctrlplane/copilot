package io.ctrlplane.copilot.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Request {
    
    @JsonAlias("kek_id")
    private String kekId;

    @JsonAlias("kms_provider")
    private String provider;

    @JsonAlias("kms_provider_path")
    private String kmsProviderPath;

    @JsonAlias("kms_additional_params")
    private Map<String, String> kmsAdditionalParams;
}
