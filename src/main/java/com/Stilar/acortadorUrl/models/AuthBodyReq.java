package com.Stilar.acortadorUrl.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class AuthBodyReq implements Serializable {
    private String username;

    private String password;
}
