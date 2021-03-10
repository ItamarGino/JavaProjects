package merkellHellman;

import java.math.BigInteger;

public class MHkeys {
	
	
	
	public class privateKey {
		
		BigInteger[] w;
		BigInteger q;
		BigInteger r;
		
		public privateKey(BigInteger[] w,BigInteger q,BigInteger r)
		{
			this.w=w;
			this.r=r;
			this.q=q;
		}

	}
	
	
	
	private BigInteger[] b ;
	private privateKey privateK;
	
	
	public MHkeys()
	{
		
	}
	
	public MHkeys(BigInteger[] b,BigInteger[] w,BigInteger q,BigInteger r)
	{
		this.b=b;
		privateK=new privateKey(w,q,r);
	}
	
	
	
	public privateKey getPrivateKey()
	{
		return privateK;
	}
	
	public BigInteger[] getPublicKey()
	{
		return b;
	}
	
	
	
	
	
		
}
