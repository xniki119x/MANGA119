package f1cont.niki119.manga119.gui;

import f1cont.niki119.manga119.helpers.LogHelper;
import f1cont.niki119.manga119.type.ImageWBMP;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

public class Gui extends JFrame implements LogHelper {

    public static int WIDTH = 256;
    public static int HEIGHT = 256;

    public JPanel panel;
    public GridBagConstraints GBC = new GridBagConstraints();
    public GridBagLayout GBL = new GridBagLayout();
    public JTextField tf;

    public Gui(){
        tf = new JTextField();
        tf.setText("500");
        panel = new JPanel(GBL);
        panel.add(tf);
        panel.setTransferHandler(new TransferHandler(){
            @Override
            public boolean canImport(TransferSupport support) {
                return true;
            }

            @Override
            public boolean importData(JComponent comp, Transferable t) {
                int f = Integer.parseInt(tf.getText());
                try {
                    java.util.List<File> files = (java.util.List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
                    for(File file : files) {
                        BufferedImage image = ImageIO.read(file);
                        ImageWBMP wbmp = new ImageWBMP(image, f);
                        File f_image = new File(file.getName().substring(0,file.getName().indexOf(".")) + ".wbmp");
                        FileOutputStream fos = new FileOutputStream(f_image);
                        fos.write(wbmp.getFullData());
                        fos.flush();
                        fos.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return super.importData(comp, t);
            }
        });
        add(panel);
        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screen_size.width/2-WIDTH/2,screen_size.height/2-HEIGHT/2,WIDTH,HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


}
