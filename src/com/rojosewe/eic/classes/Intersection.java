package com.rojosewe.eic.classes;

import java.util.ArrayList;

public class Intersection
{
    public long connection_id;
    public ArrayList<Node> path;

    public Intersection()
    {
        path = new ArrayList<Node>();
    }

	public long getConnection_id() {
		return connection_id;
	}

	public void setConnection_id(long connection_id) {
		this.connection_id = connection_id;
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}
    
    
}
