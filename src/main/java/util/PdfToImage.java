package util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class PdfToImage {
    public static final String DEFAULT_FORMAT = "jpg";

    public static void readPdf(PrintWriter writer, String fileName) {
        String pageContent = "";
        try {
            PdfReader reader = new PdfReader(fileName);
            PdfDocument pdfDoc = new PdfDocument(reader);

            int pageNum = pdfDoc.getNumberOfPages();
            for (int i = 1; i <= pageNum; i++) {
                pageContent += PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));//读取第i页的文档内容

            }
            writer.write(pageContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
    /*
    功能：将pdf文件转换为长图并保存
    @pdfPath:pdf文件路径
    @imgPath：图像保存路径
     */
    public static Image pdfToImage(String pdfPath) {
        //保存每张图片的像素值
        BufferedImage imageResult = null;
        Image imageR = null;
        try {
            //图像合并使用参数
            // 总宽度
            int width = 0;
            // 保存一张图片中的RGB数据
            int[] singleImgRGB;
            int shiftHeight = 0;
            //利用PdfBox生成图像
            PDDocument pdDocument = PDDocument.load(new File(pdfPath));
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            //循环每个页码
            for (int i = 0, len = pdDocument.getNumberOfPages(); i < len; i++) {

                BufferedImage image = renderer.renderImageWithDPI(i, 200, ImageType.RGB);
                int imageHeight = (int) (image.getHeight());
                int imageWidth = (int) (image.getWidth());
                //计算高度和偏移量
                //使用第一张图片宽度;
                width = imageWidth;
                //保存每页图片的像素值
                if(imageResult == null){
                    shiftHeight = imageHeight*len;
                    imageResult = new BufferedImage(width, imageHeight*len, BufferedImage.TYPE_INT_RGB);
                }
                //这里有高度，可以将imageHeight*len，我这里值提取一页所以不需要
                singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
                // 写入流中
                imageResult.setRGB(0, imageHeight*i, width, imageHeight, singleImgRGB, 0, width);



            }

            pdDocument.close();
            // 写图片
//            ImageIO.write(imageResult, "jpg", new File(imgPath));
            imageR =  imageResult.getScaledInstance(width/4*3,shiftHeight/3*2,Image.SCALE_SMOOTH);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            return imageR;
        }

    }

    public static void main(String[] args) throws IOException {
        String dest = "G:/test/计组实验2.pdf";
        String imgPath = "G:/test/test.jpg";
//        pdfToImage(dest, imgPath);
        JFrame f = new JFrame("test");
        f.setSize(400,300);
        f.setLocation(580,200);
        f.setLayout(null);

        JLabel l=new JLabel();
        ImageIcon i = new ImageIcon(imgPath);
        l.setIcon(i);
        l.setBounds(50,50,i.getIconWidth(),i.getIconHeight());

        JScrollPane sp=new JScrollPane(l);

        f.setContentPane(sp);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);


//        PdfWriter writer=new PdfWriter(dest);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document=new Document(pdf);
//        document.add(new Paragraph("hello World!"));
//
//        document.close();


    }
}
