package com.eztexting.api.client;

import com.eztexting.api.client.api.contacts.ContactsApi;
import com.eztexting.api.client.api.groups.GroupsApi;
import com.eztexting.api.client.api.keywords.KeywordsApi;
import com.eztexting.api.client.auth.RequestParamAuth;
import com.eztexting.api.client.api.messaging.MessagingApi;

import java.io.IOException;
import java.util.Properties;

import static com.eztexting.api.client.ClientConstants.CLIENT_CONFIG_FILE;

/**
 * EzTexting API client
 * <p>
 * <b>Authentication:</b> the CallFire API V2 uses HTTP Basic Authentication to verify
 * the user of an endpoint. A generated username/password API credential from your
 * account settings is required.
 * </p>
 * <b>Errors:</b> codes in the 400s range detail all of the errors a EzTexting Developer could
 * encounter while using the API. Bad Request, Rate Limit Reached, and Unauthorized
 * are some of the sorts of responses in the 400s block. Codes in the 500s range are
 * error responses from the EzTexting system. If an error has occurred anywhere in
 * the execution of a resource that was not due to user input, a 500 response
 * will be returned with a corresponding JSON error body. In that body will contain a message
 * detailing what went wrong.
 * API client methods throw 2 types of exceptions: API and client itself. API exceptions are mapped to
 * HTTP response codes:
 * <ul>
 * <li>{@link BadRequestException} - 400 - Bad request, the request was formatted improperly.</li>
 * <li>{@link UnauthorizedException} - 401 - Unauthorized, API Key missing or invalid.</li>
 * <li>{@link AccessForbiddenException} - 403 - Forbidden, insufficient permissions.</li>
 * <li>{@link ResourceNotFoundException} - 404 - NOT FOUND, the resource requested does not exist.</li>
 * <li>{@link InternalServerErrorException} - 500 - Internal Server Error.</li>
 * <li>{@link EzTextingApiException} - other error codes mapped to base exception.</li>
 * </ul>
 * Client exceptions:
 * <ul>
 * <li>{@link EzTextingClientException} - if error occurred inside client</li>
 * </ul>
 *
 * @author Vladimir Mikhailov (email: vmikhailov@callfire.com)
 * @see <a href="https://www.eztexting.com/developers/sms-api-documentation/rest">EzTexting API documentation</a>
 * @see <a href="https://github.com/CallFire/eztexting-api-client-java/blob/master/docs/api/ApiExamples.adoc">HowTos and examples</a>
 * @see <a href="http://stackoverflow.com/questions/tagged/callfire">Stackoverflow community questions</a>
 * @since 1.0
 */
public class EzTextingClient {
    private static Properties clientConfig = new Properties();

    static {
        loadConfig();
    }

    private RestApiClient restApiClient;

    private MessagingApi messagingApi;
    private KeywordsApi keywordsApi;
    private ContactsApi contactsApi;
    private GroupsApi groupsApi;

    /**
     * Constructs callfire client
     *
     * @param username api login
     * @param password api password
     */
    public EzTextingClient(String username, String password) {
        restApiClient = new RestApiClient(new RequestParamAuth(username, password));
    }

    /**
     * Get REST api client which uses Apache httpclient inside
     *
     * @return rest client
     */
    public RestApiClient getRestApiClient() {
        return restApiClient;
    }

    /**
     * Get client configuration
     *
     * @return configuration properties
     */
    public static Properties getClientConfig() {
        return clientConfig;
    }

    /**
     * Get messaging APIs for sending SMS/MMS messages, get short and detailed delivery reports
     *
     * @return endpoint object
     */
    public MessagingApi messagingApi() {
        if (messagingApi == null) {
            messagingApi = new MessagingApi(restApiClient);
        }
        return messagingApi;
    }

    /**
     * Get keywords APIs for rent, configure, cancel your keywords
     *
     * @return endpoint object
     */
    public KeywordsApi keywordsApi() {
        if (keywordsApi == null) {
            keywordsApi = new KeywordsApi(restApiClient);
        }
        return keywordsApi;
    }

    /**
     * Get contacts API for managing your contacts
     *
     * @return endpoint object
     */
    public ContactsApi contactsApi() {
        if (contactsApi == null) {
            contactsApi = new ContactsApi(restApiClient);
        }
        return contactsApi;
    }

    /**
     * Get groups APIs for managing your contact groups
     *
     * @return endpoint object
     */
    public GroupsApi groupsApi() {
        if (groupsApi == null) {
            groupsApi = new GroupsApi(restApiClient);
        }
        return groupsApi;
    }

    private static void loadConfig() {
        try {
            clientConfig.load(EzTextingClient.class.getResourceAsStream(CLIENT_CONFIG_FILE));
        } catch (IOException e) {
            throw new EzTextingClientException("Cannot instantiate EzTexting Client.", e);
        }
    }
}
