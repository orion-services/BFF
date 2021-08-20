package dev.orion.api.v1.sockets.dto;

import dev.orion.broker.dto.ActivityUpdateQueueDto;
import dev.orion.broker.model.ErrorType;
import dev.orion.broker.model.UserError;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ActivityBroadcastDto {
    Activity activity;
    Document document;

    private static class Activity{
        public UUID uuid;
        public List<String> participants;
        public String participantRound;
        public Boolean isActive;
        public Set<UserError> performErrors;
        public UserError userError;
        public ErrorType errorType;
    }

    private static class Document{
        public String externalUserId;

        public String documentContent;
    }

    public ActivityBroadcastDto(Activity activity,  Document document) {
        this.activity = activity;
        this.document = document;
    }

    public Activity getActivity() {
        return activity;
    }

    public Document getDocument() {
        return document;
    }
}
