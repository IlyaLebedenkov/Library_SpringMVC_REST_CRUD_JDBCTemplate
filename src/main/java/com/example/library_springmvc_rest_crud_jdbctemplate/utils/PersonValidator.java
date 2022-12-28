package com.example.library_springmvc_rest_crud_jdbctemplate.utils;


import com.example.library_springmvc_rest_crud_jdbctemplate.dao.PersonDAO;
import com.example.library_springmvc_rest_crud_jdbctemplate.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person=(Person) target;

        if(personDAO.getPersonByName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName","","This Full Name is already taken ");
        }
    }
}
