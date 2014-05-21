package com.rojosewe.eic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rojosewe.eic.classes.WikiItem;
import com.rojosewe.eic.fun.Perm;

/**
 * Servlet implementation class Autocomplete
 */
public class Autocomplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Autocomplete() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * {
		 query:'Li',
		 suggestions:['Liberia','Libyan Arab Jamahiriya','Liechtenstein','Lithuania'],
		 data:['LR','LY','LI','LT']
		}
    */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String suggestions = "";
		String query = request.getParameter("query");
		ArrayList<WikiItem> items = Perm.getItemSuggestions(query);
		StringBuilder sb = new StringBuilder();
		sb.append("{query:'"+query+"',suggestions:[");
		if(!items.isEmpty())
		{
			for (WikiItem wikiItem : items) {
				sb.append("'"+wikiItem.getItem()+"',");
			}	
		}
		sb.append("]}");
		suggestions = sb.toString();
		response.setContentType("application/json");
		response.setStatus(200);
		PrintWriter out = response.getWriter();
		out.write(suggestions);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
