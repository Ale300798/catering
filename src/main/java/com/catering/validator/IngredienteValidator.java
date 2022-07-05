package com.catering.validator;

import com.catering.model.Ingrediente;
import com.catering.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IngredienteValidator implements Validator {
    @Autowired
    private IngredienteService ingredienteService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Ingrediente.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        Ingrediente ingrediente = (Ingrediente) target;
        if(this.ingredienteService.esisteGia(ingrediente.getId())) {
            errors.reject("ingrediente.duplicato");
        }

    }
}
