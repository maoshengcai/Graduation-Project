package gui.panel;

import javax.swing.JPanel;

public abstract class WorkingPanel extends JPanel {
    public WorkingPanel() {
    }

    public abstract void updateData();

    public abstract void addListener();
}
