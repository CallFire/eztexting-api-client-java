package com.eztexting.api.client.integration;

import com.eztexting.api.client.EzTextingApiException;
import com.eztexting.api.client.api.common.model.CommonGetRequest;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.media.model.MediaFile;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class MediaLibraryIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void crudOperations() throws Exception {
        MediaFile created = client.mediaLibraryApi().create(
            "https://www.eztexting.com/sites/all/themes/ez/images/ez-texting-logo.png");

        System.out.println("Created file: " + created);

        MediaFile file = client.mediaLibraryApi().get(created.getId());
        System.out.println("Get file: " + file);

        CommonGetRequest request = CommonGetRequest.create()
            .sortType(SortType.ASC)
            .itemsPerPage(2)
            .page(0)
            .build();
        List<MediaFile> files = client.mediaLibraryApi().get(request);
        System.out.println("Get all files: " + files);

        client.mediaLibraryApi().delete(created.getId());

        ex.expect(EzTextingApiException.class);
        ex.expectMessage("Sorry, nothing was found");
        client.mediaLibraryApi().get(created.getId());
    }

    @Test
    public void testFileUpload() {
        File file = new File("src/test/resources/file-examples/train.mp3");
        assertTrue(file.exists());
        MediaFile uploadedFile = client.mediaLibraryApi().create(file);
        assertNotNull(uploadedFile);
        System.out.println("Uploaded file: " + uploadedFile);
        MediaFile downloadedFile = client.mediaLibraryApi().get(uploadedFile.getId());
        System.out.println("Downloaded file: " + downloadedFile);
        assertEquals(uploadedFile, downloadedFile);
        client.mediaLibraryApi().delete(uploadedFile.getId());
        ex.expect(EzTextingApiException.class);
        ex.expectMessage("Sorry, nothing was found");
        client.mediaLibraryApi().get(uploadedFile.getId());
    }
}
