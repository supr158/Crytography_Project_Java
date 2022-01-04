//Supratim Sahoo
//2019158
//Java program for ShiftRows operation
public class ShiftRows {
    //Function for ShiftRows operation(used for shifting 2nd row of nibble)
	public static byte[][] shift(byte[][] nibble)
	{
		byte[][] res = Util.createEmptyNibbles();
		res[0][0] = nibble[0][0];
		res[0][1] = nibble[0][1];
		res[1][0] = nibble[1][1];
		res[1][1] = nibble[1][0];
		return res;
	}
	//Main function used for testing
	public static void main(String[] args) {
		byte [][] nibble = Util.createEmptyNibbles();
		nibble[0][0] = Util.bitsToByte("0000");
		nibble[1][0] = Util.bitsToByte("1010");
		nibble[0][1] = Util.bitsToByte("0101");
		nibble[1][1] = Util.bitsToByte("1111");
		Util.printNibbles(nibble);
		byte[][] shifted = shift(nibble);
		Util.printNibbles(shifted);
	}
}