package com.alpidi.model;

import java.util.Map;

import com.alpidi.exception.APIConnectionException;
import com.alpidi.exception.APIException;
import com.alpidi.exception.AuthenticationException;
import com.alpidi.exception.InvalidRequestException;

/**
 * Common interface for Shippo objects that can store metadata.
 */
public interface MetadataStore<T> {
    Map<String, String> getMetadata();

    void setMetadata(Map<String, String> metadata);

    MetadataStore<T> update(Map<String, Object> params) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException;

    MetadataStore<T> update(Map<String, Object> params, String apiKey) throws AuthenticationException,
            InvalidRequestException, APIConnectionException, APIException;
}
