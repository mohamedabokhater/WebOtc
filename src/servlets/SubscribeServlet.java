package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.PublicChannel;
import models.SubscribeResponse;
import models.Subscription;
import models.Users;

/**
 * Servlet implementation class SubscribeServlet
 */
@WebServlet("/SubscribeServlet")
public class SubscribeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscribeServlet() {
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
		
		Gson gson = new GsonBuilder().create();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}
		Subscription subscriber = gson.fromJson(jsonFileContent.toString(),Subscription.class);
		ServletContext c=getServletContext();
		
		boolean subscribed=subscriber.Subscribe(c);
		System.out.println(subscribed);
		
		try {
        	response.setContentType("application/json; charset=UTF-8");
        	PrintWriter out = response.getWriter();
            if(subscribed){
            	PublicChannel channel=  SubscribeResponse.GetChannel(c, subscriber.getChannel());
            	SubscribeResponse res=new SubscribeResponse(true, channel);
            	String temp = gson.toJson(res, SubscribeResponse.class);
            	out.println(gson.toJson(res, SubscribeResponse.class));
            	out.close();
            }
            else{
            	SubscribeResponse res=new SubscribeResponse(false, null);
            	String temp = gson.toJson(res, SubscribeResponse.class);
            	out.println(gson.toJson(res, SubscribeResponse.class));
            	out.close();
            }
            	
        } catch (IOException e) {  
            e.printStackTrace();  
        }

	}

}
