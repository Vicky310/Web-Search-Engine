
public class BuildMain {

	public static void main(String args[]) throws IOException {
		
		practiceTrieInvIndex ptiv = new practiceTrieInvIndex();
		ptiv.buildIndex(new String[] {"abc.txt", "abc2.txt", "abc3.txt"});
		
		System.out.println("Enter phrase to be searched");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		String phrase = br.readLine();
		ptiv.find(phrase);
		
	}


	
}
