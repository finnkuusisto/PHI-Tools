package kuusisto.finn.phi;

import java.util.List;

public class RecordID {
	
	private byte[] bytes;
	
	public RecordID(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public RecordID(List<Byte> bytes) {
		this.bytes = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++) {
			this.bytes[i] = bytes.get(i);
		}
	}
	
	//TODO more citation types and perhaps a different interface
	
	private int indexOfLeftNibble(byte nibble, int from) {
		for (int i = from; i < this.bytes.length; i++) {
			byte left = (byte)(this.bytes[i] & RecordID.LEFT_NIBBLE);
			if (left == nibble) {
				return i;
			}
		}
		return -1;
	}
	
	private int indexOfLeftNibble(byte nibble) {
		return this.indexOfLeftNibble(nibble, 0);
	}
	
	private int indexOfByte(byte val, int from) {
		for (int i = from; i < this.bytes.length; i++) {
			if (this.bytes[i] == val) {
				return i;
			}
		}
		return -1;
	}
	
	private int indexOfByte(byte val) {
		return this.indexOfByte(val, 0);
	}
	
	@Override
	public String toString() {
		//TODO this is pretty useless so far
		StringBuilder str = new StringBuilder();
		str.append("\n[");
		for (int i = 0; i < this.bytes.length; i++) {
			str.append(Utils.bitString(this.bytes[i]));
			if (i < this.bytes.length - 1) {
				str.append(",");
			}
		}
		str.append("]\n");
		byte[] text = Utils.unsetBit(this.bytes, 7);
		str.append(Utils.convertToASCII(text));
		return str.toString();
	}
	
	///////////
	//Parsing//
	///////////
	
	
	
	/////////////
	//Bit-masks//
	/////////////
	
	private static final byte LEFT_NIBBLE =  Utils.mask("11110000");
	private static final byte RIGHT_NIBBLE = Utils.mask("00001111");
	
	private static final byte L_Z_LEVEL = Utils.mask("10000000");
	private static final byte L_Y_LEVEL = Utils.mask("10010000");
	private static final byte L_X_LEVEL = Utils.mask("10100000");
	private static final byte L_W_LEVEL = Utils.mask("10110000");
	private static final byte L_V_LEVEL = Utils.mask("11000000");
	private static final byte L_N_LEVEL = Utils.mask("11010000");
	private static final byte L_ESCAPE =  Utils.mask("11100000");
	private static final byte L_SPECIAL = Utils.mask("11110000");
	
	private static final byte R_INCREMENT = Utils.mask("00000000");
	private static final byte R_A_LEVEL =   Utils.mask("00000001");
	private static final byte R_B_LEVEL =   Utils.mask("00000010");
	private static final byte R_C_LEVEL =   Utils.mask("00000011");
	private static final byte R_D_LEVEL =   Utils.mask("00000100");
	
	private static final byte EOS = Utils.mask("11111111");
	private static final byte EOB = Utils.mask("11111110");
	private static final byte EOF = Utils.mask("11110000");
	
	private static final byte EXCEPTION_START = Utils.mask("11111000");
	private static final byte EXCEPTION_END =   Utils.mask("11111001");

}
