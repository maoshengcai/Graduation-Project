package service;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReportService {
    public void setGradeInPdf(String filename,int grade){
        String path = filename;
        String str[] = filename.split("[.]");
        str[str.length-2] = str[str.length-2]+ "_tmp";
        String dest = String.join(".",str);
        String str_grade = String.valueOf(grade);
        PdfReader pdfReader =null;
        PdfWriter pdfWriter =null;
        try {
            pdfReader = new PdfReader(path);
            pdfWriter = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(pdfReader,pdfWriter);
            Document document = new Document(pdfDoc);

            //页数从1开始
            int pageNumber = 1;
            //单位应该是px
            PageSize pagesize = pdfDoc.getDefaultPageSize();
            float left = pagesize.getWidth()-100;
            float bottom = pagesize.getHeight()-100;
            float width = 100;
            document.add(new Paragraph(str_grade).setFontSize(40).setFontColor(Color.RED).setFixedPosition(pageNumber,left,bottom,width).setBackgroundColor(Color.WHITE));
            System.out.println("————————————————————————————————————我在这里");
            document.close();
            pdfDoc.close();
            pdfReader.close();
            pdfWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {

        }
        System.gc();
        File file = new File(dest);
        File fileSrc = new File(path);
        System.out.println("————————————————————————————————————我在这里");
        System.out.println(fileSrc.delete());
        System.out.println(file.renameTo(fileSrc));
    }

    public static void main(String[] args){
        String path = "G:/test/1.pdf";
        ReportService reportService = new ReportService();
        reportService.setGradeInPdf(path,50);


    }

}
