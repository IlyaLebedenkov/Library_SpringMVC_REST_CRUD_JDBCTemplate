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
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks(){
        return jdbcTemplate.query("SELECT * FROM Book",new BookMapper());
    }

    public void createBook(Book book){
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES(?,?,?)",book.getTitle(),
                            book.getAuthor(),book.getYear());
    }

    public Book getBookById(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?",new BookMapper(),new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id=Person.person_id " +
                "WHERE Book.book_id=?",new PersonMapper(),new Object[]{id}).stream().findAny();
    }
    public void edit(int id,Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE book_id=?",
                updatedBook.getTitle(),updatedBook.getAuthor(),updatedBook.getYear(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?",id);
    }

    public void assign(int id,Person person){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?",person.getId(),id);
    }

    public void release(int id){
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?",id);
    }
}
