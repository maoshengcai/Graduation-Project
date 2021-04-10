package service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Sources {
    public static HashSet<String> correctedReport = new HashSet<>() ;             //已批改过的报告文件名集合
    public static ArrayList<String> nowCorrected = new ArrayList<>();                //软件运行过程中批改过的报告文件名集合

    /*
    功能：通过存储文件（fileName）设置已批改过报告文件集合,存储文件为txt文件
     */
    public static void getCorrectedReport(String fileName){
        if(!fileName.endsWith(".txt")){
            System.out.println("存储文件不是txt文件");
            return;
        }
        try(FileReader fReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fReader)){
            String aLine = reader.readLine();
            while(aLine != null){
                correctedReport.add(aLine);
                aLine = reader.readLine();
            }
        }  catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public static void setCorrectedReport(String fileName){
        try(FileWriter fWriter = new FileWriter(fileName,true);
            BufferedWriter writer = new BufferedWriter(fWriter)){
            Iterator<String> iterator = nowCorrected.iterator();
            String str ;
            for(;iterator.hasNext();){
                str = iterator.next();
                writer.write(str);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public static void initSources(){
        String fileName = "src/correctedReport.txt";
        getCorrectedReport(fileName);
    }
    public static void saveSources(){
        String fileName = "src/correctedReport.txt";
        setCorrectedReport(fileName);
    }
}
