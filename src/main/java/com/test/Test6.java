package com.test;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test6 {
    public static void main(String[] args) {
        //以eclipse的风格显示一个包下所有的Java文件
        JFrame f = new JFrame();
        f.setSize(800, 600);
        f.setLocation(200, 200);
        f.setLayout(null);

        JTabbedPane tp = new JTabbedPane();

        File file = new File("src/com/test");
        System.out.println(file.exists());
        File[] files = file.listFiles();
        int i = 0;
        for (File ff : files) {
            JTextArea ta = new JTextArea();
            try {
                BufferedReader br = new BufferedReader(new FileReader(ff));
                while (true) {
                    String line = br.readLine();
                    //  System.out.println(line);
                    if (line == null)
                        break;
                    ta.append(line + "\r\n");
                }
            } catch (IOException e) {
                //TODO: handle exception
                e.printStackTrace();
            }
            tp.add(new JScrollPane(ta));
            tp.setTitleAt(i, ff.getName());
            i++;
        }
        f.setContentPane(tp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
