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
	
	private boolean hasCitationN() {
		for (int i = 0; i < this.bytes.length; i++) {
			byte left = (byte)(this.bytes[i] & RecordID.LEFT_NIBBLE);
			if (left == RecordID.L_N_LEVEL) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasIncrement() {
		for (int i = 0; i < this.bytes.length; i++) {
			byte left = (byte)(this.bytes[i] & RecordID.LEFT_NIBBLE);
			byte right = (byte)(this.bytes[i] & RecordID.RIGHT_NIBBLE);
			if (right == RecordID.R_INCREMENT &&
				(left == RecordID.L_Z_LEVEL ||
				left == RecordID.L_Y_LEVEL ||
				left == RecordID.L_X_LEVEL ||
				left == RecordID.L_W_LEVEL ||
				left == RecordID.L_V_LEVEL ||
				left == RecordID.L_N_LEVEL)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		//TODO this is pretty useless so far
		StringBuilder str = new StringBuilder();
		if (this.hasCitationN()) {
			str.append("N");
		}
		if (this.hasIncrement()) {
			str.append(",INC");
		}
		return str.toString();
	}
	
	//////////////////
	//Bit-mask stuff//
	//////////////////
	
	private static final byte LEFT_NIBBLE =  RecordID.mask("11110000");
	private static final byte RIGHT_NIBBLE = RecordID.mask("00001111");        

	private static final byte L_Z_LEVEL = RecordID.mask("10000000");
	private static final byte L_Y_LEVEL = RecordID.mask("10010000");
	private static final byte L_X_LEVEL = RecordID.mask("10100000");
	private static final byte L_W_LEVEL = RecordID.mask("10110000");
	private static final byte L_V_LEVEL = RecordID.mask("11000000");
	private static final byte L_N_LEVEL = RecordID.mask("11010000");
	private static final byte L_ESCAPE =  RecordID.mask("11100000");
	private static final byte L_SPECIAL = RecordID.mask("11110000");
	
	private static final byte R_INCREMENT = RecordID.mask("00000000");
	
	private static byte mask(String mask) {
		if (mask == null || mask.length() != 8) {
			throw new IllegalArgumentException();
		}
		byte ret = 0;
		for (int i = 0; i < mask.length(); i++) {
			//the String "bits" are in visual order
			if (mask.charAt(i) == '0') {
				ret = Utils.unsetBit(ret, (8 - i - 1));
			} else {
				ret = Utils.setBit(ret, (8 - i - 1));
			}
		}
		return ret;
	}

}