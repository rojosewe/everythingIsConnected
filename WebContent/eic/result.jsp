<%@page import="java.sql.Connection"%>
<%@page import="com.rojosewe.eic.fun.Perm"%>
<%@page import="com.rojosewe.eic.classes.WikiItem"%>
<%@page import="com.rojosewe.eic.fun.EIC"%>
<%@page import="com.rojosewe.eic.classes.ConnectedLinks"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String error = null; 
	String s1 = request.getParameter("s1");
	String s2 = request.getParameter("s2");
	if(s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty())
		response.sendRedirect("index.html");
	Integer offset = 0; 
	Integer level = 1;
	Integer max = 15;
	Boolean restrict = false;
	Integer[] next = new Integer[2];
	
	WikiItem i1 = new WikiItem(s1);
	WikiItem i2 = new WikiItem(s2);
	
	Connection con = Perm.connect();
	i1.get(con);
	i2.get(con);
	con.close();
	
	if(i1.getId() == null || i2.getId() == null)
	{
		String params = "dbpop?";
		boolean first = false;
		if(i1.getId() == null)
		{
			params += "q1=" + i1.getItem();
			first = true;
		}
		if(i2.getId() == null)
		{
			if(first)
				params += "&q2=" + i2.getItem();
			else
				params += "q1=" + i2.getItem();
		}
		response.sendRedirect(params);
		return;
	}
		
	
	try{
		offset = Integer.valueOf(request.getParameter("offset"));
	}
	catch(Exception e){offset = 0;
	}
	
	try{
		level = Integer.valueOf(request.getParameter("level"));
		if(level < 1)
			level = 1;
		else if(level > 4)
			level = 4;
	}
	catch(Exception e){level = 1;}
	
	try{
		max = Integer.valueOf(request.getParameter("max"));
		if(max < 1)
			max = 1;
		else if(max > 50)
			max = 50;
	}
	catch(Exception e){max = 10;}
	
	try{
		String aux = request.getParameter("restrict");
		if("on".equalsIgnoreCase(aux))
			aux = "true";
		restrict = Boolean.valueOf(aux);
	}
	catch(Exception e){restrict = false;}
	
	String jsonCls = "";
	if(s1 == null || s2 == null)
	{
		error = "Parametros Incorrectos";
	}
	else
	{
		jsonCls = EIC.search(s1, s2, offset, level, max, restrict, next);
		System.out.println("next " + level);
		if(jsonCls == null)
			error = "Parametros Incorrectos";	
	}
	
	String previous = "&max="+max+"&restrict="+restrict.toString();
	if(offset > max)
	{
		int aux = offset - max;
		previous += "&level="+level+"&offset="+aux; 
	}
	else if(offset <= max && offset > 0)
		previous += "&level="+level+"&offset=0";
	else
	{
		if(level > 1)
		{
			int aux = level - 1;
			System.out.println("prev "+aux);
			previous += "&level="+aux+"&offset=0";
		}
		else
			previous += "&level=1&offset=0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Everything Is Connected - The connections between <%=s1 %> and <%=s2 %></title>
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
<link rel="icon" type="image/png" href="images/li.png">
<style>
	body
	{
		text-align:center;
		margin: 20px;
		font-family: Arial, Helvetica, sans-serif;
		background-color:#ffffff;
	}
	
	#header
	{
		width: 100%;
		float: left;
	}
	
	#content
	{
		width: 100%;
		float: left;
	}
	
	#footer
	{
		width: 100%;
		float: left;
		text-align: center;
		color: #999;
	}
	
	#resultTitle
	{
		color: #4f96be;
		font-size: 18pt;
		margin-top: 20px;
	}
	
	#explain
	{
		font-size: 16pt;
	}
	
	path.link {
	  fill: none;
	  stroke: #9ecae1;
	  stroke-width: 1.5px;
	}
	
	marker#licensing {
	  fill: green;
	}
	
	path.link.licensing {
	  stroke: green;
	}
	
	path.link.resolved {
	}
	
	.circle {
	  fill: #FFF;
	  stroke: #fc534f;
	  stroke-width: 2px;
	  
	}
	
	.lowlite-circle {
	  fill: #c0c0c0;
	  stroke: #c0c0c0;
	  stroke-width: 1.5px;
	  
	}
	
	.origincircle
	{
		fill: #FFF;
		stroke: #fc534f;
	  	stroke-width: 1.5px;
	  	position: relative;
		z-index: 9999;
	}
	
	.endcircle
	{
		fill: #FFF;
		stroke: #fc534f;
	  	stroke-width: 1.5px;
	  	position: relative;
		z-index: 9999;
	}
	
	.inner-circle {
	  fill: #fc534f;
	  stroke: #fc534f;
	  stroke-width: 0;
	  
	}
	
	.inner-lowlite-circle {
	  fill: #c0c0c0;
	  stroke: #c0c0c0;
	  stroke-width: 1.5px;
	  
	}
	
	.inner-origincircle
	{
		fill: #fc534f;
		stroke: #fc534f;
	  	stroke-width: 0;
	  	position: relative;
		z-index: 9999;
	}
	
	.inner-endcircle
	{
		fill: #fc534f;
		stroke: #fc534f;
	  	stroke-width: 0;
	  	position: relative;
		z-index: 9999;
	}
	
	
	.text {
	  font: 10pt Arial, Helvetica, sans-serif;
	  pointer-events: none;
      fill       : #333;
      text-shadow: #BBB 0.1em 0.1em 0.2em
	}
	
	.marker
	{
	  fill: none;
	  stroke: #4f96be;
	  stroke-width: 1.5px;
	}
	
	#chart
	{
		float: left;
		width: 0px;
		overflow: visible;
	}
	
	#info-table
	{
		float: right;
		width: 0px;
		background-color: #f6f6f6;
		border-top: solid 1px #f0f0f0;
		border-right: solid 1px #e0e0e0;
		border-bottom: solid 1px #d0d0d0;
		border-left: solid 1px #e0e0e0;
		font-size: 10pt;
		word-wrap: break-word;
		overflow: hidden;
		display: none;
	}
	
	#info-table-area
	{
		padding: 10px;
		padding-left: 20px;
	}
	
	#info-table a
	{
		color: #4f96be;
		text-shadow: 1px 1px 0px #FFF;
		
	}
	
	#wiki-embed
	{
	 	float: right;
		width: 0;
		overflow: hidden;
	}
	
	#close-wiki
	{
		color: #4f96be;
		cursor: pointer;
		background-color: #f0f0f0;
		width: 100%;
		font-size: 14pt;
	}
	
	#close-info-table
	{
		color: #4f96be;
		cursor: pointer;
		background-color: #f0f0f0;
		width: 100%;
		text-align: center;
		font-size: 14pt;
	}
	 
	
	#wiki-embed-area
	{
		border-top: solid 1px #f0f0f0;
		border-right: solid 1px #e0e0e0;
		border-bottom: solid 1px #d0d0d0;
		border-left: solid 1px #e0e0e0;
		
	 }
	
	#paging
	{
		width: 90%;
		text-align: left;
		margin: 10px 5%;
		float: left;
	}
	
	.pager
	{
		float: left;
		margin: 0 10px;
		background-color: #f9f9f9;
		border-top: solid 1px #f0f0f0;
		border-right: solid 1px #e0e0e0;
		border-bottom: solid 1px #d0d0d0;
		border-left: solid 1px #e0e0e0;
		color: #333;
		font-weight: bold;
		padding: 5px 10px;
		text-decoration: none;
	}
	
	.info-link-page
	{
		font-size: 8pt;
		text-align: left;
	}
	
	 .info-link"
	 {
	 	text-align: justify;
	 }
	 
	 #filter-area
	 {
	 	width: 100%;
	 	text-align: center;
	 	float: left;
	 }
	 
	 #filter
	 {
	 	display: inline-block;
	 }
	 
	 #filter-frame
	 {
	 	padding: 20px;
	 	background-color: #F9F9F9;
		border-top: solid 1px #F0F0F0;
		border-right: solid 1px #E0E0E0;
		border-bottom: solid 1px #D0D0D0;
		border-left: solid 1px #E0E0E0;
	 	margin: auto;
	 	width: 800px;
	 }
	 
