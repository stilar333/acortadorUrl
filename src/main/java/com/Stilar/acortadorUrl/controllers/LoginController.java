package com.Stilar.acortadorUrl.controllers;
import com.Stilar.acortadorUrl.models.AuthBodyReq;
import com.Stilar.acortadorUrl.models.MessageModel;
import com.Stilar.acortadorUrl.models.UserModel;
import com.Stilar.acortadorUrl.services.JwtUtilService;
import com.Stilar.acortadorUrl.services.UserServicesImpl;
import jakarta.validation.Valid;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    UserServicesImpl userServices;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping("/login")
    public ResponseEntity<MessageModel> authenticate(@RequestBody AuthBodyReq authenticationReq) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReq.getUsername(),
                            authenticationReq.getPassword()));

            final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(authenticationReq.getUsername());
            final UserModel user = (UserModel) userServices.findByUsername(authenticationReq.getUsername()).getValue();
            final String token = jwtUtilService.generateToken(userDetails, user);

            return ResponseEntity.ok(new MessageModel(true, "Se Inicio Session Correctamente", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new MessageModel(false, "Credenciales incorrectas. Verifica tu nombre de usuario y contraseña.", null));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(new MessageModel(false, "Ocurrio un error en el serividor", null));

        }
    }
    @PostMapping("/register")
    public ResponseEntity<MessageModel> save(@Valid @RequestBody UserModel user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageModel(false, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(userServices.save(user));
    }

}