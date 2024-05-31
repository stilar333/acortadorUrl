package com.Stilar.acortadorUrl.controllers;

import com.Stilar.acortadorUrl.models.MessageModel;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenDataGetController {
    @GetMapping()
    public ResponseEntity<MessageModel> getDataToken(@RequestAttribute("claims") Claims claims){
        return ResponseEntity.ok().body(new MessageModel(true, "Se extrajo Correctamente datos de tu token", claims));
    }
}
