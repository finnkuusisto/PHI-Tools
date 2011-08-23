package kuusisto.finn.phi;

import java.io.UnsupportedEncodingException;

import kuusisto.finn.phi.test.TestUtil;

public class Utils {
	
	public static boolean isBitSet(long value, int n) {
		return (((1L << n) & value) != 0);
	}
	
	public static byte setBit(byte value, int n) {
		return (byte)((1 << n) | value);
	}
	
	public static byte unsetBit(byte value, int n) {
		if (TestUtil.isBitSet(value, n)) {
			return (byte)((1 << n) ^ value);
		}
		return value;
	}
	
	public static String bitString(byte val) {
		StringBuilder str = new StringBuilder();
		for (int i = 7; i >= 0; i--) {
			str.append(Utils.isBitSet(val, i) ? "1" : "0");
		}
		return str.toString();
	}
	
	public static String convertToASCII(byte[] bytes) {
		try {
			return new String(bytes, "ascii");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//use default encoding if ascii is a no-go
		String ret = new String(bytes);
		return ret;
	}
	
	public static void main(String[] args) {
		byte test = 0;
		test = Utils.setBit(test, 7);
		test = Utils.unsetBit(test, 6);
		System.out.println(Utils.bitString(test));
		System.out.println(test);
	}

}
