package gui.panel;

import dao.StuInfoDao;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RegularTableModel extends AbstractTableModel {
    public String [] columnNames = new String[]{"专业","学号","姓名","平时成绩"};
    public ArrayList<StuInfoDao> data = new ArrayList<>();
    public ArrayList<Integer> grades = new ArrayList<>();
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(columnIndex == 3){
            String str = String.valueOf(aValue);
            if(!str.equals("null")){
                grades.set(rowIndex, Integer.valueOf(str));
            }

        }else{
            super.setValueAt(aValue, rowIndex, columnIndex);
        }

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
        if(rowIndex >= data.size()){
            return null;
        }
        StuInfoDao info = data.get(rowIndex);
        switch (columnIndex){
            case 0: return info.getAcademy();
            case 1: return info.getNumber();
            case 2: return info.getName();
            case 3: return grades.get(rowIndex);
        }
        return null;
    }
}
