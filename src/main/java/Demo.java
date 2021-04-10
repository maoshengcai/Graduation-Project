import gui.frame.MainFrame;
import service.Sources;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Demo {
    public static  void main(String[] args){
        Sources.initSources();
        MainFrame frame = MainFrame.instance;
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("退出！");
                Sources.saveSources();

            }
        });

    }

}
