package com.example.library_springmvc_rest_crud_jdbctemplate.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotNull(message = "Full name can't be empty")
    @Size(min = 2,max = 50,message = "Full name should be between 2 and 50 characters ")
    private String fullName;
    @Min(value = 1900,message = "Year of birth can't be less than 1900")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int id, String fullName, int yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public String toString() {
        return "id=" + id + ", Full name= " + fullName + ", year of birth=" + yearOfBirth;
    }
}
