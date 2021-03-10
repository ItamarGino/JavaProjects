import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cbc.CBC;
import merkellHellman.MHK_Crypto_w_Arrays;
import merkellHellman.MHkeys;
import rabin.RabinWilliams;

public class sideTwo {// bob

	private MHK_Crypto_w_Arrays crypto;
	private MHkeys keys;
	private String xteaKey;
	private CBC cbc;
	private BigInteger rabinPublicKey;

	// calculate MH keys
	public sideTwo() {

		crypto = new MHK_Crypto_w_Arrays();
		keys = crypto.genKeys();


	}

	// return public MH key
	public BigInteger[] getPublicKey() {
		return keys.getPublicKey();
	}

	public void setRabinKey(BigInteger n) {
		rabinPublicKey = n;
	}

	// save the private xtea key
	public void decryptXteaKey(String encryptedXteaKey) {
		xteaKey = crypto.decryptMsg(encryptedXteaKey, keys.getPrivateKey());
		System.out.println("xtea Key after decription=" + xteaKey);
	}

	public void decrypMessage(encryptedMessage_and_signature msg) throws IOException // get encrypt message
	{
		cbc = new CBC(xteaKey);
		cbc.setIV(msg.getIV());
		byte[][] encryptedMsg =msg.getEncryptedMessage();
		byte[][] decipher = cbc.decrypt(encryptedMsg);

		if((new RabinWilliams()).verify(encryptedMsg[1], msg.getSignature(), rabinPublicKey) == false) {
			System.out.println("bad signature!");
			return;
		}
		int i;
		System.out.println("\n\t\t******** Decipher text: GreyImage after decryption of Xtea+Cbc********");
		for(i =0; i< 5;i++) {
		System.out.print(Arrays.toString(decipher[i])+",");
		}
		System.out.println(Arrays.toString(decipher[i]));
		
		
		byte[] decipherArray=matrix_to_Array(decipher);
		DisplayImageFromByteArray(decipherArray);
	}
	
	
	private byte[] matrix_to_Array(byte[][] decipher) {
		byte[] temp = new byte[decipher.length*decipher[0].length];
		for(int i = 0; i < decipher.length; i ++) {
			for(int s = 0; s < decipher[i].length; s ++) {
				temp[i*8+s] = decipher[i][s];
			}
		}
		return temp;
	}
	
	
	private void DisplayImageFromByteArray(byte[] temp) throws IOException{
		
    ByteArrayInputStream bis = new ByteArrayInputStream(temp);
    BufferedImage bImage2 = ImageIO.read(bis);
    ImageIO.write(bImage2, "png", new File("GreyImage/result.png"));
    
    System.out.println("\n\t\t********Image received in SideTwo After decryption ********");
    
		BufferedImage img=ImageIO.read(new File("GreyImage/result.png"));
      ImageIcon icon=new ImageIcon(img);
      JFrame frame=new JFrame("After Decryption");
      frame.setLayout(new FlowLayout());
      frame.setLocation(img.getWidth(),0);

      frame.setSize(img.getHeight(),img.getWidth());
      JLabel lbl=new JLabel();
      lbl.setIcon(icon);
      frame.add(lbl);
      frame.setVisible(true);
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
