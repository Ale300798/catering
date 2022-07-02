package com.catering.validator;

import com.catering.model.Buffet;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BuffetValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Buffet.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
