package com.Stilar.acortadorUrl.models;

import com.Stilar.acortadorUrl.validators.anotations.ValidUrl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "shortUrls")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkModel {
    @Id
    private String id;
    @NotNull(message = "El url Original no puede ser nulo")
    @NotBlank(message = "El url Original no puede ir vacio")
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,4})+(.*)$", message = "El formato de la URL es inválido")
    private String originalUrl;


    private String shortUrl;


    private String idUser;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede ir vacio")
    private String linkName;

    private int visitcount;



}
