package com.catering.validator;

import com.catering.model.Chef;
import com.catering.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ChefValidator implements Validator {

    @Autowired
    private ChefService chefService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Chef.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Chef chef = (Chef) target;
        if(chefService.alreadyExists(chef.getNome(), chef.getCognome(), chef.getNazionalita())) {
            errors.reject("chef.duplicato");
        }

    }
}
