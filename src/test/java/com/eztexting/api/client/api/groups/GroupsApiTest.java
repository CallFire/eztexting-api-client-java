package com.eztexting.api.client.api.groups;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.groups.model.GetGroupsRequest;
import com.eztexting.api.client.api.groups.model.Group;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

import static com.eztexting.api.client.ClientUtils.encode;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class GroupsApiTest extends AbstractApiTest {

    @Test
    public void create() throws Exception {
        String expectedJson = getJsonPayload("/groups/groupsApi/create.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Group group = new Group();
        group.setName("group 1");
        group.setNote("note 1");
        Group created = client.groupsApi().create(group);
        EzTextingResponse<Group> ezResponse = new EzTextingResponse<>("Success", 201, created);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=" + encode("group 1")));
        assertThat(requestBody, containsString("Note=" + encode("note 1")));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void update() throws Exception {
        String expectedJson = getJsonPayload("/groups/groupsApi/update.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Group group = new Group();
        group.setId(10L);
        group.setName("group 2");
        group.setNote("note 2");
        Group updated = client.groupsApi().update(group);
        EzTextingResponse<Group> ezResponse = new EzTextingResponse<>("Success", 201, updated);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/10?"));
        assertThat(requestBody, containsString("Name=" + encode("group 2")));
        assertThat(requestBody, containsString("Note=" + encode("note 2")));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/groups/groupsApi/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Group group = client.groupsApi().get(10L);
        EzTextingResponse<Group> ezResponse = new EzTextingResponse<>("Success", 200, group);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("User=login"));
        assertThat(arg.getURI().toString(), containsString("Password=password"));
        assertThat(arg.getURI().toString(), containsString("/10?"));
    }

    @Test
    public void getAllGroups() throws Exception {
        String expectedJson = getJsonPayload("/groups/groupsApi/getAllGroups.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        GetGroupsRequest request = GetGroupsRequest.create()
            .sortBy(GetGroupsRequest.SortProperty.NAME)
            .sortType(SortType.ASC)
            .itemsPerPage(10)
            .page(5)
            .build();

        List<Group> groups = client.groupsApi().get(request);
        EzTextingResponse<Group> ezResponse = new EzTextingResponse<>("Success", 200, groups);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("sortBy=NAME"));
        assertThat(arg.getURI().toString(), containsString("sortDir=asc"));
        assertThat(arg.getURI().toString(), containsString("itemsPerPage=10"));
        assertThat(arg.getURI().toString(), containsString("page=5"));
    }

    @Test
    public void delete() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.groupsApi().delete(10L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/10?"));
    }
}
