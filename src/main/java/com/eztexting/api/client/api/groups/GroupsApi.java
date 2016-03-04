package com.eztexting.api.client.api.groups;

import com.eztexting.api.client.EzTextingApiException;
import com.eztexting.api.client.EzTextingClientException;
import com.eztexting.api.client.RestApiClient;
import com.eztexting.api.client.api.groups.model.GetGroupsRequest;
import com.eztexting.api.client.api.groups.model.Group;
import org.apache.commons.lang3.StringUtils;
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
     * @return created group
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Group create(Group group) {
        return client.post(GROUPS_PATH, Group.class, group).getEntry();
    }

    /**
     * Update a group that is stored in your Ez Texting account
     *
     * @param group group to update
     * @return updated group
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Group update(Group group) {
        Validate.notNull(group.getId(), "id cannot be null");
        String path = StringUtils.replaceOnce(GROUPS_ITEM_PATH, PLACEHOLDER, group.getId().toString());
        return client.post(path, Group.class, group).getEntry();
    }

    /**
     * Get a single group stored in your Ez Texting account.
     *
     * @param id group's id
     * @return particular group
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public Group get(Long id) {
        Validate.notNull(id, "id cannot be null");
        String path = StringUtils.replaceOnce(GROUPS_ITEM_PATH, PLACEHOLDER, id.toString());
        return client.get(path, Group.class).getEntry();
    }

    /**
     * Get a list of groups stored in your Ez Texting account.
     *
     * @param request request object with sorting and pagination options
     * @return group that were found
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public List<Group> get(GetGroupsRequest request) {
        return client.get(GROUPS_PATH, Group.class, request).getEntries();
    }

    /**
     * Delete a group that is stored in your Ez Texting account
     *
     * @param id group's id
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void delete(Long id) {
        Validate.notNull(id, "id cannot be null");
        client.delete(StringUtils.replaceOnce(GROUPS_ITEM_PATH, PLACEHOLDER, id.toString()));
    }
}
