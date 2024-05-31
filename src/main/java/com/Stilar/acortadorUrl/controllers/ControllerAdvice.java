package com.Stilar.acortadorUrl.controllers;

import com.Stilar.acortadorUrl.exceptions.DuplicatedKeyException;
import com.Stilar.acortadorUrl.exceptions.NotFoundException;
import com.Stilar.acortadorUrl.exceptions.RequestException;
import com.Stilar.acortadorUrl.exceptions.ServiceException;
import com.Stilar.acortadorUrl.models.MessageModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerAdvice {
    // Manejador de errores para cuando se proporcionan valores incorrectos en el body de la request
    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<MessageModel> RequestExceptionHandler(RequestException ex){
        MessageModel msg = new MessageModel(ex.isOk(), ex.getMessage());
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    // Manejador de errores para cuando no se encuentra el usuario en la base de datos
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<MessageModel> UsernameNotFoundExceptionHandler(RequestException ex){
        MessageModel msg = new MessageModel(false, ex.getMessage());
        return new ResponseEntity<>(msg, HttpStatus.UNAUTHORIZED);
    }


    // Manejador de errores para cuando Ocurre un error en el servidor
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<MessageModel> ServiceExceptionHandler(ServiceException ex){
        MessageModel msg = new MessageModel(ex.isOk(), ex.getMessage());
        return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejador de errores para cuando no se encuentra el documento en la base de datos
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<MessageModel> NotFoundExceptionHandler(NotFoundException ex){
        MessageModel msg = new MessageModel(ex.isOk(), ex.getMessage());
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    // Manejador de errores para cuando un valor está repetido en la base de datos (emails o usuario)
    @ExceptionHandler(value = DuplicatedKeyException.class)
    public ResponseEntity<MessageModel> DuplicatedKeyExceptionHandler(DuplicatedKeyException ex){
        MessageModel msg = new MessageModel(ex.isOk(), ex.getMessage());
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }
}
