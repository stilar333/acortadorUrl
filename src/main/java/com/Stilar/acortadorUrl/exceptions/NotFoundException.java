package com.Stilar.acortadorUrl.exceptions;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private boolean ok;

    public NotFoundException( boolean ok,String message) {
        super(message);
        this.ok=ok;
    }
}