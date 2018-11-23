package com.serverless.demo.model.lex;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

public class LexResponse {
    private DialogAction dialogAction;

    public DialogAction getDialogAction() {
        return dialogAction;
    }

    public void setDialogAction(DialogAction dialogAction) {
        this.dialogAction = dialogAction;
    }

    @Override
    public String toString() {
        return "LexResponse{" +
                "dialogAction=" + dialogAction +
                '}';
    }

    public LexResponse() {
    }

    public LexResponse(DialogAction dialogAction) {
        this.dialogAction = dialogAction;
    }

    public static class DialogAction {
        private String type;
        private String fulfillmentState;
        private Message message;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFulfillmentState() {
            return fulfillmentState;
        }

        public void setFulfillmentState(String fulfillmentState) {
            this.fulfillmentState = fulfillmentState;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public DialogAction(Message message) {
            this.message = message;
            type = "Close";
            fulfillmentState = "Fulfilled";
        }

        public DialogAction() {
        }

        @Override
        public String toString() {
            return "DialogAction{" +
                    "type='" + type + '\'' +
                    ", fulfillmentState='" + fulfillmentState + '\'' +
                    ", message=" + message +
                    '}';
        }

    }

    public static class Message {
        private String contentType;
        private String content;

        public Message(String content) {
            this.content = content;
            contentType = "PlainText";
        }

        public Message() {
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "contentType='" + contentType + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}

