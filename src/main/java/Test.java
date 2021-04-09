import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {
    JFrame f;


    public void produceTree(String filePath,DefaultMutableTreeNode pNode){

        File file= new File(filePath);
        if(file.isDirectory()){
            String[] paths = file.list();
            boolean test = true;
            for(int i = 0;i < paths.length;i++){
                DefaultMutableTreeNode node=new DefaultMutableTreeNode(paths[i]);
                pNode.add(node);
                String strPath = filePath+"/"+paths[i];
                if(new File(strPath).isDirectory()){
                    test = false;
                    produceTree(strPath,node);

                }
            }
            if(test){
                return;
            }
        }else{
            String[] str = filePath.split("[/]");
            DefaultMutableTreeNode child=new DefaultMutableTreeNode(str[str.length-1]);
            pNode.add(child);
            return;
        }
    }

    Test() {
        f = new JFrame();
        DefaultMutableTreeNode sort= new DefaultMutableTreeNode("食材分类");
        DefaultMutableTreeNode vagetable = new DefaultMutableTreeNode("蔬菜类");
        DefaultMutableTreeNode meat = new DefaultMutableTreeNode("肉类");
        sort.add(vagetable );
        sort.add(meat);
        sort.getUserObject();
        System.out.println(sort.getUserObject().toString());
        DefaultMutableTreeNode b = new DefaultMutableTreeNode("百香果");
        DefaultMutableTreeNode jin = new DefaultMutableTreeNode("泰国金桔");
        DefaultMutableTreeNode liulian = new DefaultMutableTreeNode("榴莲");
        DefaultMutableTreeNode ytao = new DefaultMutableTreeNode("樱桃");
        vagetable .add(b);
        vagetable .add(jin);
        vagetable .add(liulian);
        vagetable .add(ytao);

        DefaultMutableTreeNode c = new DefaultMutableTreeNode("牛肉");
        meat.add(c);

        JTree jt = new JTree(sort);
        f.add(jt);
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    public Test(String filePath){
        f = new JFrame();
        DefaultMutableTreeNode pNode = new DefaultMutableTreeNode(filePath);
        produceTree(filePath,pNode);
        JTree jt = new JTree(pNode);
        jt.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                JTree tree = (JTree) e.getSource();
                DefaultMutableTreeNode node= (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                Object object[] = node.getUserObjectPath();
                String str = object[0].toString();
                for(int i = 1;i<object.length;i++){
                    str = str + "/"+object[i].toString();
                }
                System.out.println(str);


            }
        });
        f.add(jt);

        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        String filePath = "G:/test";

        new Test(filePath);

    }

}
