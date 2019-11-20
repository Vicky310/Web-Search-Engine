
public class BuildInvertedIndex {
	
	int count;

	Map<Integer, String> sources;
	HashMap<String, HashSet<Integer>> index;

	public practiceTrieInvIndex() {
		// TODO Auto-generated constructor stub
		sources = new HashMap<Integer, String>();
		index = new HashMap<String, HashSet<Integer>>();

	}

	public void buildIndex(String[] files) {
		int i = 0;
		for (String fileName : files) {

			try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {

				sources.put(i, fileName);
				String ln;

				while ((ln = file.readLine()) != null) {
					String[] words = ln.split("\\W+");
					for (String word : words) {
						word = word.toLowerCase();
						if (!index.containsKey(word)) {
							index.put(word, new HashSet<Integer>());

						}
						index.get(word).add(i);

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;

		}
	}

	public void find(String phrase) {
		String[] words = phrase.split("\\W+");
		HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
		HashMap<Integer, String> filesWhereWordExists = new HashMap<>();
		HashMap<String, Integer> countOfwordWithFileName = new HashMap<>();
		for (String word : words) {
			res.retainAll(index.get(word));
		}

		System.out.println("res yha hai mc: " + res);
		if (res.size() == 0) {
			System.out.println("Not found");
			return;
		}
		System.out.println("Found in: ");

		int i = 0;
		for (int num : res) {

			filesWhereWordExists.put(i, sources.get(num));
			i++;
			System.out.println("\t" + sources.get(num));

		}

		String phraseNew = phrase.toLowerCase();
		for (int j = 0; j < res.size(); j++) {
			System.out.println(filesWhereWordExists.get(j));

			In mydata = new In(filesWhereWordExists.get(j));
			String fileText = mydata.readAll().toLowerCase();

			KMP kmp = new KMP(phraseNew);

			int finalOffset = 0;
			int count = 0;
			int offset = kmp.search(fileText);
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

	}


}
