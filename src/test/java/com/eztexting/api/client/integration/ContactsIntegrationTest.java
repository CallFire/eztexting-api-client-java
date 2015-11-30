package com.eztexting.api.client.integration;

import com.eztexting.api.client.AccessForbiddenException;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.contacts.model.Contact;
import com.eztexting.api.client.api.contacts.model.Contact.SourceType;
import com.eztexting.api.client.api.contacts.model.GetContactsRequest;
import com.eztexting.api.client.api.contacts.model.GetContactsRequest.QueryProperty;
import com.eztexting.api.client.api.contacts.model.GetContactsRequest.SortProperty;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Ignore
public class ContactsIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void crudOperations() throws Exception {
        Contact contact = new Contact();
        contact.setFirstName("Piglet");
        contact.setLastName("Notail");
        contact.setPhoneNumber("2123456786");
        contact.setEmail("piglet@small-animals-alliance.org");
        contact.setNote("It is hard to be brave, when you are only a Very Small Animal.");
        contact.setGroups(Arrays.asList("Friends", "Neighbors"));

        Contact created = client.contactsApi().create(contact);
        assertFalse(BooleanUtils.toBoolean(created.getOptOut()));

        System.out.println("Created contact: " + created);

        created.setFirstName(created.getFirstName() + "_upd");
        // opted out contacts cannot be updated or deleted
        //        created.setOptOut(true);
        Contact updated = client.contactsApi().update(created);
        System.out.println("Updated contact: " + updated);
        //        assertTrue(updated.getOptOut());
        assertEquals(created.getFirstName(), updated.getFirstName());

        updated = client.contactsApi().get(updated.getId());
        System.out.println("Get updated contact: " + updated);
        //        assertTrue(updated.getOptOut());

        GetContactsRequest request = GetContactsRequest.create()
            .sortType(SortType.ASC)
            .itemsPerPage(2)
            .page(0)
            .query(QueryProperty.FIRST_NAME)
            .source(SourceType.MANUAL)
            .group("Friends")
            .sortBy(SortProperty.CREATED_AT)
            .build();
        List<Contact> groups = client.contactsApi().get(request);
        System.out.println("Get all contacts: " + groups);

        client.contactsApi().delete(created.getId());

        ex.expect(AccessForbiddenException.class);
        ex.expectMessage("Sorry, nothing was found");
        client.contactsApi().get(created.getId());
    }
}
