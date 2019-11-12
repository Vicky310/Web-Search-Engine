package searchEngine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HandleSearch
 */
@WebServlet("/handleSearch")
public class HandleSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(request.getParameter("searchBox"));
		String realContextPath = getServletContext().getRealPath("");
//		System.out.println("REAL CONTEXT PATH: " + realContextPath);
		try {
			
		
		//FileInputStream file = (FileInputStream) getServletContext().getResourceAsStream("urldataset.txt");
		BufferedReader file = new BufferedReader(new FileReader("D:\\Masters\\Advanced Computing Concepts\\Project\\Web-Search-Engine\\urldataset.txt"));
		String url = file.readLine();
		String content = null;
		System.out.println("read File");
		URLConnection connection = null;
		
		  connection =  new URL(url).openConnection();
		  Scanner scanner = new Scanner(connection.getInputStream());
		  scanner.useDelimiter("\\Z");
		  content = scanner.next();
		  scanner.close();
		  System.out.println(content);
		}catch ( Exception ex ) {
		    //ex.printStackTrace();
			System.out.print(ex);
		}
		
	}

}