</style>
</head>
<body>
 	<div id="header">
 		<a href="index.html">
 			<img src="images/miniLogo.png" id="miniLogo"/><br/>
 		</a>
 		<div id="errors">
		<%
			if(error != null)
			{
		%>
			<%=error %>
		<%
			}
			else
			{
		%>		
		</div>
        <div id="resultTitle">
        	These are the connections between <%=s1 %> and <%=s2 %> 
    	</div>
    	<div id="explain">
    		Drag a node to reorganize or pass over it to see the full connection 
    	</div>
    	<div id="filter-area">
    		<div id="filter-frame">
		    	<div id="filter">
		    		<div>
			    		<form action="result.jsp" method="get">
			    			<div style="float: left;">
				    			<label>Start from level</label>
				    			<select name="level">
				    				<option value="1" <%=level==1?"selected":"" %>>Level 1</option>
				    				<option value="2" <%=level==2?"selected":"" %>>Level 2</option>
				    				<option value="3" <%=level==3?"selected":"" %>>Level 3</option>
				    				<option value="4" <%=level==4?"selected":"" %>>Level 4</option>
				    			</select> | 
			    			</div>
			    			<div style="float: left;">
				    			<label>Restrict to this level</label>
				    			<input name="restrict" type="checkbox" <%=restrict?"checked":"" %>> | 
				    		</div>	
			    			<div style="float: left;">
				    			<label>Max. number of results</label>
				    			<select name="max">
				    				<option value="1" <%=max==1?"selected":"" %>>1</option>
				    				<option value="5" <%=max==5?"selected":"" %>>5</option>
				    				<option value="10" <%=max==10?"selected":"" %>>10</option>
				    				<option value="15" <%=max==15?"selected":"" %>>15</option>
				    				<option value="20" <%=max==20?"selected":"" %>>20</option>
				    				<option value="25" <%=max==25?"selected":"" %>>25</option>
				    				<option value="30" <%=max==30?"selected":"" %>>30</option>
				    				<option value="40" <%=max==40?"selected":"" %>>40</option>
				    				<option value="50" <%=max==50?"selected":"" %>>50</option>
				    			</select>
				    		</div>	
			    			<input type="hidden" name="s1" value="<%=s1%>">
			    			<input type="hidden" name="s2" value="<%=s2%>">
			    			<div style="float: left;">
			    				<input type="submit" value="Filter">
			    			</div>	
			    		</form>
			    	</div>	
		    		<br/>
		    		<div>
			    		<span>
			    			<a href="result.jsp?&s1=<%=s1%>&s2=<%=s2%>">First Connection</a> | 
			    		</span>
			    		<span> 
			    			<a href="index.html">Search Other Terms</a> | 
			    		</span>
			    		<span> 
			    			<a href="newitem.html">Index a new term</a>
			    		</span>
			    	</div>
			    	<div style="margin: 10px 20px 5px 20px ">
			    		<span class='st_facebook_large' displayText='Facebook'></span>
						<span class='st_twitter_large' displayText='Tweet'></span>
						<span class='st_linkedin_large' displayText='LinkedIn'></span>
						<span class='st_email_large' displayText='Email'></span>
			    	</div>	
		    	</div>
		    </div>	
	    </div>	
    	<div id="paging">
	    	<%if(level > 1 || offset > 0){ %>
	    		<a href="result.jsp?s1=<%=s1%>&s2=<%=s2%><%=previous%>" class="pager">
	    			Previous Connections
	    		</a>
	    	<%} %>
	    	<a href="result.jsp?s1=<%=s1%>&s2=<%=s2%>&level=<%=next[0]%>&offset=<%=next[1]%>&max=<%=max %>&restrict=<%=restrict %>" class="pager">
	    			Next Connections
	    	</a>
	    </div>
    	<div style="text-align: center;margin-top: 10px" id="loading">
    		<img src="images/loader.gif" />
    		<br/>
    		Please wait while we connect the dots...
    	</div>
    	<%
			}
    	%>
    </div>
    <br/><br/>
    <div id="content">
    	<div id="chart">
		</div>
		<div id="wiki-embed">
			<div id="wiki-embed-area">
				<div id="close-wiki">Close</div>
				<div id="wiki-embed-frame"><img src='images/loader.gif' /></div>
			</div>	
		</div>
		<div id="info-table">
			<div id="close-info-table" >Close</div>
			<div id="info-table-area">
				<div id="node1" class="node">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-up.png">
				</div>
				<div class="info-link-page" id="info-link1-page"></div>
				<div id="info-link1" class="info-link">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-down.png">
				</div>
				<div id="node2" class="node">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-up.png">
				</div>
				<div class="info-link-page" id="info-link2-page"></div>
				<div id="info-link2" class="info-link">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-down.png">
				</div>
				<div id="node3" class="node">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-up.png">
				</div>
				<div class="info-link-page" id="info-link3-page"></div>
				<div id="info-link3" class="info-link">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-down.png">
				</div>
				<div id="node4" class="node">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-up.png">
				</div>
				<div class="info-link-page" id="info-link4-page"></div>
				<div id="info-link4" class="info-link">
				</div>
				<div class="linkIt">
					<img alt="" src="images/link-down.png">
				</div>
				<div id="node5" class="node">
				</div>
			</div>	
		</div>		
	</div>	
	<div id="footer">
		Information extracted from <a href="http://en.wikipedia.org/" target='_blank'>Wikipedia</a> and 
		Visualization made with <a href="http://d3js.org/" target='_blank'>d3.js</a>
	</div>
