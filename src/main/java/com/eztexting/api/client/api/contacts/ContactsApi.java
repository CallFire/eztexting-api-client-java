package com.eztexting.api.client.api.contacts;

import com.eztexting.api.client.EzTextingApiException;
import com.eztexting.api.client.EzTextingClientException;
import com.eztexting.api.client.RestApiClient;
import com.eztexting.api.client.api.contacts.model.Contact;
import com.eztexting.api.client.api.contacts.model.GetContactsRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * API for managing contacts inside your account
 *
 * @since 1.0
 */
public class ContactsApi {
    private static final String CONTACTS_PATH = "/contacts?format=json";
    private static final String CONTACTS_ITEM_PATH = "/contacts/{}?format=json";

    private RestApiClient client;

    public ContactsApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Create a new contact that will be stored in your Ez Texting account
     *
     * @param contact contact to create
     * @return created contact
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Contact create(Contact contact) {
        return client.post(CONTACTS_PATH, Contact.class, contact).getEntry();
    }

    /**
     * Update a contact that is stored in your Ez Texting account
     *
     * @param contact contact to update
     * @return updated contact
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Contact update(Contact contact) {
        Validate.notNull(contact.getId(), "id cannot be null");
        String path = StringUtils.replaceOnce(CONTACTS_ITEM_PATH, PLACEHOLDER, contact.getId());
        return client.post(path, Contact.class, contact).getEntry();
    }

    /**
     * Get a single contact stored in your Ez Texting account.
     *
     * @param id contact's id
     * @return single contact
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Contact get(String id) {
        Validate.notNull(id, "id cannot be null");
        return client.get(StringUtils.replaceOnce(CONTACTS_ITEM_PATH, PLACEHOLDER, id), Contact.class).getEntry();
    }

    /**
     * Get a list of contacts stored in your Ez Texting account.
     *
     * @param request request object with sorting and pagination options
     * @return contacts that were found
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public List<Contact> get(GetContactsRequest request) {
        return client.get(CONTACTS_PATH, Contact.class, request).getEntries();
    }

    /**
     * Delete a contact that is stored in your Ez Texting account
     *
     * @param id contact's id
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void delete(String id) {
        Validate.notNull(id, "id cannot be null");
        client.delete(StringUtils.replaceOnce(CONTACTS_ITEM_PATH, PLACEHOLDER, id));
    }
}
