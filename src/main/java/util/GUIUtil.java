package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GUIUtil {
    private static String imageFolder = "D:\\WTYdoc\\java\\j2se\\project\\process\\hutubill\\img";

    public GUIUtil() {
    }

    public static void setImageIcon(JButton b, String fileName, String tip) {
        ImageIcon i = new ImageIcon((new File(imageFolder, fileName)).getAbsolutePath());
        b.setIcon(i);
        b.setPreferredSize(new Dimension(61, 81));
        b.setToolTipText(tip);
        b.setVerticalTextPosition(3);
        b.setHorizontalTextPosition(0);
        b.setText(tip);
    }

    public static void setColor(Color color, JComponent... cs) {
        JComponent[] var2 = cs;
        int var3 = cs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            JComponent c = var2[var4];
            c.setForeground(color);
        }

    }

    //展示的panel占整个面板的大小比例
    public static void showPanel(JPanel p, double strechRate) {
        useLNF();
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setLocationRelativeTo((Component)null);
        CenterPanel cp = new CenterPanel(strechRate);
        f.setContentPane(cp);
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
        cp.show(p);
    }

    public static void showPanel(JPanel p) {
        showPanel(p, 0.85D);
    }

    public static boolean checkNumber(JTextField tf, String input) {
        if (!checkEmpty(tf, input)) {
            return false;
        } else {
            String text = tf.getText().trim();

            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException var4) {
                JOptionPane.showMessageDialog((Component)null, input + " 需要是整数");
                tf.grabFocus();
                return false;
            }
        }
    }

    public static boolean checkZero(JTextField tf, String input) {
        if (!checkNumber(tf, input)) {
            return false;
        } else {
            String text = tf.getText().trim();
            if (0 == Integer.parseInt(text)) {
                JOptionPane.showMessageDialog((Component)null, input + " 不能为零");
                tf.grabFocus();
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean checkEmpty(JTextField tf, String input) {
        String text = tf.getText().trim();
        if (0 == text.length()) {
            JOptionPane.showMessageDialog((Component)null, input + " 不能为空");
            tf.grabFocus();
            return false;
        } else {
            return true;
        }
    }

    public static int getInt(JTextField tf) {
        return Integer.parseInt(tf.getText());
    }

    public static void useLNF() {
        try {
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}

