package com.catering.validator;

import com.catering.model.Buffet;
import com.catering.repository.BuffetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BuffetValidator implements Validator {

    @Autowired
    private BuffetRepository buffetRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Buffet.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Buffet buffet =(Buffet) target;
        if(this.buffetRepository.existsById(buffet.getId())) {
            errors.reject("buffet.duplicato");
        }

    }
}
