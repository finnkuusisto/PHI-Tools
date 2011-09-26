package kuusisto.finn.phi.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestCatoSearch {

	public static void main(String[] args) {
		Scanner in = null;
		try {
			in = new Scanner(new File("./output/text.output"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		//read first 3 lines
		String[] lines = new String[4];
		lines[1] = TestCatoSearch.stripCodesEtc(in.nextLine());
		lines[2] = TestCatoSearch.stripCodesEtc(in.nextLine());
		lines[3] = TestCatoSearch.stripCodesEtc(in.nextLine());
		//XXX
		//System.out.println(lines[1]);
		//System.out.println(lines[2]);
		//System.out.println(lines[3]);
		//XXX
		//continue until end of file
		int line = 1;
		while (in.hasNextLine()) {
			//shift lines back
			lines[0] = lines[1];
			lines[1] = lines[2];
			lines[2] = lines[3];
			//and read the next line
			lines[3] = TestCatoSearch.stripCodesEtc(in.nextLine());
			//XXX
			//System.out.println(lines[3]);
			//XXX
			//check it for Cato TODO
			if (TestCatoSearch.hasCato(lines)) {
				System.out.println("Found Cato on line " + line + "!");
				System.out.println("\"" + lines[0] + "\"");
				System.out.println();
			}
			line++;
		}
	}
	
	public static boolean hasCato(String[] lines) {
		final String CATO = "cato";
		final String OTAC = "otac";
		String left = "";
		String right = "";
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].length() > 0) {
				left += lines[i].charAt(0);
				right += lines[i].charAt(lines[i].length() - 1);
			}
		}
		return CATO.equalsIgnoreCase(left) || OTAC.equalsIgnoreCase(left) ||
				CATO.equalsIgnoreCase(right) || OTAC.equalsIgnoreCase(right);
	}
	
	public static String stripCodesEtc(String text) {
		//remove beta codes
		//1. stuff that may or may not be followed by digits
		text = text.replaceAll("[@&%#\\$][0-9]*", "");
		//2. must be followed by digits
		text = text.replaceAll("[\"][0-9]+", "");
		//3. braces
		text = text.replaceAll("[\\{\\}\\[\\]\\<\\>][0-9]*", "");
		//4. misc.
		text = text.replaceAll("\\^n", "");
		text = text.replaceAll("'", "");
		text = text.replaceAll("S1", "");
		text = text.replaceAll("S2", "");
		text = text.replaceAll("S3", "");
		text = text.replaceAll("J", "");
		//and punctuation
		text = text.replaceAll("\\p{Punct}", "");
		//trim that sucker
		return text.trim();
	}
	
}