</body>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.13/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/d3.v2.min.js"></script>
<script type="text/javascript">
$("#info-table").hide();
$("#info-table").animate({width: 0}, 'fast', function(){});
var i5 = null;
var i1 = null;
var xml = "";
xml = "<%=jsonCls.replace("\"", "\\\"").replace("\\", "").trim()%>";
var ctx = null;
var nodeDict = new Array();
var linkDict = new Array();
var connArray = new Array();

function init()
{
	var item1 = $(xml).find("item1");
	i1 = new Object();
	i1.id = item1.find("id").text();
	i1.item = item1.find("item").text();
	nodeDict[i1.item] = i1;
	
	
	var item5 = $(xml).find("item5");
	i5 = new Object();
	i5.id = item5.find("id").text();
	i5.item = item5.find("item").text();
	nodeDict[i5.item] = i5;
}

function processEachNode()
{
	$(xml).find("connection").each(function()
	{
		var i2 = new Object();
		var p2 = new Object();
		var i3 = new Object();
		var p3 = new Object();
		var i4 = new Object();
		var p4 = new Object();
		
		var level = parseInt($(this).find("level").text());
		var p1 = new Object();
		p1.id = $(this).find("p1").find("id").text();
		p1.text = $(this).find("p1").find("text").text();
		p1.i1 = i1;
		if(level == 1)
		{
			connArray.push(level+"####");
			p1.i2 = i5;
		}
		if(level > 1)
		{
			var item2 = $(this).find("item2");
			i2.id = item2.find("id").text();
			i2.item = item2.find("item").text();
			
			p2.id = $(this).find("p2").find("id").text();
			p2.text = $(this).find("p2").find("text").text();
			p1.i2 = i2;
			p2.i1 = i2;
			if(level == 2)
			{
				p2.i2 = i5;
				connArray.push(level+"#"+i2.item+"###");
			}
			nodeDict[i2.item] = i2;
		}
		
		if(level > 2)
		{
			var item3 = $(this).find("item3");
			i3.id = item3.find("id").text();
			i3.item = item3.find("item").text();
			
			p3.id = $(this).find("p3").find("id").text();
			p3.text = $(this).find("p3").find("text").text();
			p2.i2 = i3;
			p3.i1 = i3;
			if(level == 3)
			{
				connArray.push(level+"#"+i2.item+"#"+i3.item+"##");
				p3.i2 = i5;
			}
			nodeDict[i3.item] = i3;
		}	
		
		if(level > 3)
		{
			var item4 = $(this).find("item4");
			i4.id = item4.find("id").text();
			i4.item = item4.find("item").text();
			
			p4.id = $(this).find("p4").find("id").text();
			p4.text = $(this).find("p4").find("text").text();
			p3.i2 = i4;
			p4.i1 = i4;
			p4.i2 = i5;
			connArray.push(level+"#"+i2.item+"#"+i3.item+"#"+i4.item+"#");
			nodeDict[i4.item] = i4;
		}
		linkDict[p1.i1.item+"-"+p1.i2.item] = p1;
		if(level > 1)
			linkDict[p2.i1.item+"-"+p2.i2.item] = p2;
		if(level > 2)
			linkDict[p3.i1.item+"-"+p3.i2.item] = p3;
		if(level > 3)
			linkDict[p4.i1.item+"-"+p4.i2.item] = p4;
	});
}
	
