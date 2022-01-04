//Supratim Sahoo
//2019158
// Java Program for Client side
import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Client 
{    
    private KeyGenerator generator;
	
	public Client() {
	}

	public KeyGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(KeyGenerator generator) {
		this.generator = generator;
	}
	//Function for S-AES encryption process which displays steps of the process
	public byte[][] encrypt(byte[][] message)
	{   
		byte[][] nibble = message;
		System.out.println("Plaintext as nibbles:");
		Util.printNibbles(nibble);
	    byte[][] key0 = generator.keyNibbles(0,1);
	    byte[][] step1 = AddRoundKey.add(nibble, key0);
	    System.out.println("After Pre-round transformation:");
	    Util.printNibbles(step1);
		System.out.println("Round key K0:");
		Util.printNibbles(key0);
		byte[][] step2 = SubNibbles.substitute(step1);
		System.out.println("After Round 1 Substitute nibbles:");
		Util.printNibbles(step2);
		byte[][] step3 = ShiftRows.shift(step2);
		System.out.println("After Round 1 Shift Rows:");
		Util.printNibbles(step3);
		byte[][] step4 = MixColumns.mix(step3);
		System.out.println("After Round 1 Mix Columns:");
		Util.printNibbles(step4);
		byte[][] key1 = generator.keyNibbles(2,3);
		byte[][] step5 = AddRoundKey.add(step4, key1);
		System.out.println("After Round 1 Add Round Key:");
		Util.printNibbles(step5);
		System.out.println("Round key K1:");
		Util.printNibbles(key1);
		byte[][] step6 = SubNibbles.substitute(step5);
		System.out.println("After Round 2 Substitute nibbles:");
		Util.printNibbles(step6);
		byte[][] step7 = ShiftRows.shift(step6);
		System.out.println("After Round 2 Shift Rows:");
		Util.printNibbles(step7);
		byte[][] key2 = generator.keyNibbles(4,5);
		byte[][] step8 = AddRoundKey.add(step7, key2);
		System.out.println("After Round 2 Add round key:");
		Util.printNibbles(step8);
		System.out.println("Round key K2:");
		Util.printNibbles(key2);
		byte[][] cipherNibble = step8;
		return cipherNibble;
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
	public static long RSA_encrypt(long m,long e,long n)
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
	//  Main function
public static void main (String[] args)throws NoSuchAlgorithmException,NumberFormatException,Exception {
    Scanner sc= new Scanner(System.in); 
    Socket clientSocket = new Socket("127.0.0.1", 6789);
    DataOutputStream outToServer =new DataOutputStream(clientSocket.getOutputStream());
    BufferedReader inFromServer =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    //Accepting data from server
    long server_key= Long.parseLong(inFromServer.readLine());
    long server_n=Long.parseLong(inFromServer.readLine());
    //Taking user input from client side as whole numbers
    System.out.println("Supratim Sahoo(2019158)");
    System.out.print("Message:");
    long msg=sc.nextLong();
    System.out.println();
    System.out.print("Secret Key:");
    long sec_key=sc.nextLong();
    System.out.println();
    System.out.print("Public Key parameters:");
    long p=sc.nextLong();//p ->prime
    long q=sc.nextLong();//q ->prime(p!=q)
    long e=sc.nextLong();//1<=e<=z;z=(p-1)*(q-1);gcd(e,z)=1
    System.out.println();
    //Generating client private key using client public key
    long d=RSA_key(p,q,e);
    long n=p*q;
    //Encrypting secret key using server public key
    long enc_key=RSA_encrypt(sec_key,server_key,server_n);
    System.out.println("Encrypted Secret Key:"+enc_key);
    System.out.println();
    Client saes = new Client();
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
	String msg_str=Long.toBinaryString(msg);
	if(msg_str.length()<16)
		{
		    while(msg_str.length()<16)
		    msg_str="0"+msg_str;
		}
		//Converting message into nibbles
		byte[][] messageASNibbles = {{ Util.bitsToByte(msg_str.substring(0,4)), Util.bitsToByte(msg_str.substring(8,12)) }, 
									 { Util.bitsToByte(msg_str.substring(4,8)), Util.bitsToByte(msg_str.substring(12,16)) }};
        //Generating ciphertext nibbles(Encryption)
		byte[][] encrypted = saes.encrypt(messageASNibbles);
        //Coverting ciphertext nibbles into ciphertext binary string
		String enc_str="";
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                String s1=Integer.toBinaryString(encrypted[j][i]);
                if(s1.length()<4)
                {
                    while(s1.length()<4)
                    s1="0"+s1;
                }
                enc_str=enc_str+s1;
            }
            
        }
    //Converting ciphertext binary string into ciphertext whole number
    long ciphertext=Long.parseLong(enc_str,2);
    System.out.println("Ciphertext:"+ciphertext);
    //Calculating hash value
    String s=String.valueOf(msg);
    long hash=(long)toInt_Value(getSHA(s));
    System.out.println("Digest:"+hash);
    //Generating digital signature
    long sign=RSA_encrypt(hash,d,n);
    System.out.println("Digital Signature:"+sign);
    //Sending data to Server
    outToServer.writeBytes(ciphertext+"\n");//Ciphertext
    outToServer.writeBytes(enc_key+"\n");//Encrypted Secret Key
    outToServer.writeBytes(sign+"\n");//Digital Signature
    outToServer.writeBytes(e+"\n");//Client Public
    outToServer.writeBytes(n+"\n");//Key
    clientSocket.close();
	}
	
		
	}


