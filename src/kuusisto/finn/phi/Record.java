package kuusisto.finn.phi;

import java.util.List;

public class Record {
	
	public final RecordID ID;
	public final RecordText TEXT;
	
	public Record(byte[] id, byte[] text) {
		this.ID = new RecordID(id);
		this.TEXT = new RecordText(text);
	}
	
	public Record(List<Byte> id, List<Byte> text) {
		this.ID = new RecordID(id);
		this.TEXT = new RecordText(text);
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("ID:\n");
		str.append(this.ID);
		str.append("\n");
		str.append("TEXT:\n");
		str.append(this.TEXT);
		str.append("\n");
		return str.toString();
	}

}
