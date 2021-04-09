package util;

import gui.panel.WorkingPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {
    private double rate;
    private JComponent c;
    private boolean strech;

    public CenterPanel(double rate, boolean strech) {
        this.setLayout((LayoutManager)null);
        this.rate = rate;
        this.strech = strech;
    }

    public CenterPanel(double rate) {
        this(rate, true);
    }

    public void repaint() {
        if (null != this.c) {
            Dimension containerSize = this.getSize();
            Dimension componentSize = this.c.getPreferredSize();
            if (this.strech) {
                this.c.setSize((int)((double)containerSize.width * this.rate), (int)((double)containerSize.height * this.rate));
            } else {
                this.c.setSize(componentSize);
            }

            this.c.setLocation(containerSize.width / 2 - this.c.getSize().width / 2, containerSize.height / 2 - this.c.getSize().height / 2);
        }

        super.repaint();
    }

    public void show(JComponent p) {
        this.c = p;
        Component[] cs = this.getComponents();
        Component[] var3 = cs;
        int var4 = cs.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Component c = var3[var5];
            this.remove(c);
        }

        this.add(p);
        if (p instanceof WorkingPanel) {
            ((WorkingPanel)p).updateData();
        }

        this.updateUI();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(200, 200);
        f.setLocationRelativeTo((Component)null);
        CenterPanel cp = new CenterPanel(0.85D, true);
        f.setContentPane(cp);
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
        JButton b = new JButton("abc");
        cp.show(b);
    }
}

