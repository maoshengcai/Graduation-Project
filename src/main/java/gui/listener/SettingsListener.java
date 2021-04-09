package gui.listener;

import gui.panel.FtpSettingPanel;
import gui.panel.SettingsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SettingsPanel sp=SettingsPanel.instance;
        JButton jb = (JButton) e.getSource();
        if(jb == sp.bFtpSetting){
            sp.pRight.show(FtpSettingPanel.instance);

        }
    }
}
