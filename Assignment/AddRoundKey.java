//Supratim Sahoo
//2019158
//Java program for Add Round Key operation
public class AddRoundKey {
    //Function used for Add Round Key operation
	public static byte[][] add(byte[][] nibble0, byte[][] nibble1) 
	{
		byte[][] res=Util.createEmptyNibbles();
		for(int i=0;i<2;i++)
		{
		    for(int j=0;j<2;j++)
		    {
		        res[i][j]=(byte)(nibble0[i][j]^nibble1[i][j]);
		    }
		}
		return res;
	}
	//Main functio used for testing
	public static void main(String[] args) {
		byte[][] nibble0 = Util.createEmptyNibbles();
		byte[][] nibble1 = Util.createEmptyNibbles();
		nibble0[0][0] = Util.bitsToByte("0110");
		nibble0[1][0] = Util.bitsToByte("1111");
		nibble0[0][1]=Util.bitsToByte("0110");
		nibble0[1][1]=Util.bitsToByte("1011");
		Util.printNibbles(nibble0);
		System.out.println();
		nibble1[0][0] = Util.bitsToByte("1010");
		nibble1[1][0] = Util.bitsToByte("0111");
		nibble1[0][1]=Util.bitsToByte("0011");
		nibble1[1][1]=Util.bitsToByte("1011");
		Util.printNibbles(nibble1);
		System.out.println();
		byte[][] added = add(nibble0, nibble1);
		Util.printNibbles(added);
	}
}