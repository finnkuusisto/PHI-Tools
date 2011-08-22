package kuusisto.finn.phi;

import java.util.List;

public class RecordText {
	
	private byte[] bytes;
	
	public RecordText(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public RecordText(List<Byte> bytes) {
		this.bytes = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++) {
			this.bytes[i] = bytes.get(i);
		}
	}
	
	public String toString() {
		//TODO this will currently include beta-codes
		return Utils.convertToASCII(this.bytes);
	}

}
