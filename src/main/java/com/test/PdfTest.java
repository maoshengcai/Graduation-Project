package com.test;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;


import java.io.IOException;



public class PdfTest {
    public static void main(String[] args){

        String path = "G:/test/1.pdf";
        String dest = "G:/test/2.pdf";
        try (
            PdfReader pdfReader = new PdfReader(path);
            PdfWriter pdfWriter = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(pdfReader,pdfWriter);){
            Document document = new Document(pdfDoc);
            //页数从1开始
            int pageNumber = 1;
            //单位应该是px
            PageSize pagesize = pdfDoc.getDefaultPageSize();
            float left = pagesize.getWidth()-100;
            float bottom = pagesize.getHeight()-100;
            float width = 100;
            String str = "98";

            document.add(new Paragraph(str).setFontSize(40).setFontColor(Color.RED).setFixedPosition(pageNumber,left,bottom,width));
            System.out.println("————————————————————————————————————我在这里");
            System.out.println("left="+left+"  bottom="+bottom+"  width="+width);
            document.close();



        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
}
