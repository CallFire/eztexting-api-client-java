package com.eztexting.api.client.api.inbox;

import com.eztexting.api.client.ClientUtils;
import com.eztexting.api.client.EzTextingApiException;
import com.eztexting.api.client.EzTextingClientException;
import com.eztexting.api.client.RestApiClient;
import com.eztexting.api.client.api.inbox.model.Folder;
import com.eztexting.api.client.api.inbox.model.GetMessagesRequest;
import com.eztexting.api.client.api.inbox.model.InboxMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * API for managing your Inbox
 *
 * @since 1.0
 */
public class InboxApi {
    private static final String FOLDERS_PATH = "/messages-folders?format=json";
    private static final String FOLDERS_ITEM_PATH = "/messages-folders/{}?format=json";
    private static final String MESSAGES_PATH = "/incoming-messages?format=json";
    private static final String MESSAGES_ITEM_PATH = "/incoming-messages/{}?format=json";
    private static final String MOVE_MESSAGE_PATH = "/incoming-messages/?format=json&_method=move-to-folder";

    private RestApiClient client;

    public InboxApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Get a single incoming text messages in your Ez Texting Inbox
     *
     * @param id id of message
     * @return incoming message
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public InboxMessage getMessage(Long id) {
        Validate.notNull(id, "id cannot be null");
        String path = StringUtils.replaceOnce(MESSAGES_ITEM_PATH, PLACEHOLDER, id.toString());
        return client.get(path, InboxMessage.class).getEntry();
    }

    /**
     * Get all incoming text messages in your Ez Texting Inbox
     *
     * @param request request with filtering fields
     * @return list with messages
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public List<InboxMessage> getMessages(GetMessagesRequest request) {
        return client.get(MESSAGES_PATH, InboxMessage.class, request).getEntries();
    }

    /**
     * Moves an incoming text message in your Ez Texting Inbox to a specified folder.
     *
     * @param id       message id
     * @param folderId folder id
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void moveMessage(Long id, Long folderId) {
        Validate.notNull(id, "id cannot be null");
        Validate.notNull(folderId, "folderId cannot be null");
        List<NameValuePair> params = ClientUtils.asParams("ID", id.toString(), "FolderID", folderId.toString());
        client.post(MOVE_MESSAGE_PATH, Object.class, params);
    }

    /**
     * Moves several incoming text messages in your Ez Texting Inbox to a specified folder.
     *
     * @param ids      list with message ids
     * @param folderId folder id
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void moveMessages(List<Long> ids, Long folderId) {
        Validate.notNull(folderId, "folderId cannot be null");
        List<NameValuePair> params = ClientUtils.asParams("ID", ids, "FolderID", folderId.toString());
        client.post(MOVE_MESSAGE_PATH, Object.class, params);
    }

    /**
     * Delete an incoming text message in your Ez Texting Inbox
     *
     * @param id message's id
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void deleteMessage(Long id) {
        Validate.notNull(id, "id cannot be null");
        client.delete(StringUtils.replaceOnce(MESSAGES_ITEM_PATH, PLACEHOLDER, id.toString()));
    }

    /**
     * Create a folder in your Ez Texting Inbox
     *
     * @param name name of the folder to create
     * @return id of created folder
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Folder createFolder(String name) {
        Validate.notBlank(name, "name cannot be blank");
        List<NameValuePair> params = ClientUtils.asParams("Name", name);
        Folder folder = client.post(FOLDERS_PATH, Folder.class, params).getEntry();
        folder.setName(name);
        return folder;
    }

    /**
     * Update the name of a Folder in your Ez Texting Inbox
     *
     * @param folder folder to update
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void updateFolder(Folder folder) {
        Validate.notNull(folder.getId(), "id cannot be null");
        String path = StringUtils.replaceOnce(FOLDERS_ITEM_PATH, PLACEHOLDER, folder.getId().toString());
        client.post(path, String.class, folder);
    }

    /**
     * Get a single folder in your Ez Texting Inbox
     *
     * @param id folder's id
     * @return folder
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Folder getFolder(Long id) {
        Validate.notNull(id, "id cannot be null");
        String path = StringUtils.replaceOnce(FOLDERS_ITEM_PATH, PLACEHOLDER, id.toString());
        return client.get(path, Folder.class).getEntry();
    }

    /**
     * Get all Folders in your Ez Texting Inbox
     *
     * @return list with folders
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public List<Folder> getFolders() {
        return client.get(FOLDERS_PATH, Folder.class).getEntries();
    }

    /**
     * Delete a Folder in your Ez Texting Inbox
     *
     * @param id folder's id
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void deleteFolder(Long id) {
        Validate.notNull(id, "id cannot be null");
        client.delete(StringUtils.replaceOnce(FOLDERS_ITEM_PATH, PLACEHOLDER, id.toString()));
    }
}
