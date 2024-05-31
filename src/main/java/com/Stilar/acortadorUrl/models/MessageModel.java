package com.Stilar.acortadorUrl.models;

import lombok.Data;

@Data
public class MessageModel {
    private boolean ok;
    private String message;
    private Object value;

    public MessageModel(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public MessageModel(boolean ok, String message, Object obj) {
        this.ok = ok;
        this.message = message;
        this.value = obj;
    }
}
