package searchEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
		String content = "";
		if (ins == null) {
			System.out.println("Not found");
		} else {
			BufferedReader br = new BufferedReader((new InputStreamReader(ins)));
			String word;
			String fileName = "file";
			int i = 1;
			int j = 1;
			while ((word = br.readLine()) != null) {
//				System.out.println(word);
//				System.out.println("read File");
				System.out.println(j);
//				Document doc = Jsoup.connect(word).ignoreHttpErrors(true).timeout(50000).get();
				Connection.Response execute = Jsoup.connect(word).ignoreHttpErrors(true).timeout(100000).execute();
				Document doc = Jsoup.parse(execute.body());
				j++;
//				System.out.println(doc.text());
//				File uploadedFile = new File(path + File.separator + fileName);
				String webInfPath = getServletConfig().getServletContext().getRealPath("/WEB-INF");
				File path = new File(webInfPath + "/TextFolder");
				if (!path.exists()) {
				    path.mkdirs();
				}
				File newFile = new File(webInfPath + "/TextFolder/"+fileName+i+".txt");
				FileWriter fw = new FileWriter(newFile.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(word+"\n");
				bw.write(doc.text());
				bw.close();
//				System.out.println("File name"+ newFile.getAbsolutePath());
				i++;
//				item.write(uploadedFile);
//				String directoryName = "/WEB-INF/TextFolder";
//				OutputStream os = cntxt.getResourceAsStream(directoryName);
//				File textFolder = new File(directoryName);
//				if (!textFolder.exists()) {
//					textFolder.mkdir();
//				}
//				File textFile = new File(directoryName + "/" + fileName);
//				FileWriter fw = new FileWriter(textFile.getAbsoluteFile());
//				BufferedWriter bw = new BufferedWriter(fw);
//				bw.write(doc.text());
//				bw.close();
//				break;
//				System.out.println(fileName+i);
			}
			System.out.println("END!!!!!");
		}
	}
}
