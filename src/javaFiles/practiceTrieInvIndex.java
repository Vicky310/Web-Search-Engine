package javaFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;




		
public class practiceTrieInvIndex {
	int count;

	String PATTERN="(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
	
	
	Map<Integer, String> sources;
	HashMap<String, HashSet<Integer>> index;

	public practiceTrieInvIndex() {
		// TODO Auto-generated constructor stub
		sources = new HashMap<Integer, String>();
		index = new HashMap<String, HashSet<Integer>>();

	}

	public void buildIndex(HttpServletRequest request) throws IOException {
		int fileNumber = 1;
		int i = 0;
		while(fileNumber < 1181) {
			String fName = "/WEB-INF/TextFolder/file"+fileNumber+".txt";
			ServletContext cntxt = request.getServletContext();
			InputStream ins = cntxt.getResourceAsStream(fName);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(ins))) {
				sources.put(i, "file"+fileNumber+".txt");
				String ln;
				
				
				while ((ln = br.readLine()) != null) {
					String[] words = ln.split("\\W+");
					
					for (String word : words) {
						word = word.toLowerCase();
						if (!index.containsKey(word)) {
							index.put(word, new HashSet<Integer>());

						}
						index.get(word).add(i);

					}
				}
				fileNumber++;
			} catch (IOException e) {
				System.out.println("HEllo MF");
				e.printStackTrace();    
			}
			i++;

		}
}
	
	
	 public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
	    { 
	        // Create a list from elements of HashMap 
	        List<Map.Entry<String, Integer> > list = 
	               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
	  
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
	            public int compare(Map.Entry<String, Integer> o1,  
	                               Map.Entry<String, Integer> o2) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	          
	        // put data from sorted list to hashmap  
	        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
	        for (Map.Entry<String, Integer> aa : list) { 
	            temp.put(aa.getKey(), aa.getValue()); 
	        } 
	        return temp; 
	    } 

	public String[] find(String phrase, HttpServletRequest request) throws IOException {
		System.out.println(phrase);
		String[] words = phrase.split("\\W+");
		for (String word : words) {
			System.out.println(word);
		}
		HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
		HashMap<Integer, String> filesWhereWordExists = new HashMap<>();
		HashMap<String, Integer> countOfwordWithFileName = new HashMap<>();
		for (String word : words) {
			res.retainAll(index.get(word));
		}

		System.out.println("res yha hai mc: " + res);
		if (res.size() == 0) {
			System.out.println("Not found");
//			return;
		}
		System.out.println("Found in: ");

		int i = 0;
		for (int num : res) {

			filesWhereWordExists.put(i, sources.get(num));
			i++;
			System.out.println("\t" + sources.get(num));

		}

		String phraseNew = phrase.toLowerCase();
		System.out.println("size of res is " + res.size());
		for (int j = 0; j < res.size(); j++) {
			System.out.println(filesWhereWordExists.get(j));

			
			KMP kmp = new KMP(phraseNew);
//			BoyerMoore bm = new BoyerMoore(phraseNew);
//			BruteForceMatch bfm = new BruteForceMatch();
			
//			BufferedReader file = new BufferedReader(new FileReader("/WEB-INF/TextFolder/"+filesWhereWordExists.get(j)));
//			Path filePath = Paths.get("TextFolder/"+filesWhereWordExists.get(j));
//			String fileText = new String(Files.readAllBytes(filePath));
			
			String fName = "/WEB-INF/TextFolder/"+filesWhereWordExists.get(j);
			ServletContext cntxt = request.getServletContext();
			InputStream ins = cntxt.getResourceAsStream(fName);
			BufferedReader br = new BufferedReader(new InputStreamReader(ins));
			String word;
			String fileText = "";
			while(br.readLine() != null) {
				word = br.readLine()+" ";
				fileText = word;
			}
			
			
			
//			In mydata = new In(filesWhereWordExists.get(j));
//			System.out.println("anything here?--------");
//			System.out.println(mydata);
//			String fileText = mydata.readAll();
//			System.out.println("here again sire look--------");
//			System.out.println(fileText);
			int finalOffset = 0;
			int count = 0;
			int offset = kmp.search(fileText);
//			int offset = bm.search(fileText); 
//			int offset = bfm.search1(phraseNew, fileText);
			if (offset == fileText.length()) {
				System.out.println(phraseNew + " is not found");
			} else {
				System.out.println(phraseNew + " is found at below positions:");
				offset = 0;
				int newSP = 0;
				while (offset < fileText.length()) {
					fileText = fileText.substring(offset + newSP).toLowerCase();

					offset = kmp.search(fileText);
					if (offset != fileText.length()) {
						finalOffset = finalOffset + offset;
						System.out.println(finalOffset);
						count++;
						newSP = phraseNew.length();
					}

				}

			}
			System.out.println("The number of occurences of " + phraseNew + " are " + count);

			countOfwordWithFileName.put(filesWhereWordExists.get(j), count);

		}
		System.out.println("-------###-------");
		System.out.println("finalyyy see the count of words");
		for(int pn = 0 ; pn < res.size(); pn++) {
		
			System.out.print(filesWhereWordExists.get(pn));
			System.out.println(countOfwordWithFileName.get(filesWhereWordExists.get(pn)));
			
			
			
		}
		//after sorting
		 Map<String, Integer> sortedCountOfWordWithFileNAme = sortByValue(countOfwordWithFileName);
		 for (Map.Entry<String, Integer> en : sortedCountOfWordWithFileNAme.entrySet()) { 
	            System.out.println("Key = " + en.getKey() +  
	                          ", Value = " + en.getValue()); 
	        } 
		 
		 int sizeOfSortedMap = sortedCountOfWordWithFileNAme.size();
		 System.out.println(sizeOfSortedMap);
		 
		 
		 ArrayList<String> finalArrayList = new ArrayList<>();
		 

		 if(sizeOfSortedMap <= 10) {
			 for (Map.Entry<String, Integer> en : sortedCountOfWordWithFileNAme.entrySet()) { 
		            finalArrayList.add(en.getKey()); 
		        }

		 }else {
			 int countToBreakAfter10 = 1;
			 for (Map.Entry<String, Integer> en : sortedCountOfWordWithFileNAme.entrySet()) {
				 	if(countToBreakAfter10 <= 10) {
				 		finalArrayList.add(en.getKey());	
				 	}else {
				 		break;
				 	}
				 	countToBreakAfter10++;
		             
		        }
		 }
		 
		 String finalURLArray[] = new String[10]; 
		 int idk = 0;
		 for(String al: finalArrayList) {
			
//			 BufferedReader file = new BufferedReader(new FileReader("/WEB-INF/TextFolder/"+al));
			 String fName = "/WEB-INF/TextFolder/"+al;
			 ServletContext cntxt = request.getServletContext();
			 InputStream ins = cntxt.getResourceAsStream(fName);
			 BufferedReader br = new BufferedReader(new InputStreamReader(ins));
			 String finalURL = br.readLine();
//			 for(int idk = 0; idk< finalArrayList.size(); idk++) {
				 finalURLArray[idk] = finalURL;
				 idk++;
//			 }
			 
			 
//			 System.out.println(al);
			 System.out.println(finalURL);
			 
		 }
		return finalURLArray; 

	}

}


//in the last file I am passing only the refined files to KMP to find the count of word. Like lets say from 1500 words, a word is found only in 30 files then only 
//30 files are passed to KMP for word count. The word here used is straightway, can be manipulated like refining the words like "what","is","a" amd then passing only the main word.
