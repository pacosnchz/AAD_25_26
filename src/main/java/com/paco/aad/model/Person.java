package com.paco.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    private String dni;
    private String name;
    private String surname;

//    public Person(){}      ---para esto es el @NoArgsConstructor

//    public Person(String dni, String name, String surname) {         --para esto es el @AllArgsConstructor
//        this.dni = dni;
//        this.name = name;
//        this.surname = surname;
//    }
//
//    public String getDni() {              ---los gettters y setters se hacen con @data
//        return dni;
//    }
//
//    public void setDni(String dni) {
//        this.dni = dni;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }

//    @Override                     ---para esto es el @ToString
//    public String toString() {
//        return "Person{" +
//                "dni='" + dni + '\'' +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                '}';
//    }
}


