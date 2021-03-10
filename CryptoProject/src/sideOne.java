
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Arrays;

import cbc.Blocks;
import cbc.CBC;
import merkellHellman.MHK_Crypto_w_Arrays;
import rabin.RabinWilliams;

public class sideOne {//Alice
	
	
	private byte[] plaintext;
	private MHK_Crypto_w_Arrays crypto;
	private String xteaPrivateKey;
	private CBC cbc;
	private BigInteger rabinN;
	private RabinWilliams rabin;
	 
   public sideOne()
   
   {
	   crypto=new MHK_Crypto_w_Arrays();
	   
	 //calculate the private xtea key. for example we get 
	   xteaPrivateKey= "sapirvhtabcdefgh";
	   cbc= new CBC (xteaPrivateKey);
	   rabin = new RabinWilliams();
	   BigInteger[] rabinKeys= rabin.createKeys();
	   rabinN=rabinKeys[0];		
		//System.out.println("/nrabin "+rabinN+"  "+rabinP+"  "+rabinQ+"\n");
	  
   }
   
   public String encryptPrivateXteaKey(BigInteger[] publicMHK)  
   {
	  return crypto.encryptMsg(xteaPrivateKey, publicMHK); 
   }
   
  
   
	// return public rabin key
	public BigInteger getPublicKeyRabin() {
		return rabinN;
	}

	public BigInteger RabinSign(byte[] msg) {
		return rabin.createSignature(msg);
	}
	
   public encryptedMessage_and_signature encryptMessage() //return encrypt picture
   {
		FileImageChooser fp=new FileImageChooser();
		String imageName=fp.selectGreyImage();
		if(imageName==null)
			return null;
		File fi = new File("GreyImage"+imageName);
		try {
			this.plaintext = Files.readAllBytes(fi.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(plaintext == null)
			return null;
		Blocks blocks = new Blocks();
		blocks.split_Blocks(plaintext);
		System.out.println("\n\t\t******** Plain text: Grey Image ********");
		System.out.println(blocks.getArrayListByteBlocks().subList(0, 6));
		byte[] iv= cbc.setRandomIV();
		byte[][] cipher = cbc.encrypt(blocks.getArrayListByteBlocks());
		
		
		BigInteger sign = RabinSign(cipher[1]);
		encryptedMessage_and_signature msgAndSign = new encryptedMessage_and_signature(cipher, sign,iv);
		return msgAndSign;

	}

	

}
