package com.eztexting.api.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * JSON serializer/deserializer
 *
 * @since 1.0
 */
public class JsonConverter {
    private ObjectMapper mapper;

    public JsonConverter() {
        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        mapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat(ClientConstants.DATE_FORMAT_PATTERN));
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.ANY);

        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj object to serialize
     * @return representation in JSON string
     * @throws EzTextingClientException in case object cannot be serialized
     */
    public String serialize(Object obj) throws EzTextingClientException {
        try {
            return obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param body The JSON string
     * @param type The type of deserialized entity
     * @param <T>  type of deserialized entity
     * @return deserialized Java object
     * @throws EzTextingClientException in case body cannot be deserialized
     */
    public <T> T deserialize(String body, TypeReference<T> type) throws EzTextingClientException {
        try {
            return mapper.readValue(body, type);
        } catch (IOException e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Get Jackson's {@link ObjectMapper}
     *
     * @return object mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Set Jackson's {@link ObjectMapper}
     *
     * @param mapper object mapper
     */
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
