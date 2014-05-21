package com.rojosewe.eic.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class WikiItem 
{
	public WikiItem()
	{ 
		this.nil = false;
	}
	
	public WikiItem(Long id, String item, String wiki_id, String wiki_notation) {
		super();
		this.id = id;
		this.item = item;
		this.wiki_id = wiki_id;
		this.wiki_notation = wiki_notation;
		this.nil = false;
	}



	private Long id;
    private String item;
    private String wiki_id;
    private String wiki_notation;
    private Long paragraph_id;
    private int visited;
    private Boolean nil;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getWiki_id() {
		return wiki_id;
	}

	public void setWiki_id(String wiki_id) {
		this.wiki_id = wiki_id;
	}

	public String getWiki_notation() {
		return wiki_notation;
	}

	public void setWiki_notation(String wiki_notation) {
		this.wiki_notation = wiki_notation;
	}

	public Long getParagraph_id() {
		return paragraph_id;
	}

	public void setParagraph_id(Long paragraph_id) {
		this.paragraph_id = paragraph_id;
	}

	public WikiItem(String item)
    {
        this.item = item;
    }

	public void setVisited(int visited) {
		this.visited = visited;
	}

	public int getVisited() {
		return visited;
	}

	public void insert(Connection con) 
	{
		PreparedStatement saveItem = null;
		String query = "INSERT INTO item (item, visited, wiki_notation, wiki_id) VALUES (?,?,?,?)" +
				"ON DUPLICATE KEY UPDATE wiki_notation=?, wiki_id=?";
		if(this.getVisited() == 2)
			query += " ,visited = 2";
		try 
		{
			saveItem = con.prepareStatement(query);
			saveItem.setString(1, this.getItem());
			saveItem.setInt(2, this.getVisited());
			saveItem.setString(3, this.getWiki_notation());
			saveItem.setString(4, this.getWiki_id());
			saveItem.setString(5, this.getWiki_notation());
			saveItem.setString(6, this.getWiki_id());
			saveItem.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(saveItem != null)
			{
				try {
					saveItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void get(Connection con) 
	{
		PreparedStatement getItem = null;
		String query = "SELECT * FROM item WHERE item = ?";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setString(1, this.getItem());
			ResultSet rs = getItem.executeQuery();
			if(rs.next())
			{
				this.setId(rs.getLong("id"));
				this.setItem(rs.getString("item"));
				this.nil = false;
				this.setVisited(rs.getInt("visited"));
				this.setWiki_notation(rs.getString("wiki_notation"));
				this.setWiki_id(rs.getString("wiki_id"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(getItem != null)
			{
				try {
					getItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static ArrayList<WikiItem> getStartingWith(Connection con, String[] queryItems, String exclude) 
	{
		ArrayList<WikiItem> result = new ArrayList<WikiItem>();
		PreparedStatement getItem = null;
		String query = "select item from item WHERE ";
		for(int i = 0; i < queryItems.length; i++)
		{
			query += "item LIKE ? AND ";	
		}
	    query += "visited = 2  ORDER BY rank LIMIT 5";
		try 
		{
			getItem = con.prepareStatement(query);
			for(int i = 0; i < queryItems.length; i++)
			{
				getItem.setString(i+1, "%"+queryItems[i]+"%");	
			}
			System.out.println(getItem.toString());
			ResultSet rs = getItem.executeQuery();
			while(rs.next())
			{
				String itemname = rs.getString("item");
				if(!itemname.equalsIgnoreCase(exclude))
				{
					WikiItem item = new WikiItem(itemname);
					result.add(item);
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(getItem != null)
			{
				try {
					getItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static ArrayList<WikiItem> getRandom(Connection con, Integer count) 
	{
		ArrayList<WikiItem> result = new ArrayList<WikiItem>();
		if(count == 0)
			return result;
		PreparedStatement getItem = null;
		String query = "SELECT * FROM item LIMIT ?";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setInt(1, count);
			ResultSet rs = getItem.executeQuery();
			if(rs.next())
			{
				WikiItem item = new WikiItem();
				item.setId(rs.getLong("id"));
				item.setItem(rs.getString("item"));
				item.nil = false;
				item.setVisited(rs.getInt("visited"));
				item.setWiki_notation(rs.getString("wiki_notation"));
				item.setWiki_id(rs.getString("wiki_id"));
				result.add(item);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(getItem != null)
			{
				try {
					getItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public void getById(Connection con) 
	{
		PreparedStatement getItem = null;
		String query = "SELECT * FROM item WHERE id = ?";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setLong(1, this.getId());
			ResultSet rs = getItem.executeQuery();
			if(rs.next())
			{
				this.setId(rs.getLong("id"));
				this.setItem(rs.getString("item"));
				this.setVisited(rs.getInt("visited"));
				this.setWiki_notation(rs.getString("wiki_notation"));
				this.setWiki_id(rs.getString("wiki_id"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
		
			if(getItem != null)
			{
				try {
					getItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void getNextUnvisited(Connection con) {
		PreparedStatement getItem = null;
		String query = "SELECT * FROM item WHERE visited = 0 LIMIT 1 ";
		try 
		{
			getItem = con.prepareStatement(query);
			ResultSet rs = getItem.executeQuery();
			if(rs.next())
			{
				this.setId(rs.getLong("id"));
				this.setItem(rs.getString("item"));
				this.setVisited(rs.getInt("visited"));
				this.setWiki_notation(rs.getString("wiki_notation"));
				this.setWiki_id(rs.getString("wiki_id"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(getItem != null)
			{
				try {
					getItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void setVisited(Connection con) 
	{
		PreparedStatement saveItem = null;
		String query = "UPDATE item SET visited=? WHERE id=?";
		try 
		{
			saveItem = con.prepareStatement(query);
			saveItem.setInt(1, 2);
			saveItem.setLong(2, this.getId());
			saveItem.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(saveItem != null)
			{
				try {
					saveItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void uprank(Connection con) 
	{
		PreparedStatement updateItem = null;
		String query = "UPDATE item SET rank = rank + 1 WHERE id=?";
		try 
		{
			updateItem = con.prepareStatement(query);
			updateItem.setLong(1, this.getId());
			updateItem.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(updateItem != null)
			{
				try {
					updateItem.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void setNil(Boolean nil) {
		this.nil = nil;
	}

	public Boolean getNil() {
		return nil;
	}

	public String toXml() {
		String xml = "<wikiitem>" +
						"<id>"+this.id+"</id>" +
						"<item>"+this.item+"</item>" +
						"<wiki_id>"+this.wiki_id+"</wiki_id>" +
						"<wiki_notation>"+this.wiki_notation+"</wiki_notation>" +
					"</wikiitem>";
		return xml;
	}
}
