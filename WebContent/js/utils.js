function page(){
		
		var a = 'Page';
	
    	console.log(a);
		startStormPage();
		
	}

function user(){
	
	var a = 'User';

	console.log(a);
	startStormUser();
	
}
function toggle() {
    document.getElementById('loading').style.display='block';
    document.getElementById('ricerca').style.display='none';
    setTimeout(function (){
        document.getElementById('loading').style.display='none';
    },20000);
    setTimeout(function (){
        document.getElementById('results').style.display='block';
    },20000);
    attendi();
}

function attendi(){
	setTimeout(function(){
	var diameter = 960,
	    format = d3.format(",d"),
	    color = d3.scale.category20c();

	var bubble = d3.layout.pack()
	    .sort(null)
	    .size([diameter, diameter])
	    .padding(1.5);

	var svg = d3.select("body").append("svg")
	    .attr("width", diameter)
	    .attr("height", diameter)
	    .attr("class", "bubble")
	    .attr("style","margin-top: -150; margin-left: 150");

	d3.json("flare.json", function(error, root) {
	  var node = svg.selectAll(".node")
	      .data(bubble.nodes(classes(root))
	      .filter(function(d) { return !d.children; }))
	    .enter().append("g")
	      .attr("class", "node")
	      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });

	  node.append("title")
	      .text(function(d) { 
	    	  if(d.value===1){
	    	  return d.className + ": " + format(d.value)+" edit"; 
	    	  }
	    	  else
	    	  {
	    		  return d.className + ": " + format(d.value)+" edits";   
	    	  }
	    	  });
	  		
	  node.append("circle")
	      .attr("r", function(d) { return d.r; })
	      .style("fill", function(d) { return color(d.packageName); });

	  node.append("text")
	      .attr("dy", ".3em")
	      .style("text-anchor", "middle")
	      .text(function(d) { return d.className.substring(0, d.r / 3); });
	});

	// Returns a flattened hierarchy containing all leaf nodes under the root.
	function classes(root) {
	  var classes = [];

	  function recurse(name, node) {
	    if (node.children) node.children.forEach(function(child) { recurse(node.name, child); });
	    else classes.push({packageName: name, className: node.name, value: node.size});
	  }

	  recurse(null, root);
	  return {children: classes};
	}

	d3.select(self.frameElement).style("height", diameter + "px");
	},20000);

	}