package com.wokebryant.anythingdemo.test;

import android.util.Log;

public class Animal {

    public int weight = 100;
    public String name;

    public Animal() {
        Log.i("animalInfo ", "initAnimal_start");
        getAnimalName();
        Log.i("animalInfo ", "initAnimal_end");
    }


    public void run() {
        Log.i("animalInfo ", "weight= " + weight + " " + "name= " + name);
    }

    public void getAnimalName() {
        name = "animal";
    }
}
