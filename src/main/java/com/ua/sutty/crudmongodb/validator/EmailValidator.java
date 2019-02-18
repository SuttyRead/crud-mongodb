package com.ua.sutty.crudmongodb.validator;

import com.ua.sutty.crudmongodb.form.UserForm;
import com.ua.sutty.crudmongodb.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailValidator implements Validator {

    private final UserRepository userRepository;

    public EmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserForm userForm = (UserForm) o;
        if (userRepository.existsByEmail(userForm.getEmail())) {
            errors.rejectValue("email", "This email already registered");
        }
    }

}
