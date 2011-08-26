package kuusisto.finn.phi;

public class Citation {

	//levels
	public boolean A;
	public boolean B;
	public boolean C;
	public boolean D;
	public boolean Z;
	public boolean Y;
	public boolean X;
	public boolean W;
	public boolean V;
	public boolean N;
	
	public boolean INCRMENT;
	public boolean BIN;
	public boolean BIN_ASCII;
	public boolean ASCII;
	
	//special codes
	public boolean EOB;
	public boolean EOF;
	public boolean EXCEPTION_START;
	public boolean EXCEPTION_END;
	
	//data
	public int bin;
	public String ascii;
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("<");
		if (this.EOB) {
			str.append("END-OF-BLOCK");
		}
		else if (this.EOF) {
			str.append("END-OF-FILE");
		}
		else if (this.EXCEPTION_START) {
			str.append("EXCEPTION-START");
		}
		else if (this.EXCEPTION_END) {
			str.append("EXCEPTION-END");
		}
		else {
			//determine level
			String[] levelStr = {"A","B","C","D","Z","Y","X","W","V","N"};
			boolean[] levelBool = {A,B,C,D,Z,Y,X,W,V,N};
			int index = -1;
			for (int i = 0; i < levelBool.length; i++) {
				if (levelBool[i]) { index = i; break; }
			}
			str.append(levelStr[index]);
			str.append(" - ");
			//determine tail
			if (this.INCRMENT) {
				str.append("INCREMENT");
			}
			else if (this.BIN) {
				str.append(this.bin);
			}
			else if (this.BIN_ASCII) {
				str.append(this.bin);
				str.append(" - ");
				str.append(this.ascii);
			}
			else {
				str.append(this.ascii);
			}
		}
		str.append(">");
		return str.toString();
	}
	
}
