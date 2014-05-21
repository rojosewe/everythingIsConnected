package com.rojosewe.eic.classes;

import java.sql.Connection;
import java.util.ArrayList;

public class Node
{
    private Node parent;
    private WikiItem item;
    private ArrayList<Node> children;
    private int visit;
    private int depth;

    public Node(WikiItem item)
    {
        parent = null;
        this.item = item;
        children = new ArrayList<Node>();
        visit = 0;
        depth = 0;
    }
    
    

    public Node getParent() {
		return parent;
	}



	public void setParent(Node parent) {
		this.parent = parent;
	}



	public WikiItem getItem() {
		return item;
	}



	public void setItem(WikiItem item) {
		this.item = item;
	}



	public ArrayList<Node> getChildren() {
		return children;
	}



	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}



	public int getVisit() {
		return visit;
	}



	public void setVisit(int visit) {
		this.visit = visit;
	}



	public int getDepth() {
		return depth;
	}



	public void setDepth(int depth) {
		this.depth = depth;
	}



	public void add(Node n)
    {
        if (!children.contains(n))
        {
            this.children.add(n);
            n.parent = this;
            n.visit = 0;
            n.depth = this.depth + 1;
        }
    }

    public void remove(Node n)
    {
        if (children.contains(n))
        {
            n.parent = null;
            this.children.remove(n);
            n.visit = 0;
        }
    }



	public void getUnvisited(Connection con) {
		// TODO Auto-generated method stub
		
	}
}