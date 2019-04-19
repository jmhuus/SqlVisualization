let directionalNodeGraph = new DirectionalNodeGraph(treeData, 700, 75  );
var diagramNodes = directionalNodeGraph.getDirectionalNodeGraph();


// set the dimensions and margins of the diagram
var margin = {top: 40, right: 90, bottom: 50, left: 90},
    width = 660 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

// SVG container
var svg = d3.select("body").append("svg")
    .attr("width", "100%")
    .attr("height", "100%")
    .append("g")
        .attr("transform", "translate("+margin.left+","+margin.top+")");

// Draw links between nodes
for (var i = 0; i < diagramNodes.length; i++) {
    var rootNode = diagramNodes[i];
    for (var x = 0; x < diagramNodes[i].parents.length; x++) {
        var parentNode = getNodeById(diagramNodes[i].parents[x], diagramNodes);
        svg.append("path")
            .attr("class", "link")
            .attr("d",
                "M" + rootNode.x + "," + rootNode.y
                + "C" + rootNode.x + "," + (rootNode.y + parentNode.y) / 2
                + " " + parentNode.x + "," +  (rootNode.y + parentNode.y) / 2
                + " " + parentNode.x + "," + parentNode.y
            );
    }
}

// Group element to contain
var node = svg.selectAll(".node")
    .data(diagramNodes)
    .enter()
    .append("g")
        .attr("class", "node")
        .attr("transform", function(d) {
            return "translate(" + d.x + "," + d.y + ")";
        });

// Node title
node.append("text")
    .attr("dy", ".35em")
    .attr("y", "-20")
    .style("text-anchor", "middle")
    .text(function(d) { return d.name; });

// Draw circle
node.append("circle")
    .attr("r", 10);



// Function to retreive nodes by ID
function getNodeById(id, nodes){
    for (var i = 0; i < nodes.length; i++) {
        if(nodes[i].id===id){
            return nodes[i];
        }
    }
}








//
