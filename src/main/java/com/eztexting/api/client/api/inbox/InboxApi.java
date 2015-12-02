package com.eztexting.api.client.api.inbox;

import com.eztexting.api.client.*;
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
    private static final String FOLDERS_ITEM_PATH = "/contacts/{}?format=json";
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
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public InboxMessage getMessage(Long id) {
        Validate.notNull(id, "id cannot be null");
        String path = StringUtils.replaceOnce(MESSAGES_ITEM_PATH, PLACEHOLDER, id.toString());
        return client.get(path, InboxMessage.class).getEntry();
    }

    /**
     * Get all incoming text messages in your Ez Texting Inbox
     *
     * @return list with messages
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public List<InboxMessage> getMessages(GetMessagesRequest request) {
        return client.get(MESSAGES_PATH, InboxMessage.class, request).getEntries();
    }

    /**
     * Moves an incoming text message in your Ez Texting Inbox to a specified folder.
     * Note: You may include multiple Message IDs to move multiple messages to same folder in a single API call.
     *
     * @param id       message id
     * @param folderId folder id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public void moveMessage(Long id, Long folderId) {
        Validate.notNull(id, "id cannot be null");
        Validate.notNull(folderId, "folderId cannot be null");
        List<NameValuePair> params = ClientUtils.asParams("ID", id.toString(), "FolderID", folderId.toString());
        client.post(MOVE_MESSAGE_PATH, Object.class, params);
    }

    /**
     * Delete an incoming text message in your Ez Texting Inbox
     *
     * @param id message's id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
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
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
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
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public void updateFolder(Folder folder) {
        Validate.notNull(folder.getId(), "id cannot be null");
        String path = StringUtils.replaceOnce(FOLDERS_ITEM_PATH, PLACEHOLDER, folder.getId().toString());
        client.post(path, Object.class, folder);
    }

    /**
     * Get a single folder in your Ez Texting Inbox
     *
     * @param id folder's id
     * @return folder
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
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
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public List<Folder> getAllFolders() {
        return client.get(FOLDERS_PATH, Folder.class).getEntries();
    }

    /**
     * Delete a Folder in your Ez Texting Inbox
     *
     * @param id folder's id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public void deleteFolder(Long id) {
        Validate.notNull(id, "id cannot be null");
        client.delete(StringUtils.replaceOnce(FOLDERS_ITEM_PATH, PLACEHOLDER, id.toString()));
    }
}
