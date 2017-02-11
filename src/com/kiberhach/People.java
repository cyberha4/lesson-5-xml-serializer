package com.kiberhach;

/**
 * Created by Хидир on 10.02.2017.
 */
public class People {
    public String name = "test2";
    public int age;
    private String salaty;

    public People(String name, int age, String sal) {
        this.name = name;
        this.age = age;
        this.salaty = sal;
    }
    public People(){

    }

    public void Test(){
        System.out.println(2);
    }



    @Override
    public String toString() {
        return "name :"+this.name+" age :"+this.age;
    }
}
