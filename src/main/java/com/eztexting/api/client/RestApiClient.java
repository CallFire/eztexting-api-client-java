package com.eztexting.api.client;

import com.eztexting.api.client.api.common.model.EzTextingModel;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.auth.Authentication;
import com.eztexting.api.client.auth.RequestParamAuth;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.eztexting.api.client.ClientConstants.USER_AGENT_PROPERTY;
import static com.eztexting.api.client.ModelType.of;

/**
 * REST client which makes HTTP calls to EzTexting service
 *
 * @since 1.0
 */
public class RestApiClient {
    private static final Logger LOGGER = new Logger(RestApiClient.class);
    private static final EzTextingResponse EMPTY_RESPONSE = new EzTextingResponse();

    private Brand brand;
    private HttpClient httpClient;
    private JsonConverter jsonConverter;
    private Authentication authentication;
    private SortedSet<RequestFilter> filters = new TreeSet<>();

    /**
     * REST API client constructor. Currently available authentication methods: {@link RequestParamAuth}
     *
     * @param brand          EzTexting brand
     * @param authentication API authentication method
     */
    public RestApiClient(Brand brand, Authentication authentication) {
        this.brand = brand;
        this.authentication = authentication;
        jsonConverter = new JsonConverter();
        httpClient = HttpClientBuilder.create()
            .setUserAgent(EzTextingClient.getClientConfig().getProperty(USER_AGENT_PROPERTY))
            .build();
    }

    /**
     * Get Apache HTTP client
     *
     * @return http client
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Set Apache HTTP client
     *
     * @param httpClient http client
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Get Jackson's json converter
     *
     * @return json converter
     */
    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    /**
     * Performs GET request to specified path
     *
     * @param path request path
     * @param type return entity type
     * @param <T>  return entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> get(String path, Class<T> type) {
        return get(path, type, Collections.<NameValuePair>emptyList());
    }

    /**
     * Performs GET request to specified path
     *
     * @param path   request path
     * @param type   return entity type
     * @param params additional request parameters
     * @param <T>    return entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> get(String path, Class<T> type, List<NameValuePair> params) {
        return get(path, type, null, params);
    }

    /**
     * Performs GET request to specified path
     *
     * @param path    request path
     * @param type    return entity type
     * @param request get request object
     * @param <T>     return entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> get(String path, Class<T> type, EzTextingModel request) {
        return get(path, type, request, Collections.<NameValuePair>emptyList());
    }

    /**
     * Performs GET request to specified path
     *
     * @param path    request path
     * @param type    return entity type
     * @param request query request
     * @param params  additional name-value parameters
     * @param <T>     return entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> get(String path, Class<T> type, EzTextingModel request,
        List<NameValuePair> params) {
        try {
            String uri = getApiBasePath() + path + '&' + buildTextPayload(request, params);
            RequestBuilder requestBuilder = RequestBuilder.get(uri);
            LOGGER.debug("GET request to {}", uri);
            return doRequest(requestBuilder, type);
        } catch (IOException e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Performs POST request with binary body to specified path
     *
     * @param path   request path
     * @param type   response entity type
     * @param params request parameters
     * @param <T>    response entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> postFile(String path, Class<T> type, Map<String, ?> params) {
        try {
            String uri = getApiBasePath() + path;
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.addBinaryBody("file", (File) params.get("file"));
            if (params.get("name") != null) {
                entityBuilder.addTextBody("name", (String) params.get("name"));
            }
            RequestBuilder requestBuilder = RequestBuilder.post(uri).setEntity(entityBuilder.build());
            LOGGER.debug("POST file upload request to {} with params {}", uri, params);

            return doRequest(requestBuilder, type);
        } catch (IOException e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Performs POST request to specified path with empty body
     *
     * @param path request path
     * @param type return entity type
     * @param <T>  return entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> post(String path, Class<T> type) {
        return post(path, type, null, Collections.<NameValuePair>emptyList());
    }

    /**
     * Performs POST request with body to specified path
     *
     * @param path    request path
     * @param type    response entity type
     * @param payload request payload
     * @param <T>     response entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> post(String path, Class<T> type, EzTextingModel payload) {
        return post(path, type, payload, Collections.<NameValuePair>emptyList());
    }

    /**
     * Performs POST request with body to specified path
     *
     * @param path   request path
     * @param type   response entity type
     * @param params additional request parameters
     * @param <T>    response entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> post(String path, Class<T> type, List<NameValuePair> params) {
        return post(path, type, null, params);
    }

    /**
     * Performs POST request with body to specified path
     *
     * @param path    request path
     * @param type    response entity type
     * @param file file for upload
     * @param <T>     response entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> post(String path, Class<T> type, File file) {
        try {
            StringBody username = new StringBody(authentication.getUsername(), ContentType.MULTIPART_FORM_DATA);
            StringBody password = new StringBody(authentication.getPassword(), ContentType.MULTIPART_FORM_DATA);
            FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("User", username);
            builder.addPart("Password", password);
            builder.addPart("Source", fileBody);

            HttpPost post = new HttpPost(   getApiBasePath() + path);
            post.setEntity(builder.build());

            return doRequest(post, type);
        } catch (IOException e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Performs POST request with body to specified path
     *
     * @param path    request path
     * @param type    response entity type
     * @param payload request payload
     * @param params  additional request parameters
     * @param <T>     response entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> post(String path, Class<T> type, EzTextingModel payload,
        List<NameValuePair> params) {
        try {
            String uri = getApiBasePath() + path;
            String textPayload = buildTextPayload(payload, params);
            RequestBuilder requestBuilder = RequestBuilder.post(uri)
                .setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
                .setEntity(EntityBuilder.create().setText(textPayload).build());
            LOGGER.debug("POST request to {} params: {}", uri, textPayload);
            return doRequest(requestBuilder, type);
        } catch (IOException e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Performs PUT request with body to specified path
     *
     * @param path    request path
     * @param type    response entity type
     * @param payload request payload
     * @param <T>     response entity type
     * @return pojo mapped from json
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public <T> EzTextingResponse<T> put(String path, Class<T> type, EzTextingModel payload) {
        try {
            String uri = getApiBasePath() + path;
            String stringPayload = buildTextPayload(payload, Collections.<NameValuePair>emptyList());
            RequestBuilder requestBuilder = RequestBuilder.put(uri)
                .setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
                .setEntity(EntityBuilder.create().setText(stringPayload).build());
            LOGGER.debug("PUT request to {} params: {}", uri, stringPayload);
            return doRequest(requestBuilder, type);
        } catch (IOException e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Performs DELETE request to specified path with query parameters
     *
     * @param path request path
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void delete(String path) {
        try {
            String uri = getApiBasePath() + path + '&' + buildTextPayload(null, Collections.<NameValuePair>emptyList());
            LOGGER.debug("DELETE request to {}", uri);
            RequestBuilder requestBuilder = RequestBuilder.delete(uri);
            doRequest(requestBuilder, String.class);
            LOGGER.debug("delete executed");
        } catch (IOException e) {
            throw new EzTextingClientException(e);
        }
    }

    /**
     * Returns base URL path for all Callfire's API 2.0 endpoints
     *
     * @return string representation of base URL path
     */
    public String getApiBasePath() {
        return EzTextingClient.getClientConfig().getProperty(brand.getBasePathProperty());
    }

