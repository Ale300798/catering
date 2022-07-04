package com.catering.validator;

import com.catering.model.Ingrediente;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IngredienteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Ingrediente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
