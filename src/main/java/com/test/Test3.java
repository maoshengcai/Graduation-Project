package com.test;

import javax.swing.*;

public class Test3 {
    public static void main(String[] args) {

        JFrame f = new JFrame("LoL");
        f.setSize(400, 300);
        f.setLocation(580, 200);
        f.setLayout(null);
        JCheckBox bCheckBox = new JCheckBox("物理英雄");
        //设置 为 默认被选中
        bCheckBox.setSelected(true);
        bCheckBox.setBounds(50, 50, 130, 30);
        JCheckBox bCheckBox2 = new JCheckBox("魔法 英雄");
        bCheckBox2.setBounds(50, 100, 130, 30);
        //判断 是否 被 选中
        System.out.println(bCheckBox2.isSelected());

        ButtonGroup bg = new ButtonGroup();
        bg.add(bCheckBox);
        bg.add(bCheckBox2);

        f.add(bCheckBox);
        f.add(bCheckBox2);

        String[] heros = new String[]{"卡特琳娜", "库奇"};
        JComboBox cb = new JComboBox(heros);
        cb.setBounds(200, 50, 80, 30);
        System.out.println(cb.getSelectedItem());

        f.add(cb);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);

        int option = JOptionPane.showConfirmDialog(f, "是否 使用外挂 ？");
        if (JOptionPane.OK_OPTION == option) {
            String answer = JOptionPane.showInputDialog(f, "请输入yes，表明使用外挂后果自负");
            if ("yes".equals(answer))
                JOptionPane.showMessageDialog(f, "你使用外挂被抓住！ 罚拣肥皂3次！");
        }
    }
}
