package com.Stilar.acortadorUrl.controllers;

import com.Stilar.acortadorUrl.models.MessageModel;
import com.Stilar.acortadorUrl.models.UserModel;
import com.Stilar.acortadorUrl.services.UserServicesImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserServicesImpl userServicesImpl;

    @PostMapping("/create")
    public ResponseEntity<MessageModel> save(@Valid @RequestBody UserModel user, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageModel(false, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userServicesImpl.save(user));
    }

    @GetMapping("/all")
    public ResponseEntity<MessageModel> allUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(new MessageModel(true,"Se encontraron Usuarios", userServicesImpl.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageModel> searchUser(@PathVariable String id) {

        MessageModel msg = userServicesImpl.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(msg);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageModel> updateUser(@RequestBody UserModel user, @PathVariable String id) {

        user.setId(id);
        MessageModel mesage = userServicesImpl.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(mesage);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageModel> deleteUser(@PathVariable String id) {
        MessageModel msg = userServicesImpl.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }


}