try
{
	init();
	processEachNode();
}
catch(e)
{
	
}


var links = [];
var wikis = []
for(key in linkDict)
{
	var link = linkDict[key];
	links.push({source:link.i1.item, target:link.i2.item, type: "resolved"});
}

/*for(key in linkDict)
{
	var link = linkDict[key];
	var contained = link.i2.item;
	var text = "";
	link.text = link.text.replace(contained, "<b>"+contained+"</b>");
}*/


var nodes = {};

// Compute the distinct nodes from the links.
links.forEach(function(link) {
  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
});

var w = $(window).width(),
    h = $(window).height();
    
    if(w > 800)
    	w = w - 200;  
    
var sep = $(document).width()/5;
sep = Math.min(sep,240);
sep = Math.max(sep,20);
	
var force = d3.layout.force()
    .nodes(d3.values(nodes))
    .links(links)
    .size([w, h])
    .linkDistance(sep)
    .charge(-200)
    .on("tick", tick)
    .start();

var svg = d3.select("#chart").append("svg:svg")
	.attr("id", "svg")
    .attr("width", w)
    .attr("height", h);

// Per-type markers, as they don't inherit styles.
svg.append("svg:defs").selectAll("marker")
    .data(["suit", "licensing", "resolved"])
  .enter().append("svg:marker").attr("class", "marker")
    .attr("id", String)
    .attr("viewBox", "0 -5 10 10")
    .attr("refX", 15)
    .attr("refY", -1.5)
    .attr("markerWidth", 5)
    .attr("markerHeight", 5)
    .attr("orient", "auto")
  .append("svg:path")
  .attr("d", "M0,-5L10,0L0,5");

