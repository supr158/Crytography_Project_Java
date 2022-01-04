//Supratim Sahoo
//2019158
//Utility Java program for nibble related operations

public class Util {
	//Function for creating empty nibbles
	public static byte[][] createEmptyNibbles()
	{
		byte[][] matrix = new byte[2][];
		matrix[0] = new byte[2];
		matrix[1] = new byte[2];
		return matrix;
	}
	//Function for printing nibbles
	public static void printNibbles(byte[][] nibble)
	{
		for(int i=0;i<2;i++)
		{
		    for(int j=0;j<2;j++)
		    {
		        String output=Integer.toBinaryString(nibble[i][j]);
		        System.out.print(" ");
		        if(output.length()<4)
		        {
		        for(int k=0;k<4-output.length();k++)
		        System.out.print('0');
		        }
		        System.out.print(output);
		    }
		    System.out.println();
		}
	}
	//Function for converting nibbles to byte array
	public static byte[] nibblesToArray(byte[][] nibble)
	{
		byte[] array = new byte[4]; //Arrange by columns
		array[0] = nibble[0][0];
		array[1] = nibble[1][0];
		array[2] = nibble[0][1];
		array[3] = nibble[1][1];
		return array; 
	}
	//Function for converting byte array to nibbles
	public static byte[][] arrayToNibbles(byte[] array)
	{
		byte[][] nibble = createEmptyNibbles();
		nibble[0][0] = array[0];
		nibble[1][0] = array[1];
		nibble[0][1] = array[2];
		nibble[1][1] = array[3];
		return nibble;
	}
	//Function to convert binary bit string to byte
	public static byte bitsToByte(String bits)throws NumberFormatException
	{	
			return Byte.parseByte(bits, 2);
	}
	//Function to convert binary bit string to short
	public static short bitsToShort(String bits)throws NumberFormatException
	{
			return Short.parseShort(bits, 2);

	}
	//Main function used for testing
	public static void main(String[] args) {
		// test nibble creation
		byte[][] nibble = createEmptyNibbles();
		printNibbles(nibble); 
		nibble[0][0] = bitsToByte("0000");
		nibble[1][0] = bitsToByte("1111");
		nibble[0][1] = bitsToByte("1010");
		nibble[1][1] = bitsToByte("0101");
		printNibbles(nibble); 
		nibble[1][1] = bitsToByte("1111");
		printNibbles(nibble);
	}
	
	
}
	
