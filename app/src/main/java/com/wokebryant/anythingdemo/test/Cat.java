package com.wokebryant.anythingdemo.test;

import android.util.Log;

public class Cat extends Animal{

    public Cat() {
        Log.i("animalInfo ", "initCat");
    }


    @Override
    public void getAnimalName() {
        name = "cat";
        Log.i("animalInfo ", "getAnimalName");
    }

    public void init() {
        weight = 20;
        run();
    }
}
