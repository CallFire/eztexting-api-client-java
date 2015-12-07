package com.eztexting.api.client.api.media;

import com.eztexting.api.client.api.AbstractApiTest;
import com.eztexting.api.client.api.common.model.CommonGetRequest;
import com.eztexting.api.client.api.common.model.EzTextingResponse;
import com.eztexting.api.client.api.common.model.SortType;
import com.eztexting.api.client.api.media.model.MediaFile;
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

public class MediaLibraryApiTest extends AbstractApiTest {

    @Test
    public void create() throws Exception {
        String expectedJson = getJsonPayload("/media/mediaLibraryApi/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        MediaFile created = client.mediaLibraryApi().create("http://file-storage.com/file.wav");
        EzTextingResponse<MediaFile> ezResponse = new EzTextingResponse<>("Success", 200, created);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Source=" + encode("http://file-storage.com/file.wav")));
        assertThat(requestBody, containsString("User=login"));
        assertThat(requestBody, containsString("Password=password"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/media/mediaLibraryApi/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        MediaFile mediaFile = client.mediaLibraryApi().get(10L);
        EzTextingResponse<MediaFile> ezResponse = new EzTextingResponse<>("Success", 200, mediaFile);
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
    public void getAllFiles() throws Exception {
        String expectedJson = getJsonPayload("/media/mediaLibraryApi/getAllFiles.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        CommonGetRequest request = CommonGetRequest.create()
            .sortType(SortType.ASC)
            .itemsPerPage(10)
            .page(5)
            .build();

        List<MediaFile> files = client.mediaLibraryApi().get(request);
        EzTextingResponse<MediaFile> ezResponse = new EzTextingResponse<>("Success", 200, files);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(ezResponse), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("sortDir=asc"));
        assertThat(arg.getURI().toString(), containsString("itemsPerPage=10"));
        assertThat(arg.getURI().toString(), containsString("page=5"));
    }

    @Test
    public void delete() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.mediaLibraryApi().delete(10L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/10?"));
    }
}
