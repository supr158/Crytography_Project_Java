//Supratim Sahoo
//2019158
//Java program used for Generating Round Keys
public class KeyGenerator {

	private byte keyBits[];
	 
	protected short [] keyWords;
	
	
	/*
	 * Rcon is a round constant, defined as follows: RC[i] = xi+2, so that 
	 * RC[1] = x3 =1000 and RC[2]=x4mod(x4 +x+1)=x+1=0011. 
	 * RC[i] forms the leftmost nibble of a short, with the rightmost nibble being all zeros. 
	 * Thus, Rcon(1) = 10000000 and Rcon(2) = 00110000.
	 */
	static short roundCon1 = Util.bitsToShort("10000000");
	static short roundCon2 = Util.bitsToShort("00110000");
	
	public KeyGenerator(byte[] keyBits) {
		this.keyBits = keyBits;
		keyWords = new short[6];
	}
	//Generating w0 keyword
	public short genW0()
	{
		short w0 = 0;
		String wString = "";
		for (int i = 0; i < 8; i++)
		{
			wString = wString + keyBits[i];
		}
		w0 = Util.bitsToShort(wString);
		keyWords[0] = w0;
		return w0;
	}
	//Generating w1 keyword
	public short genW1()
	{
		short w1 = 0;
		String wString = "";
		for (int i = 8; i < 16; i++)
		{
			wString = wString + keyBits[i];
		}
		w1 = Util.bitsToShort(wString);
		keyWords[1] = w1;
		return w1;
	}
	//Generating w2 keyword
	public short genW2()
	{
		int round1 = 1;
		short w2 = (short) (keyWords[0] ^ g(keyWords[1], round1));
		keyWords[2] = w2;
		return w2;
	}
	//Generating w3 keyword
	public short genW3()
	{
		short w3 = (short) (keyWords[2] ^ keyWords[1]);
		keyWords[3] = w3;
		return w3;
	}
	//Generating w4 keyword
	public short genW4()
	{
		int round2 = 2;
		short w4 = (short) (keyWords[2] ^ g(keyWords[3], round2));
		keyWords[4] = w4;
		return w4;
	}
	//Generating w5 keyword
	public short genW5()
	{
		short w5 = (short) (keyWords[4] ^ keyWords[3]);
		keyWords[5] = w5;
		return w5;
	}
	//Function to fill 8 bits of a binary string
	public static String fill8Bits(String wordString)
	{
		while (wordString.length() < 8)
		{
			wordString = "0" + wordString;
		}
		return wordString;
	}
	//Function to fill 4 bits of a binary string
	public static String fill4Bits(String nibbleString)
	{
		while (nibbleString.length() < 4)
		{
			nibbleString = "0" + nibbleString;
		}
		return nibbleString;
	}
	//Function to split 8 bit word into two 4 bit nibbles
	public short[] splitWord(short word)
	{
		short[] twoNibbles = new short[2];
		String word2String = fill8Bits(Integer.toBinaryString(word) );
		twoNibbles[0] = Util.bitsToShort(word2String.substring(0, 4));
		twoNibbles[1] = Util.bitsToShort(word2String.substring(4));
		return twoNibbles;
	}
	//Function for generating round key nibbles based on round numbers
	public byte[][] keyNibbles(int round0, int round1)
	{
		short[] keyNibble1 = splitWord(keyWords[round0]);
		short[] keyNibble2 = splitWord(keyWords[round1]);
		byte[][] key = Util.createEmptyNibbles();
		key[0][0] = (byte)keyNibble1[0];
		key[1][0] = (byte)keyNibble1[1];
		key[0][1] = (byte)keyNibble2[0];
		key[1][1] = (byte)keyNibble2[1];
		return key;
	}
	//Function for rotating word nibbles
	public short[] rotateWordNibbles(short[] wordNibbles)
	{
		short[] newNibbles = new short[2];
		newNibbles[0] = wordNibbles[1];
		newNibbles[1] = wordNibbles[0];
		return newNibbles;
	}
	//Function for substituting word nibbles
	public short subWord(short [] wordNibbles)
	{
		short newWord;
		short[] subWord = new short[2];
		subWord[0] = SubNibbles.subNib((byte)wordNibbles[0]);
		subWord[1] = SubNibbles.subNib((byte)wordNibbles[1]);
		String nibble0 = fill4Bits(Integer.toBinaryString(subWord[0]));
		String nibble1 = fill4Bits(Integer.toBinaryString(subWord[1]));
		newWord = (short)( Integer.parseInt(nibble0 + nibble1, 2));
		return newWord;
	}
	//Helper function used for generating round keys
	public short g(short word, int round)
	{
		int rconst = 0;
		if (round == 1)
		{
			rconst = roundCon1;
		}
		if (round == 2)
		{
			rconst = roundCon2;
		}
		return (short)(rconst ^ subWord(rotateWordNibbles( splitWord(word) ) ) );
	}
	
	public void generate()
	{
		genW0();
		genW1();
		genW2();
		genW3();
		genW4();
		genW5();
	}
	
	//Main function used for testing
	public static void main(String[] args) {
		byte[] key = {0,0,1,0, 1,1,0,1,   
				      0,1,0,1, 0,1,0,1};
		KeyGenerator generator = new KeyGenerator(key);
		short w0 = generator.genW0();
		System.out.println("Key 0 = "+fill8Bits(Integer.toBinaryString(w0)));
		short w1 = generator.genW1();
		System.out.println("Key 1 = "+fill8Bits(Integer.toBinaryString(w1)));
		short w2 = generator.genW2();
		System.out.println("Key 2 = "+fill8Bits(Integer.toBinaryString(w2)));
		short w3 = generator.genW3();
		System.out.println("Key 3 = "+fill8Bits(Integer.toBinaryString(w3)));
		short w4 = generator.genW4();
		System.out.println("Key 4 = "+fill8Bits(Integer.toBinaryString(w4)));
		short w5 = generator.genW5();
		System.out.println("Key 5 = "+fill8Bits(Integer.toBinaryString(w5)));
	}
	
}