package com.example.library_springmvc_rest_crud_jdbctemplate.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {
    private int id;

    @NotNull(message = "Full name can't be empty")
    @Size(min = 2,max = 100,message = "Title should be between 2 and 100 characters ")
    private String title;
    @NotNull(message = "Author name can't be empty")
    @Size(min = 2,max = 50,message = "Author name should be between 2 and 50 characters ")
    private String author;
    @Min(value = 1700,message = "Year of book can't be less than 1700")
    private int year;

    public Book() {
    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "id=" + id + ", title=" + title + ", author=" + author + ", year=" + year;
    }
}
