package com.eztexting.api.client.api.inbox;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.contacts.model.Contact;
import com.eztexting.api.client.api.contacts.model.GetContactsRequest;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.List;

import static com.eztexting.api.client.ClientUtils.encode;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

public class InboxApiTest extends AbstractApiTest {

















    @Test
    public void create() throws Exception {
        String expectedJson = getJsonPayload("/contacts/contactsApi/create.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Contact contact = new Contact();
        contact.setFirstName("Piglet");
        contact.setLastName("Notail");
        contact.setPhoneNumber("2123456785");
        contact.setEmail("piglet@small-animals-alliance.org");
        contact.setNote("It is hard to be brave, when you are only a Very Small Animal.");
        contact.setGroups(Arrays.asList("Friends", "Neighbors"));

        Contact created = client.contactsApi().create(contact);
        EzTextingResponse<Contact> ezResponse = new EzTextingResponse<>("Success", 201, created);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("FirstName=Piglet"));
        assertThat(requestBody, containsString("LastName=Notail"));
        assertThat(requestBody, containsString("PhoneNumber=2123456785"));
        assertThat(requestBody, containsString("Email=" + encode("piglet@small-animals-alliance.org")));
        assertThat(requestBody,
            containsString("Note=" + encode("It is hard to be brave, when you are only a Very Small Animal.")));
        assertThat(requestBody, containsString("Groups[]=Friends"));
        assertThat(requestBody, containsString("Groups[]=Neighbors"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void update() throws Exception {
        String expectedJson = getJsonPayload("/contacts/contactsApi/update.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Contact contact = new Contact();
        contact.setId("4f0b5720734fada368000000");
        contact.setOptOut(true);
        contact.setFirstName("Piglet");
        contact.setLastName("Notail");
        contact.setPhoneNumber("2123456785");
        contact.setEmail("piglet@small-animals-alliance.org");
        contact.setNote("It is hard to be brave, when you are only a Very Small Animal.");
        contact.setGroups(Arrays.asList("Friends", "Neighbors"));
        Contact updated = client.contactsApi().update(contact);
        EzTextingResponse<Contact> ezResponse = new EzTextingResponse<>("Success", 200, updated);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/4f0b5720734fada368000000?"));
        assertThat(requestBody, containsString("FirstName=Piglet"));
        assertThat(requestBody, containsString("LastName=Notail"));
        assertThat(requestBody, containsString("PhoneNumber=2123456785"));
        assertThat(requestBody, containsString("Email=" + encode("piglet@small-animals-alliance.org")));
        assertThat(requestBody,
            containsString("Note=" + encode("It is hard to be brave, when you are only a Very Small Animal.")));
        assertThat(requestBody, containsString("OptOut=1"));
        assertThat(requestBody, containsString("Groups[]=Friends"));
        assertThat(requestBody, containsString("Groups[]=Neighbors"));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/contacts/contactsApi/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Contact contact = client.contactsApi().get("4f0b5720734fada368000000");
        EzTextingResponse<Contact> ezResponse = new EzTextingResponse<>("Success", 200, contact);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("User=login"));
        assertThat(arg.getURI().toString(), containsString("Password=password"));
        assertThat(arg.getURI().toString(), containsString("/4f0b5720734fada368000000?"));
    }

    @Test
    public void getAllContacts() throws Exception {
        String expectedJson = getJsonPayload("/contacts/contactsApi/getAllContacts.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        GetContactsRequest request = GetContactsRequest.create()
            .query(GetContactsRequest.QueryProperty.FIRST_NAME)
            .source(Contact.SourceType.MANUAL)
            .optOut(true)
            .group("My Friends")
            .sortBy(GetContactsRequest.SortProperty.CREATED_AT)
            .sortType(SortType.DESC)
            .itemsPerPage(10)
            .page(10)
            .build();

        List<Contact> contacts = client.contactsApi().get(request);
        EzTextingResponse<Contact> ezResponse = new EzTextingResponse<>("Success", 200, contacts);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("query=FirstName"));
        assertThat(arg.getURI().toString(), containsString("source=" + encode("Manually Added")));
        assertThat(arg.getURI().toString(), containsString("optout=true"));
        assertThat(arg.getURI().toString(), containsString("group=" + encode("My Friends")));
        assertThat(arg.getURI().toString(), containsString("sortBy=CreatedAt"));
        assertThat(arg.getURI().toString(), containsString("sortDir=desc"));
        assertThat(arg.getURI().toString(), containsString("itemsPerPage=10"));
        assertThat(arg.getURI().toString(), containsString("page=10"));
    }

    @Test
    public void delete() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.contactsApi().delete("4f0b5720734fada368000000");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/4f0b5720734fada368000000?"));
    }
}

