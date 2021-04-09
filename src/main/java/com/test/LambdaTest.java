package com.test;

import java.util.Arrays;

public class LambdaTest {
    public static  void main(String[] args){
        String[] names = {"peter","ana","mike","join"};
        Arrays.sort(names,(a,b)->b.compareTo(a));
        for(String s:names){
            System.out.println(s.toString());
        }
    }
}
