package com.Stilar.acortadorUrl.validators;


import com.Stilar.acortadorUrl.validators.anotations.ValidUrl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    private static final String URL_PATTERN =
            "^((https?|ftp)://)?(([\\w.-]*)\\.([a-z]{2,}))(/([\\w/_\\.-]*(\\?\\S+)?)?)?$";

    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
        // Inicializa el patrón de la URL
        pattern = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        // Retorna verdadero si la URL está vacía porque no es responsabilidad de esta validación
        // comprobar si el campo está vacío o nulo, eso lo hacen @NotNull y @NotBlank.
        if (url == null || url.isBlank()) {
            return true;
        }

        matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
