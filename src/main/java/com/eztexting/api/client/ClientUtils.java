package com.eztexting.api.client;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.eztexting.api.client.api.common.model.QueryParamAsNumber;
import com.eztexting.api.client.api.common.model.QueryParamIgnore;
import com.eztexting.api.client.api.messaging.model.SimpleMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;

import static com.eztexting.api.client.CamelCaseStrategy.CAMEL_CASE_STRATEGY;
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
        int length = params.length();
        if (length > 1 && params.charAt(length - 1) == '&') {
            params.setLength(length - 1);
        }
        return params;
    }

    private static void readFields(Object request, StringBuilder params, Class<?> clazz) {
        JsonNaming jsonNaming = getDeclaredAnnotation(clazz.getDeclaredAnnotations(), JsonNaming.class);
        for (Field field : clazz.getDeclaredFields()) {
            try {
                readField(request, params, field, jsonNaming);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new EzTextingClientException(e);
            }
        }
    }

    private static void readField(Object request, StringBuilder params, Field field, JsonNaming jsonNaming)
        throws IllegalAccessException, InvocationTargetException {
        field.setAccessible(true);
        if (field.isAnnotationPresent(QueryParamIgnore.class) &&
            field.getAnnotation(QueryParamIgnore.class).enabled()) {
            return;
        }
        Object value = field.get(request);
        if (field.isAnnotationPresent(JsonUnwrapped.class) &&
            field.getAnnotation(JsonUnwrapped.class).enabled() && value != null) {
            readFields(value, params, field.getType());
            return;
        }
        if (value != null) {
            String paramName = getParamName(field, jsonNaming);
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
                value = ((Date) value).getTime() / 1000L;
            }
            String paramValue = getParamValue(field, value);
            params.append(paramName).append("=").append(encode(paramValue)).append("&");
        }
    }

    public static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EzTextingClientException(e);
        }
    }

    public static List<NameValuePair> asParams(String name, Object value) {
        List<NameValuePair> params = new ArrayList<>();
        if (value instanceof Collection) {
            for (Object o : (Collection) value) {
                params.add(new BasicNameValuePair(name + "[]", o.toString()));
            }
        } else {
            params.add(new BasicNameValuePair(name, Objects.toString(value)));
        }

        return params;
    }

    public static List<NameValuePair> asParams(String name1, Object value1, String name2, Object value2) {
        List<NameValuePair> params = asParams(name1, value1);
        params.add(new BasicNameValuePair(name2, Objects.toString(value2)));
        return params;
    }

    private static String getParamName(Field field, JsonNaming jsonNaming) {
        String paramName = field.getName();
        // PascalCase properties by default
        if (jsonNaming == null) {
            paramName = PASCAL_CASE_TO_CAMEL_CASE.nameForField(null, null, paramName);
        } else if (CamelCaseStrategy.class.equals(jsonNaming.value())) {
            paramName = CAMEL_CASE_STRATEGY.nameForField(null, null, paramName);
        }
        JsonProperty jsonProperty = getDeclaredAnnotation(field.getDeclaredAnnotations(), JsonProperty.class);
        if (jsonProperty != null) {
            paramName = jsonProperty.value();
        }
        if (Iterable.class.isAssignableFrom(field.getType())) {
            paramName = paramName + "[]";
        }
        return paramName;
    }

    private static String getParamValue(Field field, Object value) {
        if (value instanceof Boolean &&
            field.isAnnotationPresent(QueryParamAsNumber.class) &&
            field.getAnnotation(QueryParamAsNumber.class).enabled()) {
            return BooleanUtils.toIntegerObject((Boolean) value).toString();
        } else if (value instanceof SimpleMessage) {
            return ((SimpleMessage) value).getMessage();
        } else {
            return value.toString();
        }
    }

    @SuppressWarnings("unchecked")
    private static <A> A getDeclaredAnnotation(Annotation[] declaredAnnotations, Class<A> annotation) {
        for (Annotation a : declaredAnnotations) {
            if (annotation.isAssignableFrom(a.getClass())) {
                return (A) a;
            }
        }
        return null;
    }
}
