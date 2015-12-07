package com.eztexting.api.client.api.messaging.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VoiceMessage extends AbstractMessage {
    @JsonProperty("CallerPhonenumber")
    private String callerPhoneNumber;
    private String name;
    private String voiceFile;
    private String voiceSource;

    public String getCallerPhoneNumber() {
        return callerPhoneNumber;
    }

    public void setCallerPhoneNumber(String callerPhoneNumber) {
        this.callerPhoneNumber = callerPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVoiceFile() {
        return voiceFile;
    }

    public void setVoiceFile(String voiceFile) {
        this.voiceFile = voiceFile;
    }

    public String getVoiceSource() {
        return voiceSource;
    }

    public void setVoiceSource(String voiceSource) {
        this.voiceSource = voiceSource;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("callerPhoneNumber", callerPhoneNumber)
            .append("name", name)
            .append("voiceFile", voiceFile)
            .append("voiceSource", voiceSource)
            .toString();
    }
}
