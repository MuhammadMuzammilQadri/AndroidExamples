package com.example.muhammadmuzammil.memoryhandlingexample;

/**
 * Created by Muhammad Muzammil on 16-Jun-16.
 */
public class MySingleton {

    private static MySingleton mySingleton;
    private int number;

    private MySingleton(){

    }

    public static MySingleton getInstance(){
        if (mySingleton == null)
            mySingleton = new MySingleton();
        return mySingleton;
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void incrementNumber() {
        this.number++;
    }
}
