package com.rojosewe.eic.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Item2item 
{
	public Item2item()
	{
		
	}
	
	
	
	public Item2item(Long id, Long item1, Long item2, Long paragraph_id) {
		super();
		this.id = id;
		this.item1 = item1;
		this.item2 = item2;
		this.paragraph_id = paragraph_id;
	}
	
	private Long id;
	private Long item1;
	private Long item2;
	private Long paragraph_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getItem1() {
		return item1;
	}
	public void setItem1(Long item1) {
		this.item1 = item1;
	}
	public Long getItem2() {
		return item2;
	}
	public void setItem2(Long item2) {
		this.item2 = item2;
	}
	public Long getParagraph_id() {
		return paragraph_id;
	}
	public void setParagraph_id(Long paragraph_id) {
		this.paragraph_id = paragraph_id;
	}
	public void insert(Connection con) 
	{
		PreparedStatement saveI2i = null;
		String query = "INSERT INTO item2item (item1, item2, paragraph_id) VALUES (?,?,?)";
		try 
		{
			saveI2i = con.prepareStatement(query);
			saveI2i.setLong(1, this.getItem1());
			saveI2i.setLong(2, this.getItem2());
			saveI2i.setLong(3, this.getParagraph_id());
			saveI2i.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(saveI2i != null)
			{
				try {
					saveI2i.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}



	public String toXml() {
		String xml = "<i2i>" +
					"<id>"+this.id+"</id>" +
					"<iitem1>"+this.item1+"</iitem1>" +
					"<iitem2>"+this.item2+"</iitem2>" +
					"<paragraph_id>"+this.paragraph_id+"</paragraph_id>" +
				"</i2i>";
		return xml;
	}
	
	
}
