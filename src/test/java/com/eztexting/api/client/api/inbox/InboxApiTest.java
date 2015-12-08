package com.eztexting.api.client.api.inbox;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.inbox.model.Folder;
import com.eztexting.api.client.api.inbox.model.GetMessagesRequest;
import com.eztexting.api.client.api.inbox.model.GetMessagesRequest.SortProperty;
import com.eztexting.api.client.api.inbox.model.InboxMessage;
import com.eztexting.api.client.api.inbox.model.InboxMessage.MessageType;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class InboxApiTest extends AbstractApiTest {

    @Test
    public void getMessages() throws Exception {
        String expectedJson = getJsonPayload("/inbox/inboxApi/getMessages.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        GetMessagesRequest request = GetMessagesRequest.create()
            .sortBy(SortProperty.RECEIVED_ON)
            .folder(5656L)
            .search("msg")
            .messageType(MessageType.SMS)
            .sortType(SortType.ASC)
            .itemsPerPage(5)
            .page(0)
            .build();
        List<InboxMessage> messages = client.inboxApi().getMessages(request);
        EzTextingResponse<InboxMessage> ezResponse = new EzTextingResponse<>("Success", 200, messages);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("User=login"));
        assertThat(arg.getURI().toString(), containsString("Password=password"));
        assertThat(arg.getURI().toString(), containsString("Search=msg"));
        assertThat(arg.getURI().toString(), containsString("FolderID=5656"));
        assertThat(arg.getURI().toString(), containsString("Filter=SMS"));
        assertThat(arg.getURI().toString(), containsString("sortBy=ReceivedOn"));
        assertThat(arg.getURI().toString(), containsString("sortDir=asc"));
        assertThat(arg.getURI().toString(), containsString("itemsPerPage=5"));
        assertThat(arg.getURI().toString(), containsString("page=0"));
    }

    @Test
    public void getMessage() throws Exception {
        String expectedJson = getJsonPayload("/inbox/inboxApi/getMessage.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        InboxMessage message = client.inboxApi().getMessage(10L);
        EzTextingResponse<InboxMessage> ezResponse = new EzTextingResponse<>("Success", 200, message);
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
    public void getMessageNullId() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("id cannot be null");
        client.inboxApi().getMessage(null);
    }

    @Test
    public void deleteMessage() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.inboxApi().deleteMessage(10L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/incoming-messages/10?format=json"));
    }

    @Test
    public void deleteMessageNullId() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("id cannot be null");
        client.inboxApi().deleteMessage(null);
    }

    @Test
    public void moveMessage() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.inboxApi().moveMessage(10L, 20L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("ID=10"));
        assertThat(requestBody, containsString("FolderID=20"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void moveMessageNullId() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("id cannot be null");
        client.inboxApi().moveMessage(null, 20L);

        ex.expect(NullPointerException.class);
        ex.expectMessage("folderId cannot be null");
        client.inboxApi().moveMessage(10L, null);
    }

    @Test
    public void moveMessages() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.inboxApi().moveMessages(Arrays.asList(1L, 2L, 3L), 20L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("ID[]=1"));
        assertThat(requestBody, containsString("ID[]=2"));
        assertThat(requestBody, containsString("ID[]=3"));
        assertThat(requestBody, containsString("FolderID=20"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void moveMessagesNullId() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("folderId cannot be null");
        client.inboxApi().moveMessages(Collections.<Long>emptyList(), null);
    }

    @Test
    public void createFolder() throws Exception {
        String expectedJson = getJsonPayload("/inbox/inboxApi/createFolder.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Folder created = client.inboxApi().createFolder("folder");
        // set to null because original response doesn't have Name attribute
        created.setName(null);
        EzTextingResponse<Folder> ezResponse = new EzTextingResponse<>("Success", 201, created);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=folder"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void updateFolderBlankName() throws Exception {
        ex.expect(IllegalArgumentException.class);
        ex.expectMessage("name cannot be blank");
        client.inboxApi().createFolder(" ");
    }

    @Test
    public void updateFolder() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        Folder folder = new Folder("new_folder");
        folder.setId(10L);
        client.inboxApi().updateFolder(folder);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/10?"));
        assertThat(requestBody, containsString("Name=new_folder"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void updateFolderNullId() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("id cannot be null");
        client.inboxApi().updateFolder(new Folder());
    }

    @Test
    public void getFolder() throws Exception {
        String expectedJson = getJsonPayload("/inbox/inboxApi/getFolder.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Folder folder = client.inboxApi().getFolder(10L);
        EzTextingResponse<Folder> ezResponse = new EzTextingResponse<>("Success", 200, folder);
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
    public void getFolderNullId() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("id cannot be null");
        client.inboxApi().getFolder(null);
    }

    @Test
    public void getFolders() throws Exception {
        String expectedJson = getJsonPayload("/inbox/inboxApi/getFolders.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        List<Folder> folders = client.inboxApi().getFolders();
        EzTextingResponse<Folder> ezResponse = new EzTextingResponse<>("Success", 200, folders);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/messages-folders?format=json"));
    }

    @Test
    public void deleteFolder() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.inboxApi().deleteFolder(10L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/10?"));
    }

    @Test
    public void deleteFolderNullId() throws Exception {
        ex.expect(NullPointerException.class);
        ex.expectMessage("id cannot be null");
        client.inboxApi().deleteFolder(null);
    }
}