    /**
     * Returns HTTP request filters associated with API client
     *
     * @return active filters
     */
    public SortedSet<RequestFilter> getFilters() {
        return filters;
    }

    private <T> EzTextingResponse<T> doRequest(RequestBuilder requestBuilder, Class<T> type) throws IOException {
        for (RequestFilter filter : filters) {
            filter.filter(requestBuilder);
        }
        HttpResponse response = httpClient.execute(requestBuilder.build());

        return prepareResponse(response, type);
    }

    private <T> EzTextingResponse<T> doRequest(HttpPost post, Class<T> type) throws IOException {
        return prepareResponse(httpClient.execute(post), type);
    }

    @SuppressWarnings("unchecked")
    private <T> EzTextingResponse<T> prepareResponse(HttpResponse response, Class<T> type) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity == null) {
            LOGGER.debug("received http code {} with null entity, returning empty response", statusCode);
            return EMPTY_RESPONSE;
        }
        String stringResponse = EntityUtils.toString(httpEntity, "UTF-8");
        verifyResponse(statusCode, stringResponse);
        if (StringUtils.isBlank(stringResponse)) {
            LOGGER.debug("received http code {} with empty entity, returning empty response", statusCode);
            return EMPTY_RESPONSE;
        }

        if (type.equals(InputStream.class)) {
            return (EzTextingResponse<T>) EzTextingResponse.withContent(httpEntity.getContent());
        }

        EzTextingResponse<T> model = jsonConverter.deserialize(stringResponse, of(type));
        logDebugPrettyJson("received entity \n{}", model);
        return model;
    }

    @SuppressWarnings("unchecked")
    private void verifyResponse(int statusCode, String stringResponse) throws IOException {
        if (statusCode >= 400) {
            List<String> errors = Collections.EMPTY_LIST;
            if (StringUtils.isNotBlank(stringResponse)) {
                errors = jsonConverter.deserialize(stringResponse, of(Object.class)).getErrors();
            }
            throw new EzTextingApiException(statusCode, errors);

            /*
            TODO vmikhailov currently EZ API returns incorrect codes for almost all operations, so we just throw generic
            EzTextingApiException with http response code and user has to handle that appropriately
            switch (statusCode) {
                case 400:
                    throw new BadRequestException(errors);
                case 401:
                    throw new UnauthorizedException(errors);
                case 403:
                    throw new AccessForbiddenException(errors);
                case 404:
                    throw new ResourceNotFoundException(errors);
                case 500:
                    throw new InternalServerErrorException(errors);
                default:
                    throw new EzTextingApiException(errors);
            }
            */
        }
    }

    private String buildTextPayload(EzTextingModel payload, List<NameValuePair> additionalParams) {
        StringBuilder textPayload = new StringBuilder(authentication.asParamString());
        StringBuilder payloadParams = ClientUtils.buildQueryParams(payload);
        if (payloadParams.length() > 0) {
            textPayload.append('&');
        }
        textPayload.append(payloadParams);

        for (NameValuePair param : additionalParams) {
            textPayload.append('&').append(param.getName()).append("=").append(ClientUtils.encode(param.getValue()));
        }
        return textPayload.toString();
    }

    // makes extra deserialization to get pretty json string, enable only in case of debugging
    private void logDebugPrettyJson(String message, Object... params) throws JsonProcessingException {
        if (LOGGER.isDebugEnabled()) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof EzTextingModel) {
                    String prettyJson = jsonConverter.getMapper().writerWithDefaultPrettyPrinter()
                        .writeValueAsString(params[i]);
                    params[i] = prettyJson;
                }
            }
            LOGGER.debug(message, params);
        }
    }
}
