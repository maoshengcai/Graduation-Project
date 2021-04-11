package util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExcelUtil {
    //导出JTable到excel中
    public static void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
        for(int i = 0;i <model.getColumnCount();i++){
            bWriter.write(model.getColumnName(i));
            bWriter.write("\t");
        }
        bWriter.newLine();
        for(int i = 0;i<model.getRowCount();i++){
            for(int j = 0;j < model.getColumnCount();j++){
                bWriter.write(String.valueOf(model.getValueAt(i,j)));
                bWriter.write("\t");
            }
            bWriter.newLine();
        }
        bWriter.close();
    }
}
