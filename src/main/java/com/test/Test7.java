package com.test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Test7 extends JPanel implements ActionListener{
    private static final long serialVersionUID = 1L;
    private JLabel label2;

    public Test7() {
        super(new GridLayout(2,1));
        JButton b1 = new JButton("click me");
        b1.addActionListener(this);
        label2 = new JLabel("Label");
        add(label2);
        add(b1);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("LabelDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Test7());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i = 0 ; i < 10 ; i ++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {}
                    final int x =i;
                    SwingUtilities.invokeLater(new Runnable(){
                        @Override
                        public void run() {
                            label2.setText(x + "");
                        }
                    });
                }
            }
        }).start();
    }
}


