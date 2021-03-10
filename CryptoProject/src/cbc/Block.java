package cbc;

import java.util.ArrayList;


public class Block {
	private ArrayList<Byte> block;
	private byte arr_block[];
	private int index=0;
	
	public Block() {
		this.block= new ArrayList<>();
		arr_block = new byte[8];
	}


	public void add(byte byte_value) {                  //add byte to arraylist which will consist of 8 bytes
		this.block.add(byte_value);						// arrayList
		arr_block[index++]=byte_value;					//array
	}

	
	
	public byte[] getBlock() {
		return arr_block;
	}
	
	public String toString(){
			return block.toString()+"";//overriding the toString() method  
			  
		  }

}