package com.Stilar.acortadorUrl.exceptions;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
    private boolean ok;

    public ServiceException( boolean ok,String message) {
        super(message);
        this.ok=ok;
    }
}
