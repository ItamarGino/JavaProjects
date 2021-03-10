package cbc;

import java.util.ArrayList;

public class Blocks {

	private ArrayList<Block> blocks;

	public Blocks() {
		this.blocks=new ArrayList<>();	
	}
	
	public void split_Blocks(byte plain_txt[]) {  //Create ArrayList of Byte  .each block of 64 bit(8 byte)
	  addBlock(plain_txt);
	}
	

	private void addBlock(byte[] plain_txt) {                  //send func add size of ArrayList with plain text
	    add(plain_txt, 0, plain_txt.length);
	  }

	 private void add(byte[] bytes, int offset, int length) {   //Create Block of 8 bytes. add to Blocks
		 int blocks_amount = 8-(length % 8 )+length;
		 byte byteAfterPadding[] = new byte[blocks_amount];
	        System.arraycopy(bytes, 0, byteAfterPadding, 0, length); 

		 blocks_amount /= 8;
	    for (int i = offset; i < blocks_amount; i++) {
	    	Block block = new Block();
	    	for(int j=0; j<8;j++) {                          //Each iteration of i, add 8 bytes to Block
	  	      block.add(byteAfterPadding[8*i+j]);                          
	    	}
	    	blocks.add(block);                            // Add Block to Blocks
	    }
	  }

	  public Block getArrayByteBlock(int i) {           //Return Block
	    return blocks.get(i);
	    }
	  
	  public ArrayList<Block> getArrayListByteBlocks() {           //Return Block
		    return blocks;
		    }
	  
	  
	  public String toString(){
			return blocks.toString()+"";//overriding the toString() method  
			  
		  }

	
	
	
	
	
	
	
	
	
}
