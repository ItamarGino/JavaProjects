import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import rabin.RabinWilliams;


public class Main {

	//רק דברים מוצפנים יכולים לעבור פה
	public static void main(String[] args) throws IOException
	{
		sideOne Alice=new sideOne();
		sideTwo Bob= new sideTwo();

		System.out.print("start conection\n");
		/*     RABIN     */
		BigInteger rabinPublicKey = Alice.getPublicKeyRabin();
		System.out.println("\n\t\t********Alice creates Rabin private and public keys for digital signature********");
		System.out.println("Rabin signature public key: " + rabinPublicKey);
		Bob.setRabinKey(rabinPublicKey);

		/*     Merkle Hellman  - encryption of Alice Xtea private key with MH public key   */
		System.out.println("\n\t\t********Bob- created MH knapsack private and public keys for safe channel********");
		BigInteger[] publicMHK= Bob.getPublicKey(); 
		System.out.println("MH knapsack public key: " + Arrays.toString(publicMHK));

		System.out.println("\n\t\t********Alice- encrypt Xtea private key with MH public key********");
		String encrypted_PrivateXteaKey=Alice.encryptPrivateXteaKey(publicMHK);                        
		if(encrypted_PrivateXteaKey==null)
			return;
		System.out.println("Encrypted Xtea private key: " + encrypted_PrivateXteaKey);

		/*    Merkle Hellman  - decryption of Bob Xtea private key with his MH private key   */
		System.out.println("\n\t\t********Bob- decrypt Xtea private key with his MH private key********");
		Bob.decryptXteaKey(encrypted_PrivateXteaKey);


		/*     CBC,Xtea,Rabin - encryption of Alice greyImage and Rabin signature on first block of GreyImage  */
		encryptedMessage_and_signature encryptMsg_and_sign = Alice.encryptMessage();
		if(encryptMsg_and_sign==null)
			return;
		int i;
		System.out.println("\n\t\t********Alice- Cipher text: GreyImage after encryption of Xtea+Cbc********");
		for(i =0; i< 5;i++) {
			System.out.print(Arrays.toString((encryptMsg_and_sign.getEncryptedMessage())[i])+",");
		}
		System.out.println(Arrays.toString((encryptMsg_and_sign.getEncryptedMessage())[i]));
		Bob.decrypMessage(encryptMsg_and_sign);


	}


}
