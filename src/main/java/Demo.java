import gui.frame.MainFrame;
import service.Sources;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Demo {
    public static  void main(String[] args){
        Sources.initSources();
        MainFrame frame = MainFrame.instance;

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("退出！");
                Sources.saveSources();

            }
        });
        frame.setVisible(true);

    }

}
