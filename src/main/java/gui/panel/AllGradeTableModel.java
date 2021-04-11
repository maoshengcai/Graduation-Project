package gui.panel;

import dao.StuInfoDao;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class AllGradeTableModel extends AbstractTableModel {
    public String [] columnNames = new String[]{"学院","学号","姓名","RegularGrade","experiment01","experiment02","experiment03","experiment04","" +
            "experiment05","AllGrade"};
    public ArrayList<StuInfoDao> data = new ArrayList<>();
    public ArrayList<Integer[]> grades = new ArrayList<>();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        if(columnIndex == columnNames.length-1){
            System.out.println("setValueTable:  "+String.valueOf(aValue));
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean flag = false;
        if(columnIndex == (columnNames.length-1)){
            flag = true;
        }
        return flag;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String result = "";
        switch (columnIndex){
            case 0: return data.get(rowIndex).getAcademy();
            case 1: return data.get(rowIndex).getNumber();
            case 2: return data.get(rowIndex).getName();
            default:
        }
        if(columnIndex >2 && columnIndex<columnNames.length){
            int tmp = grades.get(rowIndex)[columnIndex-3];
            result = String.valueOf(tmp);
            if(tmp == -1){
                result = "";
            }
        }

        return result;
    }

}
