package gui.panel;

import javax.swing.*;

public class FileDownAndUpPanel extends JPanel {
    public static FileDownAndUpPanel instance = null;
    public JLabel warningT;
    private FileDownAndUpPanel(){
        warningT = new JLabel("");
        this.add(warningT);
    }
    static {
        instance = new FileDownAndUpPanel();
    }
}
