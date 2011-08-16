package kuusisto.finn.phi.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TestAuthorExtractor {

	public static void main(String[] args) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream("AUTHTAB.DIR"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open file!");
			e.printStackTrace();
			return;
		}
		PrintWriter out = null;
		try {
			out = new PrintWriter("author_output");
			byte[] curr = {-128};
			while (true) {
				//read the filename
				List<Byte> fnameList = new ArrayList<Byte>();
				for (int i = 0; i < 8; i++) {
					in.read(curr);
					if (curr[0] != 0 && curr[0] != ' ') {
						fnameList.add(curr[0]);
					}
				}
				String fname = TestAuthorExtractor.convertToString(fnameList);
				if (fname.equals("*END")) { break; }
				//read the author name
				List<Byte> anameList = new ArrayList<Byte>();
				in.read(curr);
				while (curr[0] <= 0x7f && curr[0] > 0) {
					anameList.add(curr[0]);
					in.read(curr);
				}
				String aname = TestAuthorExtractor.convertToString(anameList);
				//now burn to the end of the entry
				while (curr[0] != (byte)0xff) {
					in.read(curr);
				}
				while (curr[0] == (byte)0xff) {
					in.mark(1);
					in.read(curr);
				}
				in.reset(); //put char after 0xff back
				//now print our shit
				System.out.println("File: " + fname);
				System.out.println("Author: " + aname);
				System.out.println();
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
	
}
