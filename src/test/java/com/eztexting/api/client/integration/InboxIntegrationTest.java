package com.eztexting.api.client.integration;

import com.eztexting.api.client.ResourceNotFoundException;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.inbox.model.Folder;
import com.eztexting.api.client.api.inbox.model.GetMessagesRequest;
import com.eztexting.api.client.api.inbox.model.GetMessagesRequest.SortProperty;
import com.eztexting.api.client.api.inbox.model.InboxMessage;
import com.eztexting.api.client.api.inbox.model.InboxMessage.MessageType;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class InboxIntegrationTest extends AbstractIntegrationTest {

    public static final long FRIENDS_FOLDER_ID = 5431L;
    public static final long TEST_FOLDER_ID = 5425L;
    public static final long INBOX_MSG_ID = 15168146L;

    @Test
    public void moveMessage() throws Exception {
        client.inboxApi().moveMessage(INBOX_MSG_ID, FRIENDS_FOLDER_ID);
    }

    @Test
    public void moveMessages() throws Exception {
        client.inboxApi().moveMessages(Arrays.asList(INBOX_MSG_ID), TEST_FOLDER_ID);
    }

    @Test
    public void getMessages() throws Exception {
        GetMessagesRequest request = GetMessagesRequest.create()
            .sortBy(SortProperty.RECEIVED_ON)
            .folder(5656L)
            .search("")
            .messageType(MessageType.SMS)
            .sortType(SortType.ASC)
            .itemsPerPage(5)
            .page(0)
            .build();

        List<InboxMessage> messages = client.inboxApi().getMessages(request);
        System.out.println("inbox messages: " + messages);
    }

    @Test
    public void getMessage() throws Exception {
        InboxMessage message = client.inboxApi().getMessage(15168146L);
        System.out.println("inbox message: " + message);
    }

    @Test
    public void deleteMessage() throws Exception {
        client.inboxApi().deleteMessage(123L);
    }

    @Test
    public void foldersCrudOperations() throws Exception {
        Folder created = client.inboxApi().createFolder("test_folder1");
        System.out.println("Created folder: " + created);

        created.setName("test updated1");
        client.inboxApi().updateFolder(created);

        Folder updated = client.inboxApi().getFolder(created.getId());
        System.out.println("Updated folder: " + updated);
        assertEquals(created.getName(), updated.getName());

        List<Folder> folders = client.inboxApi().getFolders();
        System.out.println("Get all folders: " + folders);

        client.inboxApi().deleteFolder(created.getId());

        ex.expect(ResourceNotFoundException.class);
        ex.expectMessage("Sorry, nothing was found");
        client.inboxApi().getFolder(created.getId());
    }
}
