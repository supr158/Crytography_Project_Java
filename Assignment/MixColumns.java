//Supratim Sahoo
//2019158
//Java program for Mix Columns operation
public class MixColumns {
     	

	static byte[][] multTable = new byte[16][];
	
	static {
	    //Filling the multiplication table in GF(16) mod x^4+x+1
		byte[] mult0 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		byte[] mult1 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		byte[] mult2 = {0,2,4,6,8,10,12,14,3,1,7,5,11,9,15,13};
		byte[] mult3 = {0,3,6,5,12,15,10,9,11,8,13,14,7,4,1,2};
		byte[] mult4 = {0,4,8,12,1,7,11,15,6,2,14,10,5,1,13,9};
		byte[] mult5 = {0,5,10,15,7,2,13,8,14,11,4,1,9,12,3,6};
		byte[] mult6 = {0,6,12,10,11,13,7,1,5,3,9,15,14,8,2,4};
		byte[] mult7 = {0,7,14,9,15,8,1,6,13,10,3,4,2,5,12,11};
		byte[] mult8 = {0,8,3,11,6,14,5,13,12,4,15,7,10,2,9,1};
		byte[] mult9 = {0,9,1,8,2,11,3,10,4,13,5,12,6,15,7,14};
		byte[] mult10 = {0,10,7,13,14,4,9,3,15,5,8,2,1,11,6,12};
		byte[] mult11 = {0,11,5,14,10,1,15,4,7,12,2,9,13,6,8,3};
		byte[] mult12 = {0,12,11,7,5,9,14,2,10,6,1,13,15,3,4,8};
		byte[] mult13 = {0,13,9,4,1,12,8,5,2,15,11,6,3,14,10,7};
		byte[] mult14 = {0,14,15,1,13,3,2,12,9,7,6,8,4,10,11,5};
		byte[] mult15 = {0,15,13,2,9,6,4,11,1,14,12,3,8,7,5,10};
		//multTable[i] contains results of i multiplied with values 1 to 15 in GF(16) mod x^4+x+1
		multTable[0] = mult0;
		multTable[1] = mult1;
		multTable[2] = mult2;
		multTable[3] = mult3;
		multTable[4] = mult4;
		multTable[5] = mult5;
		multTable[6] = mult6;
		multTable[7] = mult7;
		multTable[8] = mult8;
		multTable[9] = mult9;
		multTable[10] = mult10;
		multTable[11] = mult11;
		multTable[12] = mult12;
		multTable[13] = mult13;
		multTable[14] = mult14;
		multTable[15] = mult15;
		
	}
	//Function for addition (XOR)
	public static byte add(byte a, byte b)
	{
		return (byte)(a ^ b);
	}
	//Function for multiplication
	public static byte multiply(byte a, byte b)	{
		return multTable[a][b];
	}
	
	
	/*
	 * Performing the matrix multiplication, we get
	 * S(0,0)= S(0,0)⊕(4*S(1,0))
	 * S(1,0)= (4*S(0,0))⊕S(1,0)
	 * S(0,1)= S(0,1)⊕(4*S(1,1))
	 * S(1,1)= (4*S(0,1))⊕S(1,1) 	
	 */
	 //Function for Mixing of Columns
	public static byte[][] mix(byte[][] nibble) 
	{
		byte[][] result = Util.createEmptyNibbles();
		result[0][0] = add(nibble[0][0], multiply((byte)4, nibble[1][0]));
		result[1][0] = add(multiply((byte)4, nibble[0][0]), nibble[1][0]);
		result[0][1] = add(nibble[0][1], multiply((byte)4, nibble[1][1]));
		result[1][1] = add(multiply((byte)4, nibble[0][1]), nibble[1][1]);
		return result;
	}
	//Function for Inverse Mixing of Columns
	public static byte[][] invertMix(byte[][] nibble)
	{
		byte[][] result = Util.createEmptyNibbles();
		result[0][0] = add(multiply((byte)9, nibble[0][0]), multiply((byte)2, nibble[1][0]));
		result[1][0] = add(multiply((byte)2, nibble[0][0]), multiply((byte)9, nibble[1][0]));
		result[0][1] = add(multiply((byte)9, nibble[0][1]), multiply((byte)2, nibble[1][1]));
		result[1][1] = add(multiply((byte)2, nibble[0][1]), multiply((byte)9, nibble[1][1]));
		return result;
	}
	
	public static void main(String[] args) {
		byte[][] _9229 = Util.createEmptyNibbles();
		_9229[0][0] = 9;
		_9229[0][1] = 2;
		_9229[1][0] = 2;
		_9229[1][1] = 9;
		Util.printNibbles(_9229);
		System.out.println();
		byte[][] _1441 = Util.createEmptyNibbles();
		_1441[0][0] = 1;
		_1441[0][1] = 4;
		_1441[1][0] = 4;
		_1441[1][1] = 1;
		Util.printNibbles(_1441);
		System.out.println();
		byte[][] mult = new byte[2][2];
		mult[0][0] = multiply((byte)9, (byte)1);
		mult[0][1] = multiply((byte)2, (byte)4);
		mult[1][0] = multiply((byte)2, (byte)4);
		mult[1][1] = multiply((byte)9, (byte)1);
		Util.printNibbles(mult);
		System.out.println();
		byte[][] add = new byte[2][2];
		add[0][0] = MixColumns.add((byte)9, (byte)9);
		add[0][1] = MixColumns.add((byte)10, (byte)4);
		add[1][0] = MixColumns.add((byte)2, (byte)2);
		add[1][1] = MixColumns.add((byte)8, (byte)1);
		Util.printNibbles(add);
		System.out.println();
		byte[][] mix = mix(_9229);
		Util.printNibbles(mix);
		System.out.println();
		byte[][] inversemix = invertMix(_1441);
		Util.printNibbles(inversemix);
		System.out.println();
	}
	
	
}