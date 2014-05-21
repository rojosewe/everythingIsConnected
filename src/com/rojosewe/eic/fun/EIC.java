package com.rojosewe.eic.fun;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rojosewe.eic.classes.ConnectedLinks;
import com.rojosewe.eic.classes.Node;
import com.rojosewe.eic.classes.Paragraph;
import com.rojosewe.eic.classes.WikiItem;
import com.rojosewe.eic.fun.BFS;
import com.rojosewe.eic.fun.Perm;

public class EIC 
{
	public Node node;
    public ArrayList<Node> queue;
	
	public static void start(String root)
    {   
		BFS bfs = new BFS(root);
		bfs.start();
    }
	
	public static ArrayList<Node> startSearch(String[] ss)
    {
		ArrayList<Node> nodes = new ArrayList<Node>();
        for (String s : ss)
        {
            WikiItem w = new WikiItem(s);
            Node n = new Node(w);
            BFS.search(n);
            nodes.add(n);
        }
        return nodes;
    }
	
	public void getLinks(Node node)
    {
        try
        {
            String content = GetWikipediaContents(node.getItem());

            try
            {
            	int pageIdStart = content.indexOf("{\"pageid\":");
            	int pageIdEnd = content.substring(pageIdStart).indexOf(",\"");
            	String pageId = content.substring(pageIdStart + new String("{\"pageid\":").length(), pageIdStart+pageIdEnd);
				node.getItem().setWiki_id(pageId);
            }
            catch(Exception ex)
            {
            	
            }
            
            getDenseLinks(node, content);
            Perm.saveNode(node);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	
	public static String GetWikipediaContents(WikiItem item)
    {
        String itemsname = item.getItem();
        itemsname = itemsname.replace(" ", "_");
        
        URL url;
        String line;
        String content = "";
        try {
        	url = new URL("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&redirects=&titles=" + itemsname + "&rvprop=content&format=json");
        	java.net.URLConnection c = url.openConnection();
        	c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = rd.readLine()) != null) {
                content += line;
            }
        } catch (Exception mue) {
             mue.printStackTrace();
        }
        return content;
    }
	
	public static Boolean GetWikipediaExists(WikiItem item)
    {
        String itemsname = item.getItem();
        itemsname = itemsname.replace(" ", "_");
        String line;
        String content = "";
        URL url;
        try {
        	url = new URL("http://en.wikipedia.org/w/api.php?action=query&prop=revisions&redirects=&titles=" + itemsname + "&rvprop=content&format=json");
        	java.net.URLConnection c = url.openConnection();
        	c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = rd.readLine()) != null) {
                content += line;
            }
            if(content.contains("pages\":{\"-1"))
            	return false;
            else
            	return true;
        } catch (Exception mue) {
             mue.printStackTrace();
             return false;
        }
    }

    public static void getDenseLinks(Node node, String content)
    {
    	ArrayList<String> gotem = new ArrayList<String>();
        String[] paragraphs = content.split("\\\\n\\\\n");
        for (String paragraph_text : paragraphs)
        {
        	Paragraph paragraph = new Paragraph();
            Boolean first = true;
            Pattern pattern = Pattern.compile("\\[\\[[A-Za-z0-9 \\|]+\\]\\]");
            Matcher matcher = pattern.matcher(paragraph_text);
            while (matcher.find())
            {
                String notation = matcher.group();
                int breaker = notation.indexOf("|");
                if (breaker < 0)
                    breaker = notation.indexOf("]]");
                String name = notation.substring(2, breaker);
                if (!gotem.contains(name.trim()))
                {
                    gotem.add(name.trim());
                    if (first)
                    {
                        paragraph = Perm.saveParagraph(paragraph_text);
                        first = false;
                    }
                    WikiItem item = new WikiItem(name);
                    item.setWiki_notation(notation);
                    Node n = new Node(item);
                    n.getItem().setParagraph_id(paragraph.getId());
                    node.add(n);
                }
            }
        }
        gotem.clear();
    }
    
    public static String search(String s1, String s2, int offset, int level, int max, Boolean restrict, Integer[] next)
    {
    	WikiItem i1 = new WikiItem(s1);
		WikiItem i5 = new WikiItem(s2);
    	ArrayList<ConnectedLinks> conns = Perm.getConnections(i1, i5, offset, level, max, restrict, next);
    	String xml = "<connections>";
    	xml += "<item1>"+i1.toXml()+"</item1>";
    	xml += "<item5>"+i5.toXml()+"</item5>";
    	if(conns != null)
    	{
    		for (ConnectedLinks cl : conns) 
        	{
        		xml += cl.toXml(i1, i5);
    		}	
    	}
    	xml += "</connections>";
    	xml = xml.replace("\"", "'");
    	return xml;
    }
}
