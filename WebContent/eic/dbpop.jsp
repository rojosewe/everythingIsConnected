<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.rojosewe.eic.classes.WikiItem" %>
<%@page import="com.rojosewe.eic.fun.Perm" %>
<%@page import="com.rojosewe.eic.fun.EIC" %>
<%@page import="java.sql.Connection" %>
    
<%
    try
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
	catch(Exception e)
	{
		e.printStackTrace();
		response.sendRedirect("index.html");
	}
%>