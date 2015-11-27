package com.eztexting.api.client.api.groups;

import com.eztexting.api.client.*;
import com.eztexting.api.client.api.groups.model.Group;
import com.eztexting.api.client.api.groups.model.GroupsApiResponse;
import org.apache.commons.lang3.Validate;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * API for managing contact groups inside your account
 *
 * @since 1.0
 */
public class GroupsApi {
    private static final String GROUPS_PATH = "/groups?format=json";
    private static final String GROUPS_ITEM_PATH = "/groups/{}?format=json";

    private RestApiClient client;

    public GroupsApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Create a new group that will be stored in your Ez Texting account
     *
     * @param group group to create
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public GroupsApiResponse create(Group group) {
        return client.post(GROUPS_PATH, GroupsApiResponse.class, group).getEntry();
    }

    /**
     * Update a group that is stored in your Ez Texting account
     *
     * @param group group to update
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public GroupsApiResponse update(Group group) {
        Validate.notNull(group.getId(), "id cannot be null");
        String path = GROUPS_ITEM_PATH.replaceFirst(PLACEHOLDER, group.getId().toString());
        return client.post(path, GroupsApiResponse.class, group).getEntry();
    }

    /**
     * Get a single group stored in your Ez Texting account.
     *
     * @param id group's id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public GroupsApiResponse get(Long id) {
        Validate.notNull(id, "id cannot be null");
        String path = GROUPS_ITEM_PATH.replaceFirst(PLACEHOLDER, id.toString());
        return client.get(path, GroupsApiResponse.class).getEntry();
    }

    /**
     * Get a list of groups stored in your Ez Texting account.
     *
     * @param request request object with sorting and pagination options
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public List<GroupsApiResponse> get(GetAllGroupsRequest request) {
        return client.get(GROUPS_PATH, GroupsApiResponse.class, request).getEntries();
    }

    /**
     * Delete a group that is stored in your Ez Texting account
     *
     * @param id group's id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws EzTextingApiException        in case HTTP response code is something different from codes listed above.
     * @throws EzTextingClientException     in case error has occurred in client.
     */
    public void delete(Long id) {
        Validate.notNull(id, "id cannot be null");
        client.delete(GROUPS_ITEM_PATH.replaceFirst(PLACEHOLDER, id.toString()));
    }
}
