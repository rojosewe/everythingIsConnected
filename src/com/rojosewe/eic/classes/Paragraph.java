package com.rojosewe.eic.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Paragraph 
{
	public Paragraph()	
	{
		
	}
	
	public Paragraph(Long id, String text) {
		super();
		this.id = id;
		Text = text;
	}

	private Long id;
	
	private String Text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public void insert(Connection con) 
	{
		PreparedStatement saveItem = null;
		String query = "INSERT INTO paragraph (text) VALUES (?)";
		try 
		{
			saveItem = con.prepareStatement(query);
			saveItem.setString(1, this.getText());
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void get(Connection con) 
	{
		PreparedStatement getItem = null;
		String query = "SELECT * FROM paragraph WHERE text = ?";
		try 
		{
			getItem = con.prepareStatement(query);
			getItem.setString(1, this.getText());
			ResultSet rs = getItem.executeQuery();
			if(rs.next())
			{
				this.setId(rs.getLong("id"));
				this.setText(rs.getString("text"));
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}



	public String toXml() {
		String xml = "<paragraph>" +
					"<id>"+this.id+"</id>" +
					"<text>"+this.getText()+"</text>" +
				"</paragraph>";
		return xml;
	}
	
	public String toXml(String contained, int size) {
		String ogtext = this.getText();
		int pos = ogtext.indexOf(contained);
		String text = "";
		int max = size;
		if(pos == -1 )
		{
			String replaced = contained.replace(" ", "_");
			pos = ogtext.indexOf(replaced);
			text = text.replace(replaced, contained);
		}
		max = Math.min(pos + (size/2), ogtext.length()-1);
		
		if(pos <= 400 || pos == -1)
			text = ogtext.substring(0,max);
		else
		{
			int half = size/2;
			text = ogtext.substring(Math.max(0, pos-half),Math.min(max, pos+half));
		}
		ogtext = text;
		ogtext = ogtext.replaceAll("[<>\"'\\&]*", "");
		String xml = "<paragraph>" +
					"<id>"+this.id+"</id>" +
					"<text>"+ogtext+"</text>" +
				"</paragraph>";
		return xml;
	}
}
