import java.math.BigInteger;

public class encryptedMessage_and_signature {

	private byte[][] encryptedMessage;
	private BigInteger signature;
	private byte[] iv;
	
	public encryptedMessage_and_signature(byte[][] encryptedMessage,BigInteger signature,byte[] iv)
	{
		this.encryptedMessage=encryptedMessage;
		this.signature=signature; 
		this.iv=iv;
	}
	
	public BigInteger getSignature()
	{
		return signature;
	}
	
	public byte[] getIV()
	{
		return iv;
	}
	
	public byte[][] getEncryptedMessage()
	{
		return encryptedMessage;
	}
	
}
