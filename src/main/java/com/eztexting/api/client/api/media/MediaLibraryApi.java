package com.eztexting.api.client.api.media;

import com.eztexting.api.client.ClientUtils;
import com.eztexting.api.client.EzTextingApiException;
import com.eztexting.api.client.EzTextingClientException;
import com.eztexting.api.client.RestApiClient;
import com.eztexting.api.client.api.common.model.CommonGetRequest;
import com.eztexting.api.client.api.media.model.MediaFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.eztexting.api.client.ClientConstants.PLACEHOLDER;

/**
 * API for managing media files in your account
 *
 * @since 1.0
 */
public class MediaLibraryApi {
    private static final String FILES_PATH = "/sending/files?format=json";
    private static final String FILES_ITEM_PATH = "/sending/files/{}?format=json";

    private RestApiClient client;

    public MediaLibraryApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Create a new contact that will be stored in your Ez Texting media library
     *
     * @param url url to download file
     * @return created media file object
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public MediaFile create(String url) {
        List<NameValuePair> params = ClientUtils.asParams("Source", url);
        return client.post(FILES_PATH, MediaFile.class, params).getEntry();
    }

    /**
     * Get a single file stored in your Ez Texting media library.
     *
     * @param id file's id
     * @return single media file
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public MediaFile get(Long id) {
        Validate.notNull(id, "id cannot be null");
        String path = StringUtils.replaceOnce(FILES_ITEM_PATH, PLACEHOLDER, id.toString());
        return client.get(path, MediaFile.class).getEntry();
    }

    /**
     * Get a list of groups stored in your Ez Texting account.
     *
     * @param request request object with sorting and pagination options
     * @return multiple media files
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public List<MediaFile> get(CommonGetRequest request) {
        return client.get(FILES_PATH, MediaFile.class, request).getEntries();
    }

    /**
     * Delete file in your Ez Texting media library
     *
     * @param id file's id
     * @throws EzTextingApiException    in case error has occurred on server side, check provided error description.
     * @throws EzTextingClientException in case error has occurred in client.
     */
    public void delete(Long id) {
        Validate.notNull(id, "id cannot be null");
        client.delete(StringUtils.replaceOnce(FILES_ITEM_PATH, PLACEHOLDER, id.toString()));
    }
}
