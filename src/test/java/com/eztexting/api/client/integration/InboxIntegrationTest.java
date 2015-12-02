package com.eztexting.api.client.integration;

import com.eztexting.api.client.ResourceNotFoundException;
import com.eztexting.api.client.api.inbox.model.Folder;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class InboxIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void moveMessage() throws Exception {
        // TODO
    }

    @Test
    public void getMessages() throws Exception {
        // TODO
    }

    @Test
    public void getMessage() throws Exception {
        // TODO
    }

    @Test
    public void deleteMessage() throws Exception {
        // TODO
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
