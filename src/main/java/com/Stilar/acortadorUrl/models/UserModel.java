package com.Stilar.acortadorUrl.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Usuarios")
@Data
public class UserModel {
    @Id
    private String id;
    @NotNull(message = "El Email no puede ser nulo")
    @NotBlank(message = "El Email no puede estar vacio")
    @Email
    private String email;

    @NotNull(message = "La Contraseña no puede ser nula")
    @NotBlank(message = "La Contraseña no puede estar vacia")
    private String password;

    @NotNull(message = "El Usuario no puede ser nulo")
    @NotBlank(message = "El Usuario no puede estar vacio")
    private String username;

    public UserModel(String id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
