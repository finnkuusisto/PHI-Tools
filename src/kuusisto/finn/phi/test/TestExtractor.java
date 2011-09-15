package kuusisto.finn.phi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import kuusisto.finn.phi.Record;

public class TestExtractor {

	public static void main(String[] args) {
		File dataDir = new File("./data/");
		File[] files = dataDir.listFiles();
		PrintWriter out = null;
		try {
			out = new PrintWriter("./output/text.output");
			for (File file : files) {
				if (!file.isFile() || !file.getName().startsWith("lat")) {
					continue;
				}
				//valid file, read it
				FileInputStream in = null;
				try {
					in = new FileInputStream(file);
					out.println("*****************************");
					out.println(file.getName());
					out.println("*****************************");
					TestExtractor.readFile(in, out);
				}
				finally {
					in.close();
				}
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("Couldn't open file!");
			e.printStackTrace();
			return;
		}
		catch (IOException e) {
			System.err.println("Failed closing input file!");
			e.printStackTrace();
			return;
		}
		finally {
			out.close();
		}
	}
	
	private static void readFile(FileInputStream in, PrintWriter out)
			throws IOException {
		long numBytes = in.getChannel().size();
		byte[] curr = {-128};
		in.read(curr);
		for (long i = 1; i < numBytes;) {
			List<Byte> idBuffer = new ArrayList<Byte>();
			while (TestUtil.isBitSet(curr[0], 7) && i < numBytes) {
				idBuffer.add(curr[0]);
				in.read(curr);
				i++;
			}
			List<Byte> textBuffer = new ArrayList<Byte>();
			while (!TestUtil.isBitSet(curr[0], 7) && i < numBytes) {
				textBuffer.add(curr[0]);
				in.read(curr);
				i++;
			}
			//convert those suckers
			Record rec = new Record(idBuffer, textBuffer);
			out.println(rec.toString());
		}
	}
	
}
