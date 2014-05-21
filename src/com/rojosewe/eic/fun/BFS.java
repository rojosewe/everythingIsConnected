package com.rojosewe.eic.fun;

import com.rojosewe.eic.classes.Node;
import com.rojosewe.eic.classes.WikiItem;
import com.rojosewe.eic.fun.BFS;
import com.rojosewe.eic.fun.EIC;
import com.rojosewe.eic.fun.Perm;

public class BFS extends Thread{

	public static int BFSCount = 0;
	public static void searchPop(Node nog) 
	{
		Node n = nog;
		EIC eic = new EIC();
		eic.getLinks(nog);
		int size = 0;
		for(Node child : nog.getChildren())
		{
			if(child.getItem().getVisited() == 0)
				size++; 
		}
			
		for(int i = 0; i < size; i++)
		{
			System.out.println("Viejo " + n.getItem().getItem() + ", Nivel: " + n.getItem().getVisited());
	        n = Perm.getNextUnvisitedItem(nog);
	        System.out.println("Nuevo " + n.getItem().getItem());
	        eic.getLinks(n);
		}
	}

	public static void search(Node n) {
		
	}
	
	
	public String root;
	public BFS(String root)
	{
		this.root = root;
	}
	
	public void run() 
	{
		WikiItem w = new WikiItem(root);
		Node n = new Node(w);
		BFS.searchPop(n);
    }

}
