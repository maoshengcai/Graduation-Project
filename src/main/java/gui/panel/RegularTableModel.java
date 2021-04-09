package gui.panel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RegularTableModel extends AbstractTableModel {
    public String [] columnNames = new String[]{"专业","学号","姓名","平时成绩"};
    public String[][] data = new String[][]{{"计科","2017051712","小明","50.0"},
            {"计科","2017051711","小红","90.0"}} ;
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column].toString();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean flag = false;
        if(columnIndex == columnNames.length-1){
            flag = true;
        }
        return flag;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
