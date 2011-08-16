package kuusisto.finn.phi.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class TestExtractor {

	public static void main(String[] args) {
		FileInputStream in = null;
		FileChannel inFC = null;
		long numBytes = 0;
		try {
			in = new FileInputStream("LAT0013.TXT");
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
			out = new PrintWriter("output");
			byte[] curr = {-128};
			in.read(curr);
			for (long i = 1; i < numBytes;) {
				List<Byte> idBuffer = new ArrayList<Byte>();
				while (TestExtractor.isBitSet(curr[0], 7) && i < numBytes) {
					idBuffer.add(curr[0]);
					in.read(curr);
					i++;
					if (i == 8192) { out.println("\n-----------END OF BLOCK-----------\n"); }
				}
				List<Byte> textBuffer = new ArrayList<Byte>();
				while (!TestExtractor.isBitSet(curr[0], 7) && i < numBytes) {
					textBuffer.add(curr[0]);
					in.read(curr);
					i++;
					if (i == 8192) { out.println("\n-----------END OF BLOCK-----------\n"); }
				}
				
				//convert those suckers
				String id = TestExtractor.convertToString(idBuffer);
				String text = TestExtractor.convertToString(textBuffer);
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
	
	public static String convertToString(List<Byte> list) {
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
	
	public static boolean isBitSet(long value, int n) {
		return (((1L << n) & value) != 0);
	}
	
}
