package com.Stilar.acortadorUrl.exceptions;


import lombok.Data;

@Data
public class DuplicatedKeyException extends RuntimeException {
    private boolean ok;

    public DuplicatedKeyException( boolean ok,String message) {
        super(message);
        this.ok=ok;
    }
}