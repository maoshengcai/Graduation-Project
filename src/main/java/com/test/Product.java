package com.test;

public class Product {
    private int value;
    private boolean isEmpty = true;
    public synchronized void put(int value)  {
        while(!isEmpty){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.value = value;
        isEmpty = false;
        notifyAll();
    }
    public synchronized int get(){
        while(isEmpty){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isEmpty = true;
        notifyAll();
        return value;
    }
}
