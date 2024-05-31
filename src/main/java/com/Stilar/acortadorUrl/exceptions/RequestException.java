package com.Stilar.acortadorUrl.exceptions;

import lombok.Data;

@Data
public class RequestException extends RuntimeException {
    private boolean ok;

    public RequestException( boolean ok,String message) {
        super(message);
        this.ok=ok;
    }
}