var path = svg.append("svg:g").selectAll("path")
    .data(force.links())
  .enter().append("svg:path")
	  .attr("class", function(d) { return "link " + d.type; })
	  .attr("id", function(d){return "path-"+d.source.name.replace(/\s/g,'')+"-"+d.target.name.replace(/\s/g,'');})
    .attr("marker-end", function(d) { return "url(#" + d.type + ")"; });

var circle = svg.append("svg:g").selectAll("circle")
    .data(force.nodes())
  .enter().append("svg:circle")
    .attr("r", 
    function(d){ 
    	if(d.name==i1.item || d.name==i5.item)
   			return 16;
    	return 8; 
    })
    .on("mouseover", onmouseover)
	.on("mouseout", onmouseout)
    .attr("class", 
    function(d){ 
    	if(d.name==i1.item)
   			return "origincircle";
    	else if(d.name==i5.item)
    		return "endcircle";
    	return "circle"; 
    })
    .attr("id", function(d){return "circle-"+d.name.replace(/\s/g,'');})
    .call(force.drag);
    
var circle2 = svg.append("svg:g").selectAll("circle")
.data(force.nodes())
.enter().append("svg:circle")
.attr("r", 
function(d){ 
	if(d.name==i1.item || d.name==i5.item)
			return 14;
	return 6; 
})
.on("mouseover", onmouseover)
.on("mouseout", onmouseout)
.attr("class", 
function(d){ 
	if(d.name==i1.item)
			return "inner-origincircle";
	else if(d.name==i5.item)
		return "inner-endcircle";
	return "inner-circle"; 
})
.attr("id", function(d){return "inner-circle-"+d.name.replace(/\s/g,'');})
.call(force.drag);

var text = svg.append("svg:g").selectAll("g")
    .data(force.nodes())
  .enter().append("svg:g");

