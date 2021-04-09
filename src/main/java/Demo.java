import gui.frame.MainFrame;
import service.Sources;

public class Demo {
    public static  void main(String[] args){
        Sources.initSources();
        MainFrame frame = MainFrame.instance;
        frame.setVisible(true);
    }

}
