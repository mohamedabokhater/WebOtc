package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.LoginResponse;
import models.PublicChannel;
import models.SearchResponse;

/**
 * Servlet implementation class SearchNickNameServlet
 */
@WebServlet("/SearchNickNameServlet")
public class SearchNickNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchNickNameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<PublicChannel> ChannelNames = new ArrayList<PublicChannel>();
		Gson gson = new GsonBuilder().create();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}
		String nickname = gson.fromJson(jsonFileContent.toString(),String.class);
		ServletContext c=getServletContext();
		ChannelNames=PublicChannel.GetChannelSearchByNickName(c, nickname);
		SearchResponse res = new SearchResponse(true,ChannelNames );
		try {
        	response.setContentType("application/json; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.println(gson.toJson(res, SearchResponse.class));
        	out.close();
           
            	
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		
		
	}

}
