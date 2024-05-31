package com.Stilar.acortadorUrl.controllers;

import com.Stilar.acortadorUrl.models.LinkModel;
import com.Stilar.acortadorUrl.models.MessageModel;
import com.Stilar.acortadorUrl.services.LinkServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/")
public class ShortController {
    @Autowired
    LinkServicesImpl linkServicesImpl;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<MessageModel> redirect(@PathVariable String shortUrl) {

        MessageModel msg = linkServicesImpl.findByShortUrl(shortUrl);
        LinkModel linkModel = (LinkModel) msg.getValue();
        linkServicesImpl.updateCounter(linkModel);
        URI uri = URI.create(linkModel.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();

    }

}
