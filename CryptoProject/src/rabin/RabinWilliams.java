package rabin;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RabinWilliams {

	private BigInteger n, d;
	private static final BigInteger eight = BigInteger.valueOf(8);
	private static final BigInteger six = BigInteger.valueOf(6);
	private static final BigInteger three = BigInteger.valueOf(3);
	private static final BigInteger two = BigInteger.valueOf(2);
	private static final BigInteger seven = BigInteger.valueOf(7);
	private static final BigInteger sixteen = BigInteger.valueOf(16);

	private BigInteger getaModbPrime(int a, int b) {
		BigInteger p, modulo, residue;
		residue = BigInteger.valueOf(a);
		modulo = BigInteger.valueOf(b);
		Random r = new Random();
		do {
			p = BigInteger.probablePrime(200, r);
		} while (!p.mod(modulo).equals(residue));
		return p;
	}
	/***
	 * 
	 * @return BigInteger[] {n, d}
	 */
	public BigInteger[] createKeys() {
		BigInteger p = getaModbPrime(3, 8);
		BigInteger q = getaModbPrime(7, 8);

        
		BigInteger five = BigInteger.valueOf(5);
		BigInteger eight = BigInteger.valueOf(8);

		this.n = p.multiply(q);
		this.d = n.subtract(p);
		this.d = d.subtract(q);
		this.d = d.add(five);
		this.d = d.divide(eight);

		BigInteger[] keys = new BigInteger[2];
		keys[0] = n;
		keys[1] = d;
		return keys;
	}
	
	public void setN( BigInteger n) {
		this.n = n;
	}

	public BigInteger createSignature(byte[] msg) {		
		BigInteger m_gag = sixteen.multiply(make6Mod16(msg));
		m_gag=m_gag.multiply(BigInteger.valueOf(m_gag.signum()));
		m_gag = m_gag.add(six);
	
		//JacobiSymbol p = new JacobiSymbol();
		int j = JacobiSymbol.computeJacobiSymbol(m_gag, n);
		//System.out.print("\n" + j + "\n");
		BigInteger s = BigInteger.valueOf(0);

		if (j == 1) {
			//BigInteger s_temp = m_gag;
			s = m_gag.modPow(d, n);
		//	System.out.print("\n" + "n=" + n + " " + "d=" + d + " " + "mgag=" + m_gag + "s=" + s + "\n");
		}
		else if (j == -1) {
			//BigInteger s_temp = m_gag.divide(two);
			s = (m_gag.divide(two)).modPow(d, n);
			//System.out.print("\n" + "n=" + n + " " + "d=" + d + " " + "mgag=" + m_gag + "s=" + s + "\n");
		}

		return s;

	}

	public boolean verify(byte[] msg, BigInteger s, BigInteger n) {

		BigInteger m_tag = s.modPow(two, n);
//		System.out.print("\n" + "m_tag" + m_tag + "\n");

		BigInteger m_gag = BigInteger.valueOf(0);
		BigInteger m = BigInteger.valueOf(0);


	//	System.out.print("\n" + "m_tag mod8: " + (m_tag.mod(eight)) + "\n");

		if ((m_tag.mod(eight)).equals(six)) {
			m_gag = m_tag;
	//		System.out.print("\n" + "*1*" + "\n");
		}

		if ((m_tag.mod(eight)).equals(three)) {
			m_gag = two.multiply(m_tag);
	//		System.out.print("\n" + "*2*" + "\n");
		}

		if (m_tag.mod(eight).equals(seven)) {
			m_gag = n.subtract(m_tag);
	//		System.out.print("\n" + "*3*" + "\n");
		}

		if (m_tag.mod(eight).equals(two)) {
			m_gag = two.multiply(n.subtract(m_tag));
		//	System.out.print("\n" + "*4*" + "\n");
		}

		m = (m_gag.subtract(six));
		m=m.divide(sixteen);
		BigInteger BigInt_msg =new BigInteger(msg);
		if(BigInt_msg.signum() == -1) {
			m= m.multiply(BigInteger.valueOf(-1));
		}
	
		BigInteger ms = make6Mod16(msg);
		return ms.equals(m);
	}
	
	private BigInteger make6Mod16(byte[] msg) {
		BigInteger newmsg = new BigInteger(msg);
		BigInteger mod16;
		mod16=newmsg.mod(BigInteger.valueOf(16));
		newmsg =newmsg.subtract(mod16);
		newmsg = newmsg.add(BigInteger.valueOf(6));
		return newmsg;
	}
	

}
