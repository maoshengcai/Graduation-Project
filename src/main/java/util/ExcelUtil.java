package util;

import jxl.*;
import jxl.read.biff.BiffException;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    //excel表格学生数据导入JTable中
    public static JTable excelToTable(File file)  {

        ArrayList<String[]> datas = new ArrayList<>();
        String[] dataLine = new String[3];
        try(BufferedReader bReader = new BufferedReader(new FileReader(file));

        ){
            String str = bReader.readLine();
            while(str != null){
                dataLine = Arrays.copyOfRange(str.trim().split("\\s+"),0,3);
                datas.add(dataLine);
                str = bReader.readLine();
                System.out.println("dataLine = "+dataLine.length+"  "+dataLine[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        datas.stream().forEach(g->System.out.println(g[0] + " "+g[1]+" "+g[2]));
        String[] columnName = datas.get(0);
        datas.remove(0);
        return new JTable(datas.toArray(new String[0][0]),columnName);


    }
    public static void main(String[] args){
        File file = new File("G:\\test\\grade1.xls");
        JFrame f = new JFrame("LoL");
        f.setSize(400, 300);
        f.setLocation(200, 200);
        f.setLayout(new BorderLayout());

        JTable t = excelToTable(file);

        // 根据t创建 JScrollPane
        JScrollPane sp = new JScrollPane(t);

        //或则创建一个空的JScrollPane，再通过setViewportView把table放在JScrollPane中
        // JScrollPane sp = new JScrollPane(t);
        // sp.setViewportView(t);

        // 把sp而非JTable加入到JFrame上，
        f.add(sp, BorderLayout.CENTER);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
//        try {
//            JTable jTable = excelToTable(file);
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        } catch (BiffException e) {
//            e.printStackTrace();
//        }
    }
}
