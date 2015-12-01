package com.eztexting.api.client;

import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.contacts.model.Contact;
import com.eztexting.api.client.api.groups.model.Group;
import com.eztexting.api.client.api.keywords.model.CheckAvailabilityResponse;
import com.eztexting.api.client.api.keywords.model.Keyword;
import com.eztexting.api.client.api.messaging.model.DeliveryReport;
import com.eztexting.api.client.api.messaging.model.SendMessageResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains TypeReferences for all model objects
 */
public final class ModelType {
    private static final Map<Class, TypeReference> SIMPLE_TYPES = new HashMap<>();

    static {
        initSimpleTypes();
    }

    private static void initSimpleTypes() {
        // @formatter:off
        // this is for error handling, response with error doesn't contain payload so set it to object
        SIMPLE_TYPES.put(Object.class, new TypeReference<EzTextingResponse<Object>>() {});
        SIMPLE_TYPES.put(String.class, new TypeReference<EzTextingResponse<String>>() {});
        SIMPLE_TYPES.put(Long.class, new TypeReference<EzTextingResponse<Long>>() {});

        SIMPLE_TYPES.put(Keyword.class, new TypeReference<EzTextingResponse<Keyword>>() {});
        SIMPLE_TYPES.put(Group.class, new TypeReference<EzTextingResponse<Group>>() {});
        SIMPLE_TYPES.put(Contact.class, new TypeReference<EzTextingResponse<Contact>>() {});
        SIMPLE_TYPES.put(DeliveryReport.class, new TypeReference<EzTextingResponse<DeliveryReport>>() {});
        SIMPLE_TYPES.put(SendMessageResponse.class, new TypeReference<EzTextingResponse<SendMessageResponse>>() {});
        SIMPLE_TYPES.put(CheckAvailabilityResponse.class, new TypeReference<EzTextingResponse<CheckAvailabilityResponse>>() {});
        // @formatter:on
    }

    private ModelType() {
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeReference<EzTextingResponse<T>> of(Class<T> type) {
        return safeGet(SIMPLE_TYPES, type);
    }

    private static TypeReference safeGet(Map<Class, TypeReference> map, Class type) {
        if (!map.containsKey(type)) {
            throw new IllegalStateException(
                "Map with TypeReferences doesn't contain following type: " + type.getName());
        }
        return map.get(type);
    }
}