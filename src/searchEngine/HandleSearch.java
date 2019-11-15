package searchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(request.getParameter("searchBox"));
		System.out.println("Reading URL data set");
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		ServletContext cntxt = getServletContext();
		String fName = "/WEB-INF/urlDataset.txt";
		InputStream ins = cntxt.getResourceAsStream(fName);
		if (ins == null) {
			System.out.println("Not found");
		} else {
			BufferedReader br = new BufferedReader((new InputStreamReader(ins)));
			String word;
			while ((word = br.readLine()) != null) {
				pw.println(word + "<br>");
			}
		}
	}
}
