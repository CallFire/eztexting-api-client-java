package com.eztexting.api.client.api.messaging.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * MMS message to send
 */
public class MmsMessage extends AbstractMessage {
    private Long fileId;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("fileId", fileId)
            .toString();
    }
}
