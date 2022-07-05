package com.catering.validator;

import com.catering.model.Piatto;
import com.catering.repository.PiattoRepository;
import com.catering.service.PiattoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PiattoValidator implements Validator {

    @Autowired
    private PiattoService piattoService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Piatto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Piatto piatto = (Piatto) target;
        if(this.piattoService.esisteGia(piatto.getId()))
            errors.reject("piatto.duplicato");

    }
}
