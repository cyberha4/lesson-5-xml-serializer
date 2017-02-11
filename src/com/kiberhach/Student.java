package com.kiberhach;

/**
 * Created by Хидир on 10.02.2017.
 */
public class Student {
    public String name1 = "erser";
    public int age;

    public Student(String name, int age) {
        this.name1 = name;
        this.age = age;
    }

    public Student(){

    }
    @Override
    public String toString() {
        return "name :"+this.name1+" age :"+this.age;
    }
}
