package com.test;


import java.io.*;
import java.util.Arrays;

public class Test2 {
    public static void main(String[] args) throws IOException {
//        FileWriter fWriter = new FileWriter("G:/test/hello.txt",true);
//        BufferedWriter writer = new BufferedWriter(fWriter);
//        writer.write("2017051712_01_02.pdf");
//        writer.newLine();
//        writer.flush();
//        writer.close();
//        System.out.println("--------------------");
//        FileReader file = new FileReader("G:/test/hello.txt");
//        BufferedReader reader = new BufferedReader(file);
//        String aLine = reader.readLine();
//        int num = 1;
//        while(aLine != null){
//            System.out.println("第"+num+"条"+aLine);
//            aLine = reader.readLine();
//            num++;
//        }
//        reader.close();
//
//        System.out.println("--------------------");

        File file = new File("G:\\test/test");
        String str = "G:\\test/test";
        System.out.println(str.equals("G:/test/test"));
        System.out.println(file.getParent());
        String[] strings = file.list();
        Arrays.stream(strings).forEach(System.out::println);





    }
}
