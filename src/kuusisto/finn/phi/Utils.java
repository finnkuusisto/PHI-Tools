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

}
