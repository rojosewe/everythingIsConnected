package com.rojosewe.eic.fun.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rojosewe.eic.classes.WikiItem;
import com.rojosewe.eic.fun.EIC;
import com.rojosewe.eic.fun.Perm;

public class DBPopulate extends HttpServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		try
		{
			dbpopulate(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void dbpopulate(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String root = request.getParameter("root");
		if(root == null)
		{
			response.sendError(500);
			return;
		}
		WikiItem item = new WikiItem(root);
		try {
			Connection con = Perm.connect();
			item.get(con);
			con.close();
			
			if(item.getId() != null && item.getVisited() > 0)
			{
				response.sendRedirect("index.html?indexed="+root);
				return;
			}
			else
			{
				if(!EIC.GetWikipediaExists(item))
				{
					response.sendRedirect("index.html?nowiki="+root);
					return;
				}
			}
		} catch (Exception e) {
			response.sendError(404);
			e.printStackTrace();
			return;
		}
		EIC.start(root);
		response.sendRedirect("index.html?indexing="+root);
	}
	
}
