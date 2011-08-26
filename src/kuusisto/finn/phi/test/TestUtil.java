package kuusisto.finn.phi.test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import kuusisto.finn.phi.Utils;

public class TestUtil {
	
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
	
	public static String convertToASCII(List<Byte> list) {
		byte[] buf = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			buf[i] = list.get(i);
		}
		try {
			return new String(buf, "ascii");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String ret = new String(buf);
		return ret.trim();
	}
	
	public static String stripBetaCodes(String str) {
		if (str.split("&[0-9]*").length > 1 ||
				str.split("@[0-9]*").length > 1) {
			System.out.println("removing roman font codes from " + str);
			return str.replaceAll("[@&][0-9]*", "");
		}
		return str;
	}
	
	private static int bin7Bit(byte val) {
		return Utils.unsetBit(val, 7);
	}
	
	private static int bin14Bit(byte val1, byte val2) {
		return ((TestUtil.bin7Bit(val1) << 7) + TestUtil.bin7Bit(val2));
	}
	
	public static void main(String[] args) {
		byte test1 = 67;
		byte test2 = -112;
		System.out.println(Utils.bitString(test1));
		System.out.println(Utils.bitString(test2));
		System.out.println(Utils.bitString(TestUtil.bin14Bit(test1, test2)));
	}

}
