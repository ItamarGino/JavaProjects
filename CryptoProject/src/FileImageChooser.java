import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class FileImageChooser {

	
	public void fileImageRunner(String filePath) {
		
		SwingUtilities.invokeLater(new Runnable(){		
			@Override
			public void run() {
				BufferedImage img = null;
			   try {
				img = ImageIO.read(new File (filePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			   
		        ImageIcon icon=new ImageIcon(img);
		        JFrame frame=new JFrame("Image Before Encryption");
		        frame.setLayout(new FlowLayout());
		        frame.setSize(img.getHeight(),img.getWidth());
		        JLabel lbl=new JLabel();
		        lbl.setIcon(icon);
		        frame.add(lbl);
		        frame.setVisible(true);
			}
		});
	}
	public String selectGreyImage() {
	    JFileChooser fileChooser = new JFileChooser();
	    
        int returnVal = fileChooser.showOpenDialog(fileChooser);
        String filePath=null;
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	filePath = fileChooser.getSelectedFile().getAbsolutePath();
        }
        else {
        	System.out.println("\nUser Cancled Image Encryption Process");
        	return null;
        }
        File file = new File(filePath);
        try {
			RenderedImage image = ImageIO.read(file);
	        int index = filePath.lastIndexOf("\\");

            ImageIO.write(image, "png", new File("GreyImage"+filePath.substring(index)));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        fileImageRunner(filePath);
        int index = filePath.lastIndexOf("\\");
       // System.out.println(filePath.substring(index));
        return filePath.substring(index) ;
	}
	 
}
