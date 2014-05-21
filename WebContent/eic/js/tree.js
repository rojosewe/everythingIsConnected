var i5 = null;
var i1 = null;
var xml = "<%= jsonCls %>";
var ctx = null;
var nodeDict = new Array();
var linkDict = new Array();

try
{
	init();
	processEachNode();
	/*document.writeln("<div style='width=100%;text-align:left;'>");
	printTree(i1);
    document.writeln("</div>");*/
	drawTree(i1);
}
catch(e)
{
	console.log(e.message);
}

function printTree(node) {
    if (node.children.length > 0) {
        document.writeln(" { name = " + node.name + ",");
        document.write("   childen (" + node.children.length + ") = [<br>    ");
        node.children.forEach(function (c) {
            printTree(c);
            document.write(",");                
        });
        document.write("] } <br>");            
    } else {
        document.writeln("{ name = " + node.name + "}");
    }
}
/*
function gameover(d, i) {
    svg.selectAll("g.futuregame").select("circle")
        .transition().duration(100)
        .attr("r",5)   // this is in case you roll from one team to next w/o activating teamout
        .style("fill","#ccc").style("stroke","#ccc");

    d3.select(this)
    .transition().duration(100)
    .attr("r",10)
    .style("stroke", "black").style("stroke-width", "2px");   

    d.teams.forEach(function (t) {
        if (t.stillin==1) {
            var nameText = svg.selectAll("g."+t.name.toLowerCase().replace(/\W/g,"_"))
                .selectAll("text")
                .transition().duration(100).attr("fill", "#000");
            svg.selectAll("g."+t.name.toLowerCase().replace(/\W/g,"_"))
                .append("text")
                .attr("class", "prob")
                .text((Math.round(t.probs[d.name]*1000)/10)+"%")
                .attr("x", function(d) { return d.x < 180 ? 10 : -10; })
                .attr("y",10)
                .attr("text-anchor", function(z) { return z.x < 180 ? "end" : "start"; })
                .attr("transform", function(z) { return z.x < 180 ? null : "rotate(180)"; })                
                .attr("fill","#11f")
                .attr("font-size","0.9em");
        }
    });       
}

function gameout(d, i) {
    svg.selectAll("g.futuregame").select("circle")
        .transition().duration(100)
        .attr("r",5)
        .style("fill","#99c").style("stroke","#99c");

    d3.select(this)
    .transition().duration(100)
    .attr("r",5)
    .style("stroke", "#99c").style("stroke-width", "2px");

    d.teams.forEach(function (t) {
        if (t.stillin ==1) {
            svg.selectAll("g."+t.name.toLowerCase().replace(/\W/g,"_"))
                    .selectAll("text")
                    .transition().duration(100).attr("fill", "#aaa");
            svg.selectAll("g."+t.name.toLowerCase().replace(/\W/g,"_"))
                    .select("text.prob").remove();
        }
    });       
}
*/
function init()
{
 	var item1 = $(xml).find("item1");
 	i1 = new Object();
 	i1.id = item1.find("id").text();
 	i1.item = item1.find("item").text();
 	i1.name = item1.find("item").text();
 	i1.children = new Array();
 	nodeDict[i1.id] = i1;
 	
 	
 	var item5 = $(xml).find("item5");
 	i5 = new Object();
 	i5.id = item5.find("id").text();
 	i5.item = item5.find("item").text();
 	i5.name = item5.find("item").text();
 	i5.children = new Array();
 	nodeDict[i5.id] = i5;
 }

 function processEachNode()
 {
 	$(xml).find("connection").each(function()
 	{
 		var item2 = $(this).find("item2");
 		var i2 = new Object();
 		i2.id = item2.find("id").text();
 		if(nodeDict[i2.id] == null)
 		{
 			i2.item = item2.find("item").text();
 			i2.name = item2.find("item").text();
 			i2.children = new Array();
 			nodeDict[i2.id] = i2;
 		}
 		else
 		{
 			i2 = nodeDict[i2.id];
 		}
 		if(!areParentAndSon(i1,i2))
 			i1.children.push(i2);
 		
 		
 		var p1 = new Object();
 		p1.id = $(this).find("p1").find("id").text();
 		p1.text = $(this).find("p1").find("text").text();
 		
 		var item3 = $(this).find("item3");
 		var i3 = new Object();
 		i3.id = item3.find("id").text();
 		if(nodeDict[i3.id] == null)
 		{
 			i3.item = item3.find("item").text();
 			i3.name = item3.find("item").text();
 			i3.children = new Array();
 			nodeDict[i3.id] = i3;
 		}
 		else
 		{
 			i3 = nodeDict[i3.id];
 		}
 		if(!areParentAndSon(i2,i3))
 			i2.children.push(i3);
 		
 		var p2 = new Object();
 		p2.id = $(this).find("p2").find("id").text();
 		p2.text = $(this).find("p2").find("text").text();
 		
 		var item4 = $(this).find("item4");
 		var i4 = new Object();
 		i4.id = item4.find("id").text();
 		if(nodeDict[i4.id] == null)
 		{
 			i4.item = item3.find("item").text();
 			i4.name = item3.find("item").text();
 			i4.children = new Array();
 			nodeDict[i4.id] = i4;
 		}
 		else
 		{
 			i4 = nodeDict[i4.id];
 		}
 		if(!areParentAndSon(i3,i4))
 			i3.children.push(i4);
 		
 		var p3 = new Object();
 		p3.id = $(this).find("p3").find("id").text();
 		p3.text = $(this).find("p3").find("text").text();
 		
 		var p4 = new Object();
 		p4.id = $(this).find("p4").find("id").text();
 		p4.text = $(this).find("p4").find("text").text();
 		if(!areParentAndSon(i4,i5))
 			i4.children.push(i5);
 		
 		
 		p1.i1 = i1;
 		p1.i2 = i2;
 		p2.i1 = i2;
 		p2.i2 = i3;
 		p3.i1 = i3;
 		p3.i2 = i4;
 		p4.i1 = i4;
 		p4.i2 = i5;
 		
 		if(!linkDict[p1.i1.id+"-"+p1.i2.id])
 			linkDict[p1.i1.id+"-"+p1.i2.id] = p1;
 		
 		if(!linkDict[p2.i1.id+"-"+p2.i2.id])
 			linkDict[p2.i1.id+"-"+p2.i2.id] = p2;
 		
 		if(!linkDict[p3.i1.id+"-"+p3.i2.id])
 			linkDict[p3.i1.id+"-"+p3.i2.id] = p3;
 		
 		if(!linkDict[p4.i1.id+"-"+p4.i2.id])
 			linkDict[p4.i1.id+"-"+p4.i2.id] = p4;
 	});
 }
 
 function areParentAndSon(parent, son)
 {
	 for(var i = 0; i < parent.children.length; i++) {
         if(parent.children[i].id === son.id) {
             return true;
         }
     }
     return false;
 }
 
 function drawTree(json)
 {
	 var radius = 600 / 2;
	 
	 var tree = d3.layout.tree()
	     .size([360, 310])
	     .separation(function(a, b) { return (a.parent == b.parent ? 1 : 2) / a.depth; });
	 
	 
	 var diagonal = d3.svg.diagonal.radial()
	     .projection(function(d) { return [d.y, d.x / 180 * Math.PI]; });
	 
	 var vis = d3.select("#chart").append("svg")
	     .attr("width", 1000)
	     .attr("height", 1000)
	     .append("g")
	     .attr("transform", "translate(" + (radius+150) + "," + (radius+180) + ")");
	 
	   var nodes = tree.nodes(json);
	 
	   var link = vis.selectAll("path.link")
	       .data(tree.links(nodes))
	     .enter().append("path")
	       .attr("class", "link")
	       .attr("d", diagonal);
	 
	   var node = vis.selectAll("g.node")
	     .data(nodes)
	     .enter().append("g")
	     .attr("class", "node")
	     .attr("transform", function(d) { return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")"; });
	       
	   var outerNodes = node.filter(function (d) {
	       return ((d.name=="cluster"));       
	   });
	   
	   


	   //outerNodes.on("mouseover", gameover).on("mouseout", gameout);
	   
	   node.append("circle")
	       .attr("r", 8);
	   
	   outerNodes.append("circle").attr("r",3)
	   .style("stroke", "#ccc").style("stroke-width", "5px")
	   .style("fill", "#fff");
	 
	   node.append("text")
	       .attr("dx", function(d) { return d.x < 180 ? 14 : -14; })
	       .attr("dy", ".31em")
	       .attr("class", "text")
	       .attr("text-anchor", function(d) { return d.x < 180 ? "start" : "end"; })
	       .attr("transform", function(d) { return d.x < 180 ? null : "rotate(180)"; })
	       .text(function(d) { return d.name; });
	 $("#loading").html("");	 
 }

