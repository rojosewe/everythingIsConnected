package com.rojosewe.eic.fun;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Connection;

import com.rojosewe.eic.classes.ConnectedLinks;
import com.rojosewe.eic.classes.Item2item;
import com.rojosewe.eic.classes.Node;
import com.rojosewe.eic.classes.Paragraph;
import com.rojosewe.eic.classes.WikiItem;
import com.rojosewe.eic.fun.Conf;
import com.rojosewe.eic.fun.Perm;
import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class Perm {

	public static Connection connect() throws ClassNotFoundException, SQLException 
    {
		Connection con = null;
		if (con == null) {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Conf.JDBC, Conf.USERJDBC, Conf.PSSWDJDBC);
		}
		return con;
    }
	
	public static void saveNode(Node node) 
	{

        try
        {
			Connection con = connect();
			ArrayList<Node> queue = new ArrayList<Node>();
	        queue.add(node);
	        for (int i = 0; i < queue.size(); i++)
	        {
	            saveNodeItem(queue, i, con);
	        }
	        con.close();
	    }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}

	private static void saveNodeItem(ArrayList<Node> queue, int i, Connection con) 
	{
		Node u = queue.get(i);
        queue.addAll(u.getChildren());
    	u.getItem().setVisited(0);
    	if (i == 0)
        {
            u.getItem().setVisited(2);
        }
    	u.getItem().insert(con);
    	u.getItem().get(con);
        if (u.getParent() != null && i != 0)
        {
            Item2item i2i = new Item2item();
            i2i.setItem1(u.getParent().getItem().getId());
            i2i.setItem2(u.getItem().getId());
            i2i.setParagraph_id(u.getItem().getParagraph_id());
            i2i.insert(con);
        }
	}

	public static Paragraph saveParagraph(String paragraph)
	{
		Paragraph par = new Paragraph();
        par.setText(paragraph);
        try
        {
			Connection con = connect();
			par.insert(con);
			par.get(con);
	        con.close();
	    }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return par;
	}
	
	public static void uprank(WikiItem i)
	{
		try
        {
			Connection con = connect();
			i.uprank(con);
	        con.close();
	    }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}

	static public Mutex mutex = new Mutex();
	
	public static Node getNextUnvisitedItem(Node nog) 
	{
		Node n = new Node(new WikiItem());
		boolean found = false;
		for(Node child : nog.getChildren())
		{
			if(child.getItem().getVisited() == 0)
			{
				found = true;
				n = child;
				break;
			}
		}
		if(!found)
		{
			try
	        {
				Connection con = connect();
				mutex.acquire();
				n.getItem().getNextUnvisited(con);
				n.getItem().setVisited(con);
				mutex.release();
				con.close();
				
		    }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }	
		}
		return n;
	}
	


	public static ArrayList<ConnectedLinks> getConnections(WikiItem i1, WikiItem i5, int offset, int level, int max,
			Boolean restrict, Integer[] next) 
	{
		ArrayList<ConnectedLinks> conns = new ArrayList<ConnectedLinks>();
		ConnectedLinks parent = new ConnectedLinks();
		
		try
        {
			Connection con = connect();
			i1.get(con);
			if(i1.getNil())
				return null;
			i5.get(con);
			if(i5.getNil())
				return null;
			Perm.uprank(i1);
	    	System.out.println("uprank 1 " + i1.getItem());
	    	Perm.uprank(i5);
	    	System.out.println("uprank 5 " + i5.getItem());
			parent.setItem1(i1);
			parent.setItem5(i5);
			System.out.println("Level " + level);
			if(level == 1)
			{
				conns.addAll(parent.getConnectedLinksLevel1(con, max, offset, next));
				if(conns.size() < max && !restrict)
				{
					level = 2;
				}
			}
			if(level == 2)
			{
				conns.addAll(parent.getConnectedLinksLevel2(con, max - conns.size(), offset, next));
				if(conns.size() < max && !restrict)
				{
					level = 3;
				}
			}
			if(level == 3)
			{
				conns.addAll(parent.getConnectedLinksLevel3(con, max - conns.size(), offset, next));
				if(conns.size() < max && !restrict)
				{
					level = 4;
				}
			}
			if(level == 4)
			{
				conns.addAll(parent.getConnectedLinksLevel4(con, max - conns.size(), offset, next));
			}
			con.close();
	    }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return conns;
	}

	public static ArrayList<WikiItem> getItemSuggestions(String query) 
	{
		ArrayList<WikiItem> items = new ArrayList<WikiItem>();
		WikiItem item = new WikiItem(query);
		try
		{
			String[] queryItems = query.split(" ");
			Connection con = connect();
			item.get(con);
			if(item.getId() != null && item.getVisited() == 2)
			{
				items.add(item);
				items.addAll(WikiItem.getStartingWith(con, queryItems, query));
			}
			else
				items.addAll(WikiItem.getStartingWith(con, queryItems, null));
			
			con.close();	
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		return items;
	}

}
