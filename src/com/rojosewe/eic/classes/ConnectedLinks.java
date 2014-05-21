package com.rojosewe.eic.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ConnectedLinks 
{
	int level;
	WikiItem item1;
	Item2item i2i1;
	Paragraph p1;
	WikiItem item2;
	Item2item i2i2;
	Paragraph p2;
	WikiItem item3;
	Item2item i2i3;
	Paragraph p3;
	WikiItem item4;
	Item2item i2i4;
	Paragraph p4;
	WikiItem item5;
	
	public ConnectedLinks() 
	{
		super();
	}
	
	public ConnectedLinks(WikiItem item1, Item2item i2i1, Paragraph p1,
			WikiItem item2, Item2item i2i2, Paragraph p2, WikiItem item3,
			Item2item i2i3, Paragraph p3, WikiItem item4, Item2item i2i4,
			Paragraph p4, WikiItem item5, int level) {
		super();
		this.item1 = item1;
		this.i2i1 = i2i1;
		this.p1 = p1;
		this.item2 = item2;
		this.i2i2 = i2i2;
		this.p2 = p2;
		this.item3 = item3;
		this.i2i3 = i2i3;
		this.p3 = p3;
		this.item4 = item4;
		this.i2i4 = i2i4;
		this.p4 = p4;
		this.item5 = item5;
		this.level = level;
	}
	
	public ConnectedLinks(WikiItem item1, Item2item i2i1, Paragraph p1,
			WikiItem item5, int level) {
		super();
		this.item1 = item1;
		this.i2i1 = i2i1;
		this.p1 = p1;
		this.item5 = item5;
		this.level = level;
	}

	public ConnectedLinks(WikiItem item1, Item2item i2i1, Paragraph p1,
			WikiItem item2, Item2item i2i2, Paragraph p2, WikiItem item5,
			int level) {
		super();
		this.item1 = item1;
		this.i2i1 = i2i1;
		this.p1 = p1;
		this.item2 = item2;
		this.i2i2 = i2i2;
		this.p2 = p2;
		this.item5 = item5;
		this.level = level;
	}
	
	public ConnectedLinks(WikiItem item1, Item2item i2i1, Paragraph p1,
			WikiItem item2, Item2item i2i2, Paragraph p2, WikiItem item3,
			Item2item i2i3, Paragraph p3, WikiItem item5,
			int level) {
		super();
		this.item1 = item1;
		this.i2i1 = i2i1;
		this.p1 = p1;
		this.item2 = item2;
		this.i2i2 = i2i2;
		this.p2 = p2;
		this.item3 = item3;
		this.i2i3 = i2i3;
		this.p3 = p3;
		this.item5 = item5;
		this.level = level;
	}

	public WikiItem getItem1() {
		return item1;
	}
	public void setItem1(WikiItem item1) {
		this.item1 = item1;
	}
	public Item2item getI2i1() {
		return i2i1;
	}
	public void setI2i1(Item2item i2i1) {
		this.i2i1 = i2i1;
	}
	public Paragraph getP1() {
		return p1;
	}
	public void setP1(Paragraph p1) {
		this.p1 = p1;
	}
	public WikiItem getItem2() {
		return item2;
	}
	public void setItem2(WikiItem item2) {
		this.item2 = item2;
	}
	public Item2item getI2i2() {
		return i2i2;
	}
	public void setI2i2(Item2item i2i2) {
		this.i2i2 = i2i2;
	}
	public Paragraph getP2() {
		return p2;
	}
	public void setP2(Paragraph p2) {
		this.p2 = p2;
	}
	public WikiItem getItem3() {
		return item3;
	}
	public void setItem3(WikiItem item3) {
		this.item3 = item3;
	}
	public Item2item getI2i3() {
		return i2i3;
	}
	public void setI2i3(Item2item i2i3) {
		this.i2i3 = i2i3;
	}
	public Paragraph getP3() {
		return p3;
	}
	public void setP3(Paragraph p3) {
		this.p3 = p3;
	}
	public WikiItem getItem4() {
		return item4;
	}
	public void setItem4(WikiItem item4) {
		this.item4 = item4;
	}
	public Item2item getI2i4() {
		return i2i4;
	}
	public void setI2i4(Item2item i2i4) {
		this.i2i4 = i2i4;
	}
	public Paragraph getP4() {
		return p4;
	}
	public void setP4(Paragraph p4) {
		this.p4 = p4;
	}
	public WikiItem getItem5() {
		return item5;
	}
	public void setItem5(WikiItem item5) {
		this.item5 = item5;
	}
	
	public String toXml(WikiItem i1, WikiItem i5)
	{
		String xml = "";
		ArrayList<String> list = new ArrayList<String>();
		list.add(i1.getItem());
		if(i2i2 != null)
			list.add(this.getItem2().getItem());
		if(i2i3 != null)
			list.add(this.getItem3().getItem());
		if(i2i4 != null)
			list.add(this.getItem4().getItem());
		list.add(i5.getItem());
		System.out.println(list);
		xml = "<connection><level>"+this.level+"</level>"+
				"<i2i1>"+this.i2i1.toXml()+"</i2i1>"+
				"<p1>"+this.p1.toXml(list.get(1), 800)+"</p1>";
				if(i2i2 != null)
				{
					
					xml += "<item2>"+this.item2.toXml()+"</item2>" + 
					"<i2i2>"+this.i2i2.toXml()+"</i2i2>"+
					"<p2>"+this.p2.toXml(list.get(2), 800)+"</p2>";
				}
				if(i2i3 != null)
				{
					xml += "<item3>"+this.item3.toXml()+"</item3>" + 
					"<i2i3>"+this.i2i3.toXml()+"</i2i3>"+
					"<p3>"+this.p3.toXml(list.get(3), 800)+"</p3>";
				}
				if(i2i4 != null)
				{
					xml += "<item4>"+this.item4.toXml()+"</item4>" +
					"<i2i4>"+this.i2i4.toXml()+"</i2i4>"+
					"<p4>"+this.p4.toXml(list.get(4), 800)+"</p4>";	
				}
			xml += "</connection>";
		return xml;
	}
	
	public ArrayList<ConnectedLinks> getConnectedLinksLevel1(Connection con, int max, int offset, Integer[] next) {
		ArrayList<ConnectedLinks> connections = new ArrayList<ConnectedLinks>();
		PreparedStatement getItem = null;
		next[0] = 1;
		String query = "SELECT * FROM item2item as i2i1 " +
			"LEFT JOIN paragraph as p1 ON p1.id = i2i1.paragraph_id " +
			"WHERE i2i1.item1 = ? " +
			"AND i2i1.item2 = ? " +
			"LIMIT ?, ?;";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setLong(1, this.item1.getId());
			getItem.setLong(2, this.item5.getId());
			getItem.setInt(3, offset);
			getItem.setInt(4, max);
			ResultSet rs = getItem.executeQuery();
			while(rs.next())
			{
				Item2item i2i1 = new Item2item(rs.getLong("i2i1.id"), rs.getLong("i2i1.item1"), rs.getLong("i2i1.item2"), rs.getLong("i2i1.paragraph_id"));
				Paragraph p1 = new Paragraph(rs.getLong("p1.id"), rs.getString("p1.text"));
				ConnectedLinks link = new ConnectedLinks(this.item1, i2i1, p1, this.item5, 1);
				link.item1 = null;
				link.item5 = null;
				connections.add(link);
				offset++;
			}
			next[1] = offset;
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
		return connections;
	}
	
	public ArrayList<ConnectedLinks> getConnectedLinksLevel2(Connection con, int max, int offset, Integer[] next) {
		ArrayList<ConnectedLinks> connections = new ArrayList<ConnectedLinks>();
		PreparedStatement getItem = null;
		next[0] = 2;
		String query = "SELECT * FROM item2item as i2i1 "+
					    "LEFT JOIN paragraph as p1 ON p1.id = i2i1.paragraph_id "+ 
						"LEFT JOIN item2item as i2i2 ON i2i1.item2 = i2i2.item1 "+
						"LEFT JOIN paragraph as p2 ON p2.id = i2i2.paragraph_id "+
						"LEFT JOIN item as item2 ON item2.id = i2i2.item1 "+
						"WHERE i2i1.item1 = ? "+ 
						"AND i2i2.item2 = ? "+  
						"AND i2i1.item2 != i2i1.item1 "+
						"AND i2i1.item2 != i2i2.item2 "+
						"AND item2.id != i2i1.item1 "+
						"AND item2.id != i2i2.item2 "+
						"LIMIT ?, ?;";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setLong(1, this.item1.getId());
			getItem.setLong(2, this.item5.getId());
			getItem.setInt(3, offset);
			getItem.setInt(4, max);
			ResultSet rs = getItem.executeQuery();
			while(rs.next())
			{
				Item2item i2i1 = new Item2item(rs.getLong("i2i1.id"), rs.getLong("i2i1.item1"), rs.getLong("i2i1.item2"), rs.getLong("i2i1.paragraph_id"));
				Paragraph p1 = new Paragraph(rs.getLong("p1.id"), rs.getString("p1.text"));
				WikiItem item2 = new WikiItem(rs.getLong("item2.id"), rs.getString("item2.item"), rs.getString("wiki_id"), rs.getString("wiki_notation"));
				Item2item i2i2 = new Item2item(rs.getLong("i2i2.id"), rs.getLong("i2i2.item1"), rs.getLong("i2i2.item2"), rs.getLong("i2i2.paragraph_id"));
				Paragraph p2 = new Paragraph(rs.getLong("p2.id"), rs.getString("p2.text"));
				ConnectedLinks link = new ConnectedLinks(this.item1, i2i1, p1, item2, i2i2, p2, this.item5, 2);
				link.item1 = null;
				link.item5 = null;
				connections.add(link);
				offset++;
			}
			next[1] = offset;
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
		return connections;
	}
	
	public ArrayList<ConnectedLinks> getConnectedLinksLevel3(Connection con, int max, int offset, Integer[] next) {
		ArrayList<ConnectedLinks> connections = new ArrayList<ConnectedLinks>();
		PreparedStatement getItem = null;
		next[0] = 3;
		String query = "SELECT * FROM item2item as i2i1 "+
					    "LEFT JOIN paragraph as p1 ON p1.id = i2i1.paragraph_id "+ 
						"LEFT JOIN item2item as i2i2 ON i2i1.item2 = i2i2.item1 "+
						"LEFT JOIN paragraph as p2 ON p2.id = i2i2.paragraph_id "+
						"LEFT JOIN item as item2 ON item2.id = i2i2.item1 "+
						"LEFT JOIN item2item as i2i3 ON i2i2.item2 = i2i3.item1 "+ 
						"LEFT JOIN paragraph as p3 ON p3.id = i2i3.paragraph_id "+
						"LEFT JOIN item as item3 ON item3.id = i2i3.item1 "+
						"WHERE i2i1.item1 = ? "+ 
						"AND i2i3.item2 = ? "+
						"AND item2.id != i2i1.item1 "+
						"AND item3.id != i2i1.item1 "+
						"AND item2.id != i2i3.item2 "+
						"AND item3.id != i2i3.item2 "+
						"LIMIT ?, ?;";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setLong(1, this.item1.getId());
			getItem.setLong(2, this.item5.getId());
			getItem.setInt(3, offset);
			getItem.setInt(4, max);
			ResultSet rs = getItem.executeQuery();
			while(rs.next())
			{
				Item2item i2i1 = new Item2item(rs.getLong("i2i1.id"), rs.getLong("i2i1.item1"), rs.getLong("i2i1.item2"), rs.getLong("i2i1.paragraph_id"));
				Paragraph p1 = new Paragraph(rs.getLong("p1.id"), rs.getString("p1.text"));
				WikiItem item2 = new WikiItem(rs.getLong("item2.id"), rs.getString("item2.item"), rs.getString("wiki_id"), rs.getString("wiki_notation"));
				Item2item i2i2 = new Item2item(rs.getLong("i2i2.id"), rs.getLong("i2i2.item1"), rs.getLong("i2i2.item2"), rs.getLong("i2i2.paragraph_id"));
				Paragraph p2 = new Paragraph(rs.getLong("p2.id"), rs.getString("p2.text"));
				WikiItem item3 = new WikiItem(rs.getLong("item3.id"), rs.getString("item3.item"), rs.getString("item3.wiki_id"), rs.getString("item3.wiki_notation"));
				Item2item i2i3 = new Item2item(rs.getLong("i2i3.id"), rs.getLong("i2i3.item1"), rs.getLong("i2i3.item2"), rs.getLong("i2i3.paragraph_id"));
				Paragraph p3 = new Paragraph(rs.getLong("p3.id"), rs.getString("p3.text"));
				ConnectedLinks link = new ConnectedLinks(this.item1, i2i1, p1, item2, i2i2, p2, item3, i2i3, p3, this.item5, 3);
				link.item1 = null;
				link.item5 = null;
				connections.add(link);
				offset++;
			}
			next[1] = offset;
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
		return connections;
	}
	
	public ArrayList<ConnectedLinks> getConnectedLinksLevel4(Connection con, int max, int offset, Integer[] next) {
		ArrayList<ConnectedLinks> connections = new ArrayList<ConnectedLinks>();
		PreparedStatement getItem = null;
		next[0] = 4;
		String query = "SELECT * FROM item2item as i2i1 "+
					    "LEFT JOIN paragraph as p1 ON p1.id = i2i1.paragraph_id "+ 
						"LEFT JOIN item2item as i2i2 ON i2i1.item2 = i2i2.item1 "+
						"LEFT JOIN paragraph as p2 ON p2.id = i2i2.paragraph_id "+
						"LEFT JOIN item as item2 ON item2.id = i2i2.item1 "+
						"LEFT JOIN item2item as i2i3 ON i2i2.item2 = i2i3.item1 "+ 
						"LEFT JOIN paragraph as p3 ON p3.id = i2i3.paragraph_id "+
						"LEFT JOIN item as item3 ON item3.id = i2i3.item1 "+
						"LEFT JOIN item2item as i2i4 ON i2i3.item2 = i2i4.item1 "+ 
						"LEFT JOIN paragraph as p4 ON p4.id = i2i4.paragraph_id "+
						"LEFT JOIN item as item4 ON item4.id = i2i4.item1 "+
						"WHERE i2i1.item1 = ? "+ 
						"AND i2i4.item2 = ? "+  
						"AND item2.id != i2i1.item1 "+
						"AND item3.id != i2i1.item1 "+
						"AND item4.id != i2i1.item1 "+
						"AND item2.id != i2i4.item2 "+
						"AND item3.id != i2i4.item2 "+
						"AND item4.id != i2i4.item2 "+
						"LIMIT ?, ?;";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setLong(1, this.item1.getId());
			getItem.setLong(2, this.item5.getId());
			getItem.setInt(3, offset);
			getItem.setInt(4, max);
			ResultSet rs = getItem.executeQuery();
			while(rs.next())
			{
				Item2item i2i1 = new Item2item(rs.getLong("i2i1.id"), rs.getLong("i2i1.item1"), rs.getLong("i2i1.item2"), rs.getLong("i2i1.paragraph_id"));
				Paragraph p1 = new Paragraph(rs.getLong("p1.id"), rs.getString("p1.text"));
				WikiItem item2 = new WikiItem(rs.getLong("item2.id"), rs.getString("item2.item"), rs.getString("wiki_id"), rs.getString("wiki_notation"));
				Item2item i2i2 = new Item2item(rs.getLong("i2i2.id"), rs.getLong("i2i2.item1"), rs.getLong("i2i2.item2"), rs.getLong("i2i2.paragraph_id"));
				Paragraph p2 = new Paragraph(rs.getLong("p2.id"), rs.getString("p2.text"));
				WikiItem item3 = new WikiItem(rs.getLong("item3.id"), rs.getString("item3.item"), rs.getString("item3.wiki_id"), rs.getString("item3.wiki_notation"));
				Item2item i2i3 = new Item2item(rs.getLong("i2i3.id"), rs.getLong("i2i3.item1"), rs.getLong("i2i3.item2"), rs.getLong("i2i3.paragraph_id"));
				Paragraph p3 = new Paragraph(rs.getLong("p3.id"), rs.getString("p3.text"));
				WikiItem item4 = new WikiItem(rs.getLong("item4.id"), rs.getString("item4.item"), rs.getString("item4.wiki_id"), rs.getString("item4.wiki_notation"));
				Item2item i2i4 = new Item2item(rs.getLong("i2i4.id"), rs.getLong("i2i4.item1"), rs.getLong("i2i4.item2"), rs.getLong("i2i4.paragraph_id"));
				Paragraph p4 = new Paragraph(rs.getLong("p4.id"), rs.getString("p4.text"));
				ConnectedLinks link = new ConnectedLinks(this.item1, i2i1, p1, item2, i2i2, p2, item3, i2i3, p3, item4, i2i4, p4, this.item5, 4);
				link.item1 = null;
				link.item5 = null;
				connections.add(link);
				offset++;
			}
			next[1] = offset;
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
		return connections;
	}
}
