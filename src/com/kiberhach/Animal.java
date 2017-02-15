package com.kiberhach;

/**
 * Created by admin on 15.02.2017.
 */
public class Animal {
    public String name = "Animal";
    public int age =10;
    private String salaty = "Animal-salaty";


    public void Test(){
        System.out.println(2);
    }



    @Override
    public String toString() {
        return "name :"+this.name+" age :"+this.age;
    }
}
