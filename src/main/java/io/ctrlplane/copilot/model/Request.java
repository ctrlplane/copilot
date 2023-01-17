package io.ctrlplane.copilot.model;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/** Represents a request for key. */
@Data
public class Request {

    /* The id of the kek. */
    @SerializedName(value = "kek_id")
    private String kekId;

    /* The provider e.g. Vault, Azure, dev. */
    @SerializedName(value = "kms_provider")
    private String provider;

    /* The path to the key. */
    @SerializedName(value = "kms_provider_path")
    private String kmsProviderPath;

    /* Anything else needed to obtain the key. */
    @SerializedName(value = "kms_additional_params")
    private Map<String, String> kmsAdditionalParams;
}
