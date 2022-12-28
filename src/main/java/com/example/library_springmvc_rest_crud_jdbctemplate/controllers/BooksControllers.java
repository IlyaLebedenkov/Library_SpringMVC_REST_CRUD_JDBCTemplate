package com.example.library_springmvc_rest_crud_jdbctemplate.controllers;

import com.example.library_springmvc_rest_crud_jdbctemplate.dao.BookDAO;
import com.example.library_springmvc_rest_crud_jdbctemplate.dao.PersonDAO;
import com.example.library_springmvc_rest_crud_jdbctemplate.models.Book;
import com.example.library_springmvc_rest_crud_jdbctemplate.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksControllers {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BooksControllers(BookDAO bookDAO,PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO=personDAO;
    }

    @GetMapping
    public String showAllBooks(Model model){
        model.addAttribute("books",bookDAO.getAllBooks());
        return "books/index";
    }

    @GetMapping("/new")
    public String createNewBookForm(Model model){
        model.addAttribute("book",new Book());
        return "books/new";
    }

    @PostMapping
    public String createNewBook(@ModelAttribute("book") @Valid Book book,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookDAO.createBook(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable("id") int id, Model model){
        model.addAttribute("book",bookDAO.getBookById(id));

        Optional<Person> bookOwner=bookDAO.getBookOwner(id);

        if(bookOwner.isPresent()){
            model.addAttribute("owner",bookOwner.get());
        }else{
            model.addAttribute("person",new Person());
            model.addAttribute("people",personDAO.getPeople());
        }

        return "books/book";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id")int id,Model model){
        model.addAttribute("book",bookDAO.getBookById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id")int id,@ModelAttribute("person") Person person){
        bookDAO.assign(id,person);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id")int id){
        bookDAO.release(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id")int id,@ModelAttribute("book") @Valid Book  book,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.edit(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
