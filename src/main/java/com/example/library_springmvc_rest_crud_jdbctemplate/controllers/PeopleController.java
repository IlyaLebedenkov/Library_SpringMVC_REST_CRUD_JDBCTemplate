package com.example.library_springmvc_rest_crud_jdbctemplate.controllers;

import com.example.library_springmvc_rest_crud_jdbctemplate.dao.PersonDAO;
import com.example.library_springmvc_rest_crud_jdbctemplate.models.Person;
import com.example.library_springmvc_rest_crud_jdbctemplate.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showAllPeople(Model model){
        model.addAttribute("people",personDAO.getPeople());
        return "people/index";
    }

    @GetMapping("/new")
    public String createNewPersonForm(Model model){
        model.addAttribute("person",new Person());
        return "people/new";
    }

    @PostMapping
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                                  BindingResult bindingResult){
        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String getPersonPage(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personDAO.getPersonById(id));
        model.addAttribute("books",personDAO.getAllBooksOfPerson(id));
        return "people/person";
    }

    @GetMapping("/{id}/edit")
    public String editPersonForm(@PathVariable("id")int id, Model model){
        model.addAttribute("person",personDAO.getPersonById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id,@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        personDAO.editPerson(id,person);
        return "redirect:/people";

    }


}
