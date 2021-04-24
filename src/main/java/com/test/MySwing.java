package com.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySwing {
    public JFrame jFrame = new JFrame();
    public JPanel jPanel = new JPanel();
    public JLabel jLabel = new JLabel();
    public JButton start = new JButton("start");
    public JButton stop = new JButton("stop");
    public int count = 1;
    public boolean flag;
    public void addstart(){
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while(flag){
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    jLabel.setText(String.valueOf(count));
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            count++;

                        }
                    }
                }).start();
            }
        });
    }
    public void addstop(){
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = false;
            }
        });
    }
    public  void demo(){
        jFrame.setPreferredSize(new Dimension(300,100));
        jPanel.add(jLabel);
        jPanel.add(start);
        jPanel.add(stop);
        jFrame.add(jPanel);
        addstart();
        addstop();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
    public static void main(String[] args){
        new MySwing().demo();
    }
}
