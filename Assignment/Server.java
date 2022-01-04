//Supratim Sahoo
//2019158
// Java Program for Server side
import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Server {
	private KeyGenerator generator;
    	public Server() {
	}

	public KeyGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(KeyGenerator generator) {
		this.generator = generator;
	}
	//Function for S-AES decryption process which displays steps for the process
	public byte[][] decrypt(byte[][] message)
	{
		byte[][] nibble = message;
        System.out.println("Ciphertext nibbles:");
        Util.printNibbles(nibble);
		byte[][] key2 = generator.keyNibbles(4,5);
		byte[][] step1 = AddRoundKey.add(nibble, key2);
		System.out.println("After Pre-round transformation:");
		Util.printNibbles(step1);
		System.out.println("Round key K2:");
		Util.printNibbles(key2);

		byte[][] step2= ShiftRows.shift(step1);
		System.out.println("After Round 1 InvShift rows:");
		Util.printNibbles(step2);
		byte[][] step3 = SubNibbles.invertSubstitute(step2);
		System.out.println("After Round 1 InvSubstitute nibbles:");
		Util.printNibbles(step3);
		byte[][] key1 = generator.keyNibbles(2,3);
		byte[][] step4 = AddRoundKey.add(step3, key1);
		System.out.println("After Round 1 InvAdd round key:");
		Util.printNibbles(step4);
		System.out.println("Round key K1:");
		Util.printNibbles(key1);
		
		byte[][] step5 = MixColumns.invertMix(step4);
		System.out.println("After Round 1 InvMix columns:");
		Util.printNibbles(step5);
		byte[][] step6 = ShiftRows.shift(step5);
		System.out.println("After Round 2 InvShift rows:");
		Util.printNibbles(step6);
		byte[][] step7 = SubNibbles.invertSubstitute(step6);
		System.out.println("After Round 2 InvSubstitute nibbles:");
		Util.printNibbles(step7);
		byte[][] key0 = generator.keyNibbles(0,1);
		byte[][] step8 = AddRoundKey.add(step7, key0);
		System.out.println("After Round 2 Add round key:");
		Util.printNibbles(step8);
		System.out.println("Round Key K0:");
		Util.printNibbles(key0);

		byte[][] plainNibble = step8;
		
		return plainNibble;
	}
	
    	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
	{
		// Static getInstance method is called with hashing SHA
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		// digest() method called
		// to calculate message digest of an input
		// and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	
	public static int toInt_Value(byte[] hash)
	{
		// Convert byte array into signum representation
		BigInteger number = new BigInteger(1, hash);
        int num = number.intValue();
		return num;
		
	}

	// Returns modulo inverse of a with
	// respect to m using extended Euclid
	// Algorithm Assumption: a and m are
	// coprimes, i.e., gcd(a, m) = 1
	static long modInverse(long a, long m)
	{
		long m0 = m;
		long y = 0, x = 1;

		if (m == 1)
			return 0;

		while (a > 1) {
			// q is quotient
			long q = a / m;

			long t = m;

			// m is remainder now, process
			// same as Euclid's algo
			m = a % m;
			a = t;
			t = y;

			// Update x and y
			y = x - q * y;
			x = t;
		}

		// Make x positive
		if (x < 0)
			x += m0;

		return x;
	}
    
        // Recursive Java program
    // to compute modular power
static long exponentmod(long a,long b, long c)
{
		
	// Base cases
	if (a == 0)
		return 0;
	if (b == 0)
		return 1;
	
	// If B is even
	long y;
	if (b % 2 == 0)
	{
		y = exponentmod(a,b / 2,c);
		y = (y * y) % c;
	}
	
	// If B is odd
	else
	{
		y = a % c;
		y = (y * exponentmod(a, b - 1,	c) % c) % c;
	}
	
	return (long)((y + c) % c);
}

    //Function for RSA key generation
	public  static long RSA_key(long p,long q,long e)
	{
		long   z;
		z = (p - 1) * (q - 1);
		if(gcd(e,z)!=1)
		return 0;
		else
		{
		    long d=modInverse(e,z);
		    return d;
		}
	}
	//Function for RSA encryption
	public static long RSA_decrypt(long m,long e,long n)
	{
	    return exponentmod(m,e,n);
	}
    //Function for finding gcd
	static long gcd(long a, long b)
	{   
	    
	    if(a<b)
	    {
	        long temp;
	        temp=a;
	        a=b;
	        b=temp;
	    }
		if (b == 0)
			return a;
		else
			return gcd(a%b, b);
	}
	
	public static void main(String[] args)throws NoSuchAlgorithmException,NumberFormatException,Exception {
	    Scanner sc=new Scanner(System.in);

        Server saes = new Server();
        //Taking user input from Server side as whole numbers
        System.out.println("Supratim Sahoo(2019158)");
		System.out.print("Public key parameters:");
		long p=sc.nextLong();
		long q=sc.nextLong();
		long e=sc.nextLong();
		System.out.println();
		ServerSocket serverSocket = new ServerSocket(6789);
		while(true) {
        Socket clientSocket = serverSocket.accept();
		long n=p*q;

        PrintStream ps = new PrintStream(clientSocket.getOutputStream( ));
        //Sending data to Client 
		ps.println(e);//Server Public
		ps.println(n);//Key
		ps.flush( );
		 BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream( )));
		 //Accepting data from Client
	     long cipher = Long.parseLong(br.readLine( ));
         long enc_key=Long.parseLong(br.readLine());
         long sign=Long.parseLong(br.readLine());
         long client_key=Long.parseLong(br.readLine());
         long client_n=Long.parseLong(br.readLine());
         //Generating Server private key using Server public key
		long d=RSA_key(p,q,e);
		//Decrypting encrypted secret key using Server private key
		long sec_key=RSA_decrypt(enc_key,d,n);
		System.out.println("Decrypted Secret Key:"+sec_key);
		
		String key_str=Long.toBinaryString(sec_key);
	
		if(key_str.length()<16)
		{   
		    while(key_str.length()<16)
		    key_str="0"+key_str;
		}
	
		byte[] key = new byte[16];
		for(int i=0;i<16;i++)
		{
		    if(key_str.charAt(i)=='1')
		    key[i]=1;
		    else
		    key[i]=0;
		}
        //Generating round keys using secret key
		KeyGenerator withKey = new KeyGenerator(key);
		saes.setGenerator(withKey);
		withKey.generate(); 
		
		String cipher_str=Long.toBinaryString(cipher);
		if(cipher_str.length()<16)
		{
		    while(cipher_str.length()<16)
		    cipher_str="0"+cipher_str;
		}
		//Converting ciphertext into nibbles
		byte[][] cipherASNibbles = {{ Util.bitsToByte(cipher_str.substring(0,4)), Util.bitsToByte(cipher_str.substring(8,12)) }, 
									 { Util.bitsToByte(cipher_str.substring(4,8)), Util.bitsToByte(cipher_str.substring(12,16)) }};
        //Generating plaintext nibbles(Decryption)
		byte[][] decrypted = saes.decrypt(cipherASNibbles);
		//Converting plaintext nibbles into plaintext binary string 
		String dec_str="";
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                String s1=Integer.toBinaryString(decrypted[j][i]);
                if(s1.length()<4)
                {
                    while(s1.length()<4)
                    s1="0"+s1;
                }
                dec_str=dec_str+s1;
            }
            
        }
        //Converting plaintext binary string into plaintext whole number
        long plaintext=Long.parseLong(dec_str,2);
        System.out.println("Decrypted Plaintext:"+plaintext);
        //Calculating hash value
		String s=String.valueOf(plaintext);
        long hash=(long)toInt_Value(getSHA(s));
        System.out.println("Message Digest:"+hash);
        //Generating intermediate verification code
       long sign1=RSA_decrypt(sign,client_key,client_n);
       System.out.println("Intermediate verification code:"+sign1);
       long hash_mod=hash%client_n;
       if(hash_mod==sign1)
       System.out.println("Signature Verified");
       else
       System.out.println("Signature Not Verified");
}
}
}