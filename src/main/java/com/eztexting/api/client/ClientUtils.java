package com.eztexting.api.client;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.eztexting.api.client.api.common.model.request.QueryParamIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;

import static com.fasterxml.jackson.databind.PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE;

/**
 * Utility class
 *
 * @since 1.0
 */
public final class ClientUtils {
    private ClientUtils() {
    }

    /**
     * Method traverses request object using reflection and build key=value string
     *
     * @param request request
     * @param <T>     type of request
     * @return string representation of request params
     * @throws EzTextingClientException in case IllegalAccessException occurred
     */
    public static <T extends EzTextingModel> StringBuilder buildQueryParams(T request) throws EzTextingClientException {
        StringBuilder params = new StringBuilder();
        if (request == null) {
            return params;
        }
        Class<?> superclass = request.getClass().getSuperclass();
        while (superclass != null) {
            readFields(request, params, superclass);
            superclass = superclass.getSuperclass();
        }
        readFields(request, params, request.getClass());
        // remove & at the end
        int lastChar = params.length() - 1;
        if (params.charAt(lastChar) == '&') {
            params.deleteCharAt(lastChar);
        }
        return params;
    }

    private static void readFields(Object request, StringBuilder params, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                readField(request, params, field);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new EzTextingClientException(e);
            }
        }
    }

    private static void readField(Object request, StringBuilder params, Field field)
        throws IllegalAccessException, InvocationTargetException {
        field.setAccessible(true);
        if (field.isAnnotationPresent(QueryParamIgnore.class) &&
            field.getAnnotation(QueryParamIgnore.class).enabled()) {
            return;
        }
        Object value = field.get(request);
        if (value != null) {
            String paramName = getParamName(field);
            if (value instanceof Collection) {
                for (Object o : (Collection) value) {
                    params.append(paramName).append("=").append(encode(o.toString())).append("&");
                }
                return;
            }
            if (value instanceof Enum) {
                Enum enumValue = (Enum) value;
                Method[] enumMethods = enumValue.getDeclaringClass().getDeclaredMethods();
                for (Method method : enumMethods) {
                    if (method.isAnnotationPresent(JsonValue.class)) {
                        value = method.invoke(enumValue);
                    }
                }
            }
            if (value instanceof Date) {
                value = ((Date) value).getTime();
            }
            params.append(paramName).append("=").append(encode(value.toString())).append("&");
        }
    }

    public static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EzTextingClientException(e);
        }
    }

    private static String getParamName(Field field) {
        String paramName = PASCAL_CASE_TO_CAMEL_CASE.nameForField(null, null, field.getName());
        JsonProperty jsonProperty = field.getDeclaredAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            paramName = jsonProperty.value();
        }
        if (Iterable.class.isAssignableFrom(field.getType())) {
            paramName = paramName + "[]";
        }
        return paramName;
    }
}
