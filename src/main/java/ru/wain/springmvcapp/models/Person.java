package ru.wain.springmvcapp.models;


import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//Бины можели создавать не нужно
public class Person {

    private int id;
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 32, message = "Name should be between 2 - 32 characters")
    private String name;
    @Min(value = 0, message = "Age must be greater than zero!")
    private int age;
    @NotEmpty(message = "email must be filled!")
    @Email(message = "email should be valid!")
    private String email;

    public Person(){

    }

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
