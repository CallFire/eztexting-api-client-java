package com.eztexting.api.client.integration;

import com.eztexting.api.client.AccessForbiddenException;
import com.eztexting.api.client.api.common.model.CommonGetRequest;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.media.model.MediaFile;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class MediaLibraryIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void crudOperations() throws Exception {
        MediaFile created = client.mediaLibraryApi().create(
            "https://eztxting.s3.amazonaws.com/188814/mms/train_1449507791.mp3");

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

        ex.expect(AccessForbiddenException.class);
        ex.expectMessage("Sorry, nothing was found");
        client.mediaLibraryApi().get(created.getId());
    }
}
