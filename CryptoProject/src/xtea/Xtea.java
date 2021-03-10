package xtea;

public class Xtea 
{
	/*	Inner class	for iteration initialization	*/
	public final static class IterationSpec
	{
		int _iterations;
		int _deltaSumInitial;
		
		IterationSpec(int iterations,int deltaSumInitial) {
			_iterations=iterations;
			_deltaSumInitial=deltaSumInitial;
		}
	}
	/*	Constants - Number of iteration (64) ; Delta constant (Necessary for the algorithm)	*/
	private static final int DELTA_SUM_64 =  0x61C88647 * 64;
	private static final int DELTA =  0x61C88647;
	public final static IterationSpec ITERATIONS64 = new IterationSpec(64, DELTA_SUM_64);
	/*	An IterationSpec's instance and an Keys array */
	private final IterationSpec _iterationSpec;
	private int[] _key;
    /* Constructor */ 
    public Xtea(int[] key)
    {
    	this(key, ITERATIONS64);	
    }
	/**
	 * creates an XTEA object from the given String key and iterations count.
	 * @param key used in ecryption/decryption routine.
	 * @param iterationSpec - containing iterations count.
	 */
	public Xtea(String key, IterationSpec iterationSpec) 
	{
		this(new KeyGenerator().core(key), iterationSpec);
	}

	/**
	 * creates an XTEA object from the given String key. The default value of rounds is 64;
	 * @param key used in ecryption/decryption routine.
	 */
	public Xtea(String key) 
	{
		this(new KeyGenerator().core(key), ITERATIONS64);
	}
	/**
	 * creates an XTEA object from an int array of four
	 * @throws IllegalArgumentException
	 */
	public Xtea(int[] key, IterationSpec iterationSpec) throws IllegalArgumentException 
	{
		if (key.length != 4) 
		{
			throw new IllegalArgumentException();
		}
		_key = key;
		_iterationSpec=iterationSpec;
	}
	/**
	 * converts incoming array of eight bytes (64 bits, as requires) from offset to array of two integer values.
	 * (An Integer is represented in memory as four bytes)
	 * @param bytes - Incoming byte array of length eight to be converted
	 * @param offset - Offset from which to start converting bytes
	 * @param res - Int array of length two which contains converted array bytes.
	 */
	private void byte2int(byte[] bytes, int offset, int[] res) 
	{
		res[0] = (int) ((((int) bytes[offset] & 0xff) << 24)
				| (((int) bytes[offset + 1] & 0xff) << 16)
				| (((int) bytes[offset + 2] & 0xff) << 8) 
				| ((int) bytes[offset + 3] & 0xff));
		
		res[1] = (int) ((((int) bytes[offset + 4] & 0xff) << 24)
				| (((int) bytes[offset + 5] & 0xff) << 16)
				| (((int) bytes[offset + 6] & 0xff) << 8) 
				| ((int) bytes[offset + 7] & 0xff));
	}
	/**
	 * converts incoming array of two integers from offset to array of eight bytes (64 bits, as requires).
	 * (An Integer is represented in memory as four bytes)
	 * @param i - Incoming integer array of two to be converted.
	 * @param offset - from which to start converting integer values.
	 * @param res - byte array of length eight which contains converted integer array i.
	 */
	private void int2byte(int[] i, int offset, byte[] res) 
	{
		// i[0] to byte
		res[offset] = (byte) ((i[0] >> 24) & 0xff);
		res[offset + 1] = (byte) ((i[0] >> 16) & 0xff);
		res[offset + 2] = (byte) ((i[0] >> 8) & 0xff);
		res[offset + 3] = (byte) (i[0] & 0xff);
		// i[1] to byte
		res[offset + 4] = (byte) ((i[1] >> 24) & 0xff);
		res[offset + 5] = (byte) ((i[1] >> 16) & 0xff);
		res[offset + 6] = (byte) ((i[1] >> 8) & 0xff);
		res[offset + 7] = (byte) (i[1] & 0xff);
	}
	/**
	 * enciphers two int values
	 * @param block - int array to be encipher according to the XTEA encryption algorithm.
	 */
	private void encipher(int[] block)
	{
		int n = _iterationSpec._iterations;
		int delta_sum = 0;
		while (n-- > 0) 
		{
			block[0] += ((block[1] << 4 ^ block[1] >> 5) + block[1]) ^ (delta_sum + _key[delta_sum & 3]);
			delta_sum += DELTA;
			block[1] += ((block[0] << 4 ^ block[0] >> 5) + block[0]) ^ (delta_sum + _key[delta_sum >> 11 & 3]);
		}
	}
	/**
	 * deciphers two int values
	 * @param e_block - int array to be decipher according to the XTEA encryption algorithm.
	 */
	private void decipher(int[] e_block) 
	{
		int delta_sum = _iterationSpec._deltaSumInitial;
		int n = _iterationSpec._iterations;
		while (n-- > 0) 
		{
			e_block[1] -= ((e_block[0] << 4 ^ e_block[0] >> 5) + e_block[0]) ^ (delta_sum + _key[delta_sum >> 11 & 3]);
			delta_sum -= DELTA;
			e_block[0] -= ((e_block[1] << 4 ^ e_block[1] >> 5) + e_block[1]) ^ (delta_sum + _key[delta_sum & 3]);
		}
	}
	/**
	 * encrypts incoming byte array according XTEA
	 * @param buffer - incoming byte array to be encrypted.
	 * @return 
	 */
	public byte[] encrypt(byte[] buffer) 
	{
		int[] asInt = new int[2];
		for (int i = 0; i < buffer.length; i += 8) 
		{
			byte2int(buffer, i, asInt);	
			encipher(asInt);
			int2byte(asInt, i, buffer);
		}
		
		return buffer;
		
	}
	/**
	 * decrypts incoming byte array according XTEA
	 * @param buffer - incoming byte array to be decrypted
	 */
	public byte[] decrypt(byte[] buffer) 
	{
		int[] asInt = new int[2];
		for (int i = 0; i < buffer.length; i += 8) 
		{
			byte2int(buffer, i, asInt);
			decipher(asInt);
			int2byte(asInt, i, buffer);
		}
		
		return buffer;
	}
	/* Just for the example
	public static void main(String[] args)
	{
		// The key will be encrypted and changed by KeyGenerator().core(key)
		int[] key = new int[] {1, 2, 3, 4};
		String key2 = "abcdefgh";
		// An eight chars String convert into 64 bits array
		byte[] message = "absolute".getBytes();
		// Creating an instance of Xtea class with 128 bits key
		Xtea myXtea = new Xtea(key);
		
		System.out.println("Original Text:");
		String str = new String(message);
		System.out.println(str);
		
		myXtea.encrypt(message);
		System.out.println("After the Encryption:");
		String str2 = new String(message);
		System.out.println(str2);
		
		myXtea.decrypt(message);
		System.out.println("After the Decryption: (Back to original)");
		String str3 = new String(message);
		System.out.println(str3);
	}
	*/
}