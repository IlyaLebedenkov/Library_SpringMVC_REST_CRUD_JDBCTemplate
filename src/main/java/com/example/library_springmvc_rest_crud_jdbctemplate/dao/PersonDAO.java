package com.example.library_springmvc_rest_crud_jdbctemplate.dao;

import com.example.library_springmvc_rest_crud_jdbctemplate.models.Book;
import com.example.library_springmvc_rest_crud_jdbctemplate.models.Person;
import com.example.library_springmvc_rest_crud_jdbctemplate.utils.BookMapper;
import com.example.library_springmvc_rest_crud_jdbctemplate.utils.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM Person",new PersonMapper());
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES (?,?)",person.getFullName(),
                person.getYearOfBirth());
    }

    public List<Book> getAllBooksOfPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?",new BookMapper(),new Object[]{id});
    }

    public Person getPersonById(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?",new PersonMapper(),
                new Object[]{id}).stream().findAny().orElse(null);
    }

    public Optional<Person> getPersonByName(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?",new PersonMapper(),
                new Object[]{name}).stream().findAny();
    }

    public void editPerson(int id,Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET full_name=?,year_of_birth=? WHERE person_id=?",
                updatedPerson.getFullName(),updatedPerson.getYearOfBirth(),id);
    }
}
