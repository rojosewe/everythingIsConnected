/* FUNCIONA FINO COMO GRAFO
	var i5 = null;
	var i1 = null;
	var xml = "<%= jsonCls %>";
	var ctx = null;
	var nodeDict = new Array();
	var linkDict = new Array();
	
	function init()
	{
		var item1 = $(xml).find("item1");
		i1 = new Object();
		i1.id = item1.find("id").text();
		i1.item = item1.find("item").text();
		nodeDict[i1.id] = i1;
		
		
		var item5 = $(xml).find("item5");
		i5 = new Object();
		i5.id = item5.find("id").text();
		i5.item = item5.find("item").text();
		nodeDict[i5.id] = i5;
	}
	
	function processEachNode()
	{
		$(xml).find("connection").each(function()
		{
			var item2 = $(this).find("item2");
			var i2 = new Object();
			i2.id = item2.find("id").text();
			i2.item = item2.find("item").text();
			
			var p1 = new Object();
			p1.id = $(this).find("p1").find("id").text();
			p1.text = $(this).find("p1").find("text").text();
			
			var item3 = $(this).find("item3");
			var i3 = new Object();
			i3.id = item3.find("id").text();
			i3.item = item3.find("item").text();
			
			var p2 = new Object();
			p2.id = $(this).find("p2").find("id").text();
			p2.text = $(this).find("p2").find("text").text();
			
			var item4 = $(this).find("item4");
			var i4 = new Object();
			i4.id = item4.find("id").text();
			i4.item = item4.find("item").text();
			
			var p3 = new Object();
			p3.id = $(this).find("p3").find("id").text();
			p3.text = $(this).find("p3").find("text").text();
			
			var p4 = new Object();
			p4.id = $(this).find("p4").find("id").text();
			p4.text = $(this).find("p4").find("text").text();
			
			p1.i1 = i1;
			p1.i2 = i2;
			p2.i1 = i2;
			p2.i2 = i3;
			p3.i1 = i3;
			p3.i2 = i4;
			p4.i1 = i4;
			p4.i2 = i5;
			
			nodeDict[i2.id] = i2;
			nodeDict[i3.id] = i3;
			nodeDict[i4.id] = i4;
			
			linkDict[p1.i1.id+"-"+p1.id+"-"+p1.i2.id] = p1;
			linkDict[p2.i1.id+"-"+p2.id+"-"+p2.i2.id] = p2;
			linkDict[p3.i1.id+"-"+p3.id+"-"+p3.i2.id] = p3;
			linkDict[p4.i1.id+"-"+p4.id+"-"+p4.i2.id] = p4;
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
	for(key in linkDict)
	{
		var link = linkDict[key];
		links.push({source:link.i1.id, target:link.i2.id, type: "resolved"});
		console.log("source: "+ link.source + ", target: "+link.target );
	}
	
	
	var nodes = {};

	// Compute the distinct nodes from the links.
	links.forEach(function(link) {
	  link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
	  link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
	});

	var w = 960,
	    h = 500;

	var force = d3.layout.force()
	    .nodes(d3.values(nodes))
	    .links(links)
	    .size([w, h])
	    .linkDistance(100)
	    .charge(-300)
	    .on("tick", tick)
	    .start();

	var svg = d3.select("body").append("svg:svg")
	    .attr("width", w)
	    .attr("height", h);

	// Per-type markers, as they don't inherit styles.
	svg.append("svg:defs").selectAll("marker")
	    .data(["suit", "licensing", "resolved"])
	  .enter().append("svg:marker")
	    .attr("id", String)
	    .attr("viewBox", "0 -5 10 10")
	    .attr("refX", 15)
	    .attr("refY", -1.5)
	    .attr("markerWidth", 4)
	    .attr("markerHeight", 4)
	    .attr("orient", "auto")
	  .append("svg:path")
	    .attr("d", "M0,-5L10,0L0,5");

	var path = svg.append("svg:g").selectAll("path")
	    .data(force.links())
	  .enter().append("svg:path")
	    .attr("class", function(d) { return "link " + d.type; })
	    .attr("marker-end", function(d) { return "url(#" + d.type + ")"; });

	var circle = svg.append("svg:g").selectAll("circle")
	    .data(force.nodes())
	  .enter().append("svg:circle")
	    .attr("r", 6)
	    .call(force.drag);

	var text = svg.append("svg:g").selectAll("g")
	    .data(force.nodes())
	  .enter().append("svg:g");

	text.append("svg:text")
	    .attr("x", 8)
	    .attr("y", ".31em")
	    .attr("class", "shadow")
	    .text(function(d) { return d.name; });

	text.append("svg:text")
	    .attr("x", 8)
	    .attr("y", ".31em")
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

	  text.attr("transform", function(d) {
	    return "translate(" + d.x + "," + d.y + ")";
	  });
	}*/