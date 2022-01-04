//Supratim Sahoo
//2019158
//Java program for substitute nibbles
import java.util.*;

public class SubNibbles {

	private static Map<Byte, Byte> lookupTable = new HashMap<Byte, Byte>();
	private static Map<Byte, Byte> inverseLookupTable = new HashMap<Byte, Byte>();
	
	static {
		byte n0  = Util.bitsToByte("0000"), s0  = Util.bitsToByte("1001");
		byte n1  = Util.bitsToByte("0001"), s1  = Util.bitsToByte("0100");
		byte n2  = Util.bitsToByte("0010"), s2  = Util.bitsToByte("1010");
		byte n3  = Util.bitsToByte("0011"), s3  = Util.bitsToByte("1011");
		byte n4  = Util.bitsToByte("0100"), s4  = Util.bitsToByte("1101");
		byte n5  = Util.bitsToByte("0101"), s5  = Util.bitsToByte("0001");
		byte n6  = Util.bitsToByte("0110"), s6  = Util.bitsToByte("1000");
		byte n7  = Util.bitsToByte("0111"), s7  = Util.bitsToByte("0101");
		byte n8  = Util.bitsToByte("1000"), s8  = Util.bitsToByte("0110");
		byte n9  = Util.bitsToByte("1001"), s9  = Util.bitsToByte("0010");
		byte n10 = Util.bitsToByte("1010"), s10 = Util.bitsToByte("0000");
		byte n11 = Util.bitsToByte("1011"), s11 = Util.bitsToByte("0011");
		byte n12 = Util.bitsToByte("1100"), s12 = Util.bitsToByte("1100");
		byte n13 = Util.bitsToByte("1101"), s13 = Util.bitsToByte("1110");
		byte n14 = Util.bitsToByte("1110"), s14 = Util.bitsToByte("1111");
		byte n15 = Util.bitsToByte("1111"), s15 = Util.bitsToByte("0111");;
	    //Filling up the lookupTable
		lookupTable.put(n0, s0);
		lookupTable.put(n1, s1);
		lookupTable.put(n2, s2);
		lookupTable.put(n3, s3);
		lookupTable.put(n4, s4);
		lookupTable.put(n5, s5);
		lookupTable.put(n6, s6);
		lookupTable.put(n7, s7);
		lookupTable.put(n8, s8);
		lookupTable.put(n9, s9);
		lookupTable.put(n10, s10);
		lookupTable.put(n11, s11);
		lookupTable.put(n12, s12);
		lookupTable.put(n13, s13);
		lookupTable.put(n14, s14);
		lookupTable.put(n15, s15);
		//Filling up the inverseLookupTable
		inverseLookupTable.put(s0, n0);
		inverseLookupTable.put(s1, n1);
		inverseLookupTable.put(s2, n2);
		inverseLookupTable.put(s3, n3);
		inverseLookupTable.put(s4, n4);
		inverseLookupTable.put(s5, n5);
		inverseLookupTable.put(s6, n6);
		inverseLookupTable.put(s7, n7);
		inverseLookupTable.put(s8, n8);
		inverseLookupTable.put(s9, n9);
		inverseLookupTable.put(s10, n10);
		inverseLookupTable.put(s11, n11);
		inverseLookupTable.put(s12, n12);
		inverseLookupTable.put(s13, n13);
		inverseLookupTable.put(s14, n14);
		inverseLookupTable.put(s15, n15);
	}
	//Function which returns substituted nibbles for input nibbles by looking at the  lookupTable
	public static byte[][] substitute(byte[][] nibble)
	{
		byte[][] subs = Util.createEmptyNibbles();
		for (int i = 0; i < 2; i++)
		{
			for (int j =0; j < 2; j++)
			{
				byte value = lookupTable.get(nibble[i][j]);
				subs[i][j] = value;
			}
		}
		return subs;
	}
	//Function which returns inverse substituted nibbles for input nibbles by looking at the inverseLookupTable
	public static byte[][] invertSubstitute(byte[][] nibble)
	{
		byte[][] subs= Util.createEmptyNibbles();
		for (int i = 0; i < 2; i++)
		{
			for (int j =0; j < 2; j++)
			{
				byte value = inverseLookupTable.get(nibble[i][j]);
				subs[i][j] = value;
			}
		}
		return subs;
	}
	
	
	public static byte subNib(byte nibble) 
	{
		return lookupTable.get(nibble);
	}
	
	public static byte invertSub(byte nibble)
	{
		return inverseLookupTable.get(nibble);
	}
	//Main function used for testing
	public static void main(String[] args) {
		byte[][] nibble = Util.createEmptyNibbles();
		nibble[0][0] = Util.bitsToByte("1010");
		nibble[1][0] = Util.bitsToByte("0000");
		nibble[0][1] = Util.bitsToByte("1111");
		nibble[1][1] = Util.bitsToByte("0011");
		byte[][] subst = substitute(nibble);
		Util.printNibbles(nibble);
		Util.printNibbles(subst);
	}
	
}