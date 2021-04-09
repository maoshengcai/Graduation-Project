package com.test;

import gui.panel.RegularTableModel;

import javax.swing.*;
import java.awt.*;

public class TestGui {
    public static void main(String[] args) {

        JFrame f = new JFrame("LoL");
        f.setSize(400, 300);
        f.setLocation(200, 200);
        f.setLayout(new BorderLayout());

        String[] columnNames = new String[] { "id", "name", "hp", "damage" };
        String[][] heros = new String[][] { { "1", "盖伦", "616", "100" },
                { "2", "提莫", "512", "102" }, { "3", "奎因", "832", "200" } };
//        JTable t = new JTable(heros, columnNames);
        JTable t = new JTable(new RegularTableModel());

        // 根据t创建 JScrollPane
        JScrollPane sp = new JScrollPane(t);

        //或则创建一个空的JScrollPane，再通过setViewportView把table放在JScrollPane中
        // JScrollPane sp = new JScrollPane(t);
        // sp.setViewportView(t);

        // 把sp而非JTable加入到JFrame上，
        f.add(t.getTableHeader(),BorderLayout.NORTH);
        f.add(sp, BorderLayout.CENTER);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
    }
}
