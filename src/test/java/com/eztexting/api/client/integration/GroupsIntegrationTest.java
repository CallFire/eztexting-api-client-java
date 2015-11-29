package com.eztexting.api.client.integration;

import com.eztexting.api.client.AccessForbiddenException;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.groups.GetGroupsRequest;
import com.eztexting.api.client.api.groups.model.Group;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GroupsIntegrationTest extends AbstractIntegrationTest {

    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.com.eztexting", "DEBUG");

    }


    @Test
    public void crudOperations() throws Exception {


        Group group1 = new Group();
        group1.setName("test group1");
        group1.setNote("test note1");
        Group created = client.groupsApi().create(group1);

        System.out.println("Created group: " + created);

        created.setName("test updated");
        Group updated = client.groupsApi().update(created);
        System.out.println("Updated group: " + updated);
        assertEquals(created.getName(), updated.getName());

        updated = client.groupsApi().get(updated.getId());
        System.out.println("Get updated group: " + updated);

        GetGroupsRequest request = GetGroupsRequest.create()
            .sortType(SortType.ASC)
            .itemsPerPage(1)
            .page(0)
            .build();
        List<Group> groups = client.groupsApi().get(request);
        System.out.println("Get all groups: " + groups);

        client.groupsApi().delete(created.getId());

        ex.expect(AccessForbiddenException.class);
        ex.expectMessage("Sorry, nothing was found");
        client.groupsApi().get(created.getId());
    }
}
