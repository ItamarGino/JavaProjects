package cbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import xtea.Xtea;

/*
 * class CBC:  gets all the ciphered blocks from xtea. encrypt/decrypt it
 * return: 2D Array of bLocks
 */
public class CBC{

	private ArrayList<Block> blocks;
    private byte[][] encrypte_2D_Array;
    private byte[][] decrypted_2D_Array;
    private Xtea xtea;
	 
	private  byte[] iv = { (byte)0x52, (byte)0x6b,(byte)0x2a, (byte)0x29, (byte)0xfa, (byte)0x6a, (byte)0x92, (byte)0xfe };  //initialize iv
    private static final int EIGHT =8 ;
	
    public CBC(String key) {                  //constructor set privatekey to Xtea
		this.blocks=new ArrayList<Block>();	
	     xtea = new Xtea(key);
	}
	
	public void setIV(byte[] iv) {
		this.iv=iv;
	}
	public byte[] getIV() { 
		return this.iv;
	}
	
	public byte[] setRandomIV() {     //random IV for each grey image
		Random rand;
		int seedNo = 255; // to generate value in that particular range.(2^7= 256)
		int sValue = 0;
		rand = new Random();
		while (sValue < 8) {
			this.iv[sValue] = (byte)(rand.nextInt(seedNo));
			sValue++;
		}
		return this.iv;
	}
	
	public byte[][] encrypt(ArrayList<Block> blks) {         // plaintext block XOR previous block. send block to xtea and repeat
		this.blocks=blks;
		encrypte_2D_Array =new byte[blks.size()][EIGHT];

		encrypte_2D_Array[0]=xorByteArrays(iv,blocks.get(0).getBlock());
		encrypte_2D_Array[0]=this.xtea.encrypt(encrypte_2D_Array[0]);
		for(int i=0; i <this.blocks.size()-1;i++) {
			encrypte_2D_Array[i+1]=(xorByteArrays(encrypte_2D_Array[i],blocks.get(i+1).getBlock()));	
			encrypte_2D_Array[i+1]=xtea.encrypt(encrypte_2D_Array[i+1]);
		}
		return encrypte_2D_Array;

	}

	public byte[][] decrypt(byte[][] ciphertxt) {         // send block to Xtea and XOR with previous encipher block 
		int blocks_amount = ciphertxt.length;
		decrypted_2D_Array =new byte[blocks_amount][EIGHT];   // or ciphertxt[0].length for col
		 
		decrypted_2D_Array[0]=xtea.decrypt(Arrays.copyOf(ciphertxt[0], ciphertxt[0].length));
		decrypted_2D_Array[0]=xorByteArrays(iv,decrypted_2D_Array[0]);
		
		
			for(int i=0; i <blocks_amount-1;i++) {
				decrypted_2D_Array[i+1]=xtea.decrypt(Arrays.copyOf(ciphertxt[i+1], ciphertxt[i+1].length));
				decrypted_2D_Array[i+1]=xorByteArrays(ciphertxt[i],decrypted_2D_Array[i+1]);	
		}
			return decrypted_2D_Array;

	}



	/**
	 * Xors byte arrays
	 * @param firstArr
	 * @param secondArr
	 * @return xored
	 */
	private byte[] xorByteArrays(byte[] firstArr, byte[] secondArr){   
		byte [] xordArr = new byte[8];
		for(int i =0; i<firstArr.length;i++){
			xordArr[i] = (byte) (firstArr[i] ^ secondArr[i]);
		}				
		return xordArr;
	}
	
	
	

}

