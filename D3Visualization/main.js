var treeData = [
{"parent":"null","children":[{"parent":"##Table1","children":[{"parent":"SELECT1","children":[{"parent":"##Table3","children":[{"parent":"null","children":[],"name":"##Table3"}],"name":"SELECT1"},{"parent":"##Table4","children":[{"parent":"SELECT3","children":[{"parent":"##Table5","children":[{"parent":"null","children":[],"name":"##Table5"}],"name":"SELECT3"},{"parent":"##Table6","children":[{"parent":"null","children":[],"name":"##Table6"}],"name":"SELECT4"},{"parent":"##Table7","children":[{"parent":"null","children":[],"name":"##Table7"}],"name":"SELECT5"},{"parent":"##Table8","children":[{"parent":"null","children":[],"name":"##Table8"}],"name":"SELECT6"}],"name":"##Table4"}],"name":"SELECT2"},{"parent":"##Table2","children":[{"parent":"null","children":[],"name":"##Table2"}],"name":"SELECT7"},{"parent":"##Table3","children":[{"parent":"null","children":[],"name":"##Table3"}],"name":"SELECT8"}],"name":"##Table1"}],"name":"SELECT0"}],"name":"Server1.dbo.SererTable1"}
];

// ************** Generate the tree diagram	 *****************
var margin = {top: 40, right: 120, bottom: 20, left: 120},
	width = 960 - margin.right - margin.left,
	height = 500 - margin.top - margin.bottom;

var i = 0;

var tree = d3.layout.tree()
	.size([height, width]);

var diagonal = d3.svg.diagonal()
	.projection(function(d) { return [d.x, d.y]; });

var svg = d3.select("body").append("svg")
	.attr("width", "100%")
	.attr("height", "100%")
    .append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

root = treeData[0];

update(root);

function update(source) {

  // Compute the new tree layout.
  var nodes = tree.nodes(source).reverse(),
	  links = tree.links(nodes);

  // Normalize for fixed-depth.
  nodes.forEach(function(d) { d.y = d.depth * 100; });

  // Declare the nodes…
  var node = svg.selectAll("g.node")
	  .data(nodes, function(d) { return d.id || (d.id = ++i); });

  // Enter the nodes.
  var nodeEnter = node.enter().append("g")
	  .attr("class", "node")
	  .attr("transform", function(d) {
		  return "translate(" + d.x + "," + d.y + ")"; });

  nodeEnter.append("circle")
	  .attr("r", 10);

  nodeEnter.append("text")
	  .attr("y", function(d) {
		  return d.children || d._children ? -18 : 18;
      })
	  .attr("dy", ".35em")
	  .attr("text-anchor", "left")
	  .text(function(d) { return d.name; })
	  .style("fill-opacity", 1);

  // Declare the links…
  var link = svg.selectAll("path.link")
	  .data(links, function(d) { return d.target.id; });

  // Enter the links.
  link.enter().insert("path", "g")
	  .attr("class", "link")
	  .attr("d", diagonal);

}
