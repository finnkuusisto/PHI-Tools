package kuusisto.finn.phi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LatinAuthorTable {
	
	private List<String> authors;
	private List<String> files;
	
	private LatinAuthorTable() {
		this.authors = new ArrayList<String>();
		this.files = new ArrayList<String>();
	}
	
	public String getAuthorName(String fileName) {
		int index = this.files.indexOf(fileName);
		return (index < 0) ? null : this.authors.get(index);
	}
	
	public List<String> getAuthorNames() {
		return this.authors;
	}
	
	public String getFileName(String authorName) {
		int index = this.authors.indexOf(authorName);
		return (index < 0) ? null : this.files.get(index);
	}
	
	public String getFileCode(String authorName) {
		String fileName = this.getFileName(authorName);
		return (fileName == null) ? null : fileName.substring(3);
	}
	
	public List<String> getFileNames() {
		return this.files;
	}
	
	public static LatinAuthorTable fromFile(String filename) {
		BufferedInputStream in = null;
		try {
			FileInputStream fileInStream = 
				new FileInputStream(filename);
			in = new BufferedInputStream(fileInStream);
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open file!");
			e.printStackTrace();
			return null;
		}
		LatinAuthorTable ret = new LatinAuthorTable();
		try {
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
				String fname = Utils.convertToASCII(fnameList);
				if (fname.equals("*END")) {
					break;
				}
				//read the author name
				List<Byte> anameList = new ArrayList<Byte>();
				in.read(curr);
				while (curr[0] <= 0x7f && curr[0] > 0) {
					anameList.add(curr[0]);
					in.read(curr);
				}
				String aname = Utils.convertToASCII(anameList);
				aname = Utils.stripBetaCodes(aname);
				//now burn to the end of the entry
				while (curr[0] != (byte)0xff) {
					in.read(curr);
				}
				while (curr[0] == (byte)0xff) {
					in.mark(1);
					in.read(curr);
				}
				in.reset(); //put char after 0xff back
				//add those to the lists
				if (fname.startsWith("LAT")) {
					ret.authors.add(aname);
					ret.files.add(fname);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading!");
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				//man that's some bad luck
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		LatinAuthorTable lat = LatinAuthorTable.fromFile("./data/authtab.dir");
		for (String author : lat.getAuthorNames()) {
			System.out.println("Author: " + author);
			System.out.println("File:   " + lat.getFileName(author));
			System.out.println("Code:   " + lat.getFileCode(author));
			System.out.println();
		}
	}

}
