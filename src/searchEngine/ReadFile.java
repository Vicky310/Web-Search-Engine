package searchEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;


public class ReadFile {
	public static String readUrl() throws IOException {
		String content = "";
		Path path = Paths.get("urlDataset.txt");
		content = new String(Files.readAllBytes(path));
		System.out.println(content);
	    return content;
	}
	
	public static void main(String args[]) throws IOException {
		readUrl();
	}

}
