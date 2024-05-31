package com.Stilar.acortadorUrl.controllers;

import com.Stilar.acortadorUrl.exceptions.RequestException;
import com.Stilar.acortadorUrl.models.LinkModel;
import com.Stilar.acortadorUrl.models.MessageModel;
import com.Stilar.acortadorUrl.services.LinkServicesImpl;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/links")
public class LinkController {
    @Autowired
    private LinkServicesImpl linkServicesImpl;

    @PostMapping("/create")
    public ResponseEntity<MessageModel> save(@Valid @RequestBody LinkModel linkModel, @RequestAttribute("claims")Claims claims, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageModel(false, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(linkServicesImpl.save(linkModel, (String) claims.get("userId")));
    }

    //@GetMapping("/all")
    public List<LinkModel> allLinks() {
        return linkServicesImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageModel> searchLink(@PathVariable String id, @RequestAttribute("claims") Claims claims) {

        MessageModel msg = linkServicesImpl.findById(id);
        if(!msg.isOk()){
            msg = linkServicesImpl.findByIdUser(id);
        }
        if(!msg.isOk()){
            msg = linkServicesImpl.findByIdUser((String) claims.get("userId"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @PutMapping("/update")
    public ResponseEntity<MessageModel> updateLink(@Valid @RequestBody LinkModel linkModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageModel(false, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(linkServicesImpl.save(linkModel, ""));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageModel> deleteLink(@PathVariable String id) {
        MessageModel msg = linkServicesImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

}
