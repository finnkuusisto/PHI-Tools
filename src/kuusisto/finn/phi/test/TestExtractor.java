package kuusisto.finn.phi.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class TestExtractor {

	public static void main(String[] args) {
		FileInputStream in = null;
		FileChannel inFC = null;
		long numBytes = 0;
		try {
			in = new FileInputStream("./data/LAT0959.TXT");
			inFC = in.getChannel();
			numBytes = inFC.size();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open file!");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.err.println("Couldn't read byte count!");
			e.printStackTrace();
			return;
		}
		PrintWriter out = null;
		try {
			out = new PrintWriter("./output/text.output");
			byte[] curr = {-128};
			in.read(curr);
			for (long i = 1; i < numBytes;) {
				List<Byte> idBuffer = new ArrayList<Byte>();
				while (TestUtil.isBitSet(curr[0], 7) && i < numBytes) {
					idBuffer.add(TestUtil.unsetBit(curr[0], 7));
					in.read(curr);
					i++;
					if (i == 8192) { out.println("\n-----------END OF BLOCK-----------\n"); }
				}
				List<Byte> textBuffer = new ArrayList<Byte>();
				while (!TestUtil.isBitSet(curr[0], 7) && i < numBytes) {
					textBuffer.add(curr[0]);
					in.read(curr);
					i++;
					if (i == 8192) { out.println("\n-----------END OF BLOCK-----------\n"); }
				}
				
				//convert those suckers
				String id = TestUtil.convertToASCII(idBuffer);
				String text = TestUtil.convertToASCII(textBuffer);
				text = TestUtil.stripBetaCodes(text);
				out.println("ID:");
				out.println(id);
				out.println(idBuffer.toString());
				out.println("TEXT:");
				out.println(text);
				out.println(textBuffer.toString());
				out.println();
			}
		} catch (IOException e) {
			System.err.println("Error reading!");
			e.printStackTrace();
			return;
		} finally {
			out.close();
		}
	}
	
}