text.append("svg:text")
    .attr("x", 
    function(d){ 
    	if(d.name==i1.item || d.name==i5.item)
   			return 16;
    	return 8; 
    })
    .attr("y", ".31em")
    .attr("class", "text")
    .attr("id", function(d){return "text-"+d.name.replace(/\s/g,'');})
    .text(function(d) { return d.name; });

function tick() {
  path.attr("d", function(d) {
    var dx = d.target.x - d.source.x,
        dy = d.target.y - d.source.y,
        dr = Math.sqrt(dx * dx + dy * dy);
    return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
  });

  circle.attr("transform", function(d) {
    return "translate(" + d.x + "," + d.y + ")";
  });
  
  circle2.attr("transform", function(d) {
	    return "translate(" + d.x + "," + d.y + ")";
	  });

  text.attr("transform", function(d) {
    return "translate(" + d.x + "," + d.y + ")";
  });
}

function onmouseover(d, i) 
{
	$(".circle").css('fill', '#c0c0c0');
	$(".circle").css('stroke', '#c0c0c0');
	$(".inner-circle").css('fill', '#c0c0c0');
	$(".inner-circle").css('stroke', '#c0c0c0');
	$(".text").css('fill', '#c0c0c0');
	$(".link").css('stroke', '#c0c0c0');
	$(".marker").css('stroke', '#c0c0c0');
	var path = new Array();
	path[1] = i1;
	path[5] = i5;
	
	connArray = shuffle(connArray);
	
	if(d.name != i1.item && d.name != i5.item)
	{
		for(var j = 0; j < connArray.length; j++)
		{
			var value = connArray[j];
			if(value.indexOf("#"+d.name+"#") > -1)
			{
				var parr = value.split("#");
				path[0] = parseInt(parr[0]);
				path[2] = nodeDict[parr[1]];
				path[3] = nodeDict[parr[2]];
				path[4] = nodeDict[parr[3]];
				path[path[0]+1] = i5;
				break;
			}
		}
	}
	else
	{
		var p = connArray[0];
		var parr = p.split("#");
		path[0] = parseInt(parr[0]);
		path[2] = nodeDict[parr[1]];
		path[3] = nodeDict[parr[2]];
		path[4] = nodeDict[parr[3]];
		path[path[0]+1] = i5;
	}
	
	for(var i = 1; i < path[0]+2; i++)
	{
		$("#circle-"+path[i].item.replace(/\s/g,'')).css('fill', '#FFF');
		$("#circle-"+path[i].item.replace(/\s/g,'')).css('stroke', '#fc534f');
		$("#circle-"+path[i].item.replace(/\s/g,'')).css('stroke-width', '2px');
		$("#inner-circle-"+path[i].item.replace(/\s/g,'')).css('fill', '#fc534f');
		$("#inner-circle-"+path[i].item.replace(/\s/g,'')).css('stroke', '#FFF');
		$("#text-"+path[i].item.replace(/\s/g,'')).css('fill', '#333');
		$("#text-"+path[i].item.replace(/\s/g,'')).css('font-weight', 'bold');
		if(i <  path[0]+1)
		{
			$("#path-"+path[i].item.replace(/\s/g,'')+"-"+path[i+1].item.replace(/\s/g,'')).css('stroke', '#fc534f');
			$("#path-"+path[i].item.replace(/\s/g,'')+"-"+path[i+1].item.replace(/\s/g,'')).css('stroke-width', '3px');
			$("#path-"+path[i].item.replace(/\s/g,'')+"-"+path[i+1].item.replace(/\s/g,'')).css('position', 'relative');
			$("#path-"+path[i].item.replace(/\s/g,'')+"-"+path[i+1].item.replace(/\s/g,'')).css('z-index', '#9999');
		}
	}
	for(var i = 1; i < 5; i++)
	{
			$("#node"+i).html("");
			$("#info-link"+i+"-page").html("");
			$("#info-link"+i).html("");
	}
	for(var i = 1; i < path[0]+2; i++)
	{
		$("#node"+i).html("<a href='http://en.m.wikipedia.org/wiki/"+path[i].item.replace(/\s/g,'_')+"' target='_blank' class='node-link'><h3>"+path[i].item+"</h3></a>");
		wikis[i-1] = path[i].item.replace(/\s/g,'_');
		if(i < path[0]+1)
		{
			$("#info-link"+i+"-page").html("Ref found in the wikipedia page of " + path[i].item);
			var wikitext = linkDict[path[i].item+"-"+path[i+1].item].text;
			var patt=/\[\[[\sa-zA-Z0-9\\|!\\.,?()\-\']+\]\]/g;
			var hit=patt.exec(wikitext);
			while(hit != null){
			    hit = String(hit);
			    var aux = hit.replace("[[", "").replace("]]", "").split("|")[0];
			    var waux = aux.replace(/\s/g, "_");
			    wikitext = wikitext.replace(hit, "<a href='http://en.m.wikipedia.org/wiki/"+waux+"' target='_blank' class='node-link'>"+aux+"</a>");
				hit=patt.exec(wikitext);
			}
			wikitext = wikitext.replace(path[i+1].item, "<b>"+path[i+1].item+"</b>"); 
			$("#info-link"+i).html(wikitext);
		}
	}
	$("#info-table").show();
	$("#info-table").animate({width: 300}, 'fast', function(){});
	$(".node-link").click(function(event){
		event.preventDefault();
		openWikiEmbed($(this).attr('href'));
	});
}

//+ Jonas Raoni Soares Silva
//@ http://jsfromhell.com/array/shuffle [v1.0]

shuffle = function(o){ //v1.0
	for(var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
	return o;
};


function onmouseout(d, i) 
{
	$(".circle").css('fill', '#FFF');
	$(".circle").css('stroke', '#fc534f');
	$(".circle").css('stroke-width', '1.5px');
	$(".inner-circle").css('fill', '#fc534f');
	$(".inner-circle").css('stroke', '#fc534f');
	$(".text").css('fill', '#333');
	$(".link").css('stroke', '#9ecae1');
	$(".link").css('stroke-width', '1.5px');
	$(".marker").css('stroke', '#4f96be');
	$(".text").css('font-weight', 'normal');
	$(".origincircle").css('fill', '#FFF;');
	$(".origincircle").css('stroke', '#fc534f');
	$(".origincircle").css('stroke-width', '1.5px');
	$(".inner-origincircle").css('fill', '#fc534f');
	$(".inner-origincircle").css('stroke', '#fc534f');
	$(".inner-origincircle").css('stroke-width', '0');
	$(".endcircle").css('fill', '#FFF;');
	$(".endcircle").css('stroke', '#fc534f');
	$(".endcircle").css('stroke-width', '1.5px');
	$(".inner-endcircle").css('fill', '#fc534f');
	$(".inner-endcircle").css('stroke', '#fc534f');
	$(".inner-endcircle").css('stroke-width', '0');
	$(".text").css('font-weight', 'normal');
}

$("#close-wiki").click(function(){closeWikiEmbed()});
$("#close-info-table").click(function(){closeInfoTable()});

function closeInfoTable()
{
	$("#info-table").animate({width: 0}, 'fast', function(){$("#info-table").hide();});
}

function closeWikiEmbed()
{
	$("#wiki-embed").animate({width: 0}, 'fast',
			function(){
				$("#wiki-embed-frame").html("<img src='images/loader.gif' />");
			}
	);
}

function openWikiEmbed(linj)
{
	$("#wiki-embed").animate({width: 300}, 'fast', function(){});
	$("#wiki-embed-frame").html("<iframe src='"+linj+"' width='300' height='800' frameborder='0'></iframe>");
}


$("#loading").html("");
if(connArray.length == 0 && <%=level%> == 1 && <%=offset%> == 0)
{
	$("#paging").html("There are probably millions of connections between these two items but I didn't find any registered in the database.");
}
$(window).resize(function() {
	var w = $(window).width();
	var h = $(window).height();
	if(w > 800)
    	w = w - 200;  
  $('#svg').attr("width", w);
  $('#svg').attr("height", h);
});
</script>
<script type="text/javascript">var switchTo5x=true;</script>
<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
<script type="text/javascript">stLight.options({publisher: "71985567-14b7-44ac-90fd-dbb2d94354ab"}); </script>
</html>

