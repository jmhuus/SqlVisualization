class DirectionalNodeGraph{
    constructor(data, pixelWidth, layerHeight){
        this.layerHeight = layerHeight;
        this.pixelWidth = pixelWidth;
        this.nodes = data;
        this.nodes.forEach(function(node){
            node.visited = false;
        });
    }


    getDirectionalNodeGraph(){
        this.setNodeBlockWidth();
        this.setNodeLayers();
        this.remapNodeLayersToBePositive();
        this.setNodeCoordinates();

        return this.nodes;
    }

    getNodeById(id){
        for (var i = 0; i < this.nodes.length; i++) {
            if(this.nodes[i].id===id){
                return this.nodes[i];
            }
        }
    }

    // Calculates the blockWidth of each node based on the number parent nodes
    setNodeBlockWidth(){
        for (var i = 0; i < this.nodes.length; i++) {
            if (this.nodes[i].visited === false) {

                // Leaf node, blockWidth of 1
                if(this.nodes[i].parents.length===0){
                    this.nodes[i].blockWidth = 1;
                    this.nodes[i].visited = true;
                    continue;
                }

                // Node blockWidth = sum blockWidths of parents
                var nodeBlockWidth = 0;
                for (var x = 0; x < this.nodes[i].parents.length; x++) {
                    // Only sum parent nodes that are immediately related
                    // If there are multiple paths to a parent, then don't sum
                    if (this.getPathsCountBetweenTwoNodes(this.nodes[i], this.getNodeById(this.nodes[i].parents[x])) === 1) {
                        nodeBlockWidth += this.getNodeBlockWidth(this.getNodeById(this.nodes[i].parents[x]));
                    }
                }
                this.nodes[i].blockWidth = nodeBlockWidth;
                this.nodes[i].visited = true;
            }
        }
    }


    // Calculate blockWidths for all nodes
    getNodeBlockWidth(node){
        // Node already visited, skip
        if(node.visited){return node.blockWidth;}

        // Leaf node; blockWidth = 1
        if(node.parents.length===0){
            node.blockWidth = 1;
            node.visited = true;
            return 1;
        }


        // Add all parent node blockWidths
        var nodeBlockWidth = 0;
        for (var x = 0; x < node.parents.length; x++) {
            // Only sum parent nodes that are immediately related
            // If there are multiple paths to a parent, then don't sum
            if (this.getPathsCountBetweenTwoNodes(node, this.getNodeById(node.parents[x])) === 1) {
                nodeBlockWidth += this.getNodeBlockWidth(this.getNodeById(node.parents[x]));
            }
        }
        node.visited = true;
        node.blockWidth = nodeBlockWidth;
        return nodeBlockWidth;
    }


    // DFS to count the number of paths available between a node and one of it's parents
    getPathsCountBetweenTwoNodes(rootNode, targetNode){
        var pathCount = 0;
        for (var i = 0; i < rootNode.parents.length; i++) {
            pathCount += this.countPaths(this.getNodeById(rootNode.parents[i]), targetNode);
        }

        return pathCount;
    }
    countPaths(rootNode, targetNode){
        // Target found, return path count of 1
        if(rootNode.id === targetNode.id){return 1;}

        var pathCount = 0;
        for (var i = 0; i < rootNode.parents.length; i++) {
            pathCount += this.countPaths(this.getNodeById(rootNode.parents[i]), targetNode);
        }

        return pathCount;
    }

    // Recursively set each node layer
    setNodeLayers(){
        // Reset visited flag
        this.nodes.forEach(function(node){
            node.visited = false;
        });

        // Set layer for each node
        // TODO: eventually account for multiple node diagrams; diagrams that are separate from each other
        var startingLayer = 0;
        this.nodes[0].layer = startingLayer;
        this.nodes[0].visited = true;
        this.setParentLayers(this.nodes[0], startingLayer);
        this.setChildLayers(this.nodes[0], startingLayer);
    }
    setParentLayers(node, layerPos){
        for (var i = 0; i < node.parents.length; i++) {

            // Ensure that parent nodes are immediate node memebers
            if ((this.getPathsCountBetweenTwoNodes(node, this.getNodeById(node.parents[i])) !== 1) || this.getNodeById(node.parents[i]).visited) {
                continue;
            }

            // Set layer
            this.getNodeById(node.parents[i]).visited = true;
            this.getNodeById(node.parents[i]).layer = layerPos+1;

            // Set parent layers
            this.setParentLayers(this.getNodeById(node.parents[i]), layerPos+1);

        }
    }
    setChildLayers(node, layerPos){
        for (var i = 0; i < this.nodes.length; i++) {
            for (var x = 0; x < this.nodes[i].parents.length; x++) {

                // Ensure that child nodes are immediate node memebers
                if (this.getPathsCountBetweenTwoNodes(this.nodes[i], this.getNodeById(this.nodes[i].parents[x])) !== 1) {
                    continue;
                }

                // Connection found, set layer
                if (node.id === this.nodes[i].parents[x]) {
                    this.nodes[i].visited = true;
                    this.nodes[i].layer = layerPos-1;
                    var randNode = this.nodes[i];
                    this.setChildLayers(this.nodes[i], layerPos-1);
                    this.setParentLayers(this.nodes[i], layerPos-1);
                }
            }
        }
    }

    // Node layers can be negative or positive, remap to always be positive
    remapNodeLayersToBePositive(){
        // Locate lowest layer number
        var lowestLayer = 0;
        var largestLayer = 0;
        for (var i = 0; i < this.nodes.length; i++) {
            if (this.nodes[i].layer < lowestLayer) { lowestLayer = this.nodes[i].layer; }
            if (this.nodes[i].layer > largestLayer) { largestLayer  = this.nodes[i].layer}
        }

        largestLayer += Math.abs(lowestLayer);

        // Push each node layer up by abs(lowestLayer)
        this.nodes.forEach(function(node){
            node.layer += Math.abs(lowestLayer);
            node.layer = largestLayer - node.layer;
        });
    }

    // Uses node blockWidths to position node x,y coordinates
    setNodeCoordinates(){
        // Reset visited flag
        this.nodes.forEach(function(node){
            node.visited = false;
        });

        // Retrieve the largest possible node blockWidth
        var maxNodeWidth = 0;
        for (var i = 0; i < this.nodes.length; i++) {
            if (this.nodes[i].blockWidth > maxNodeWidth) {
                maxNodeWidth = this.nodes[i].blockWidth;
            }
        }

        // Set X coordinate for each node
        for (var i = 0; i < this.nodes.length; i++) {
            // X coordinates for non-leaf nodes
            if (this.nodes[i].parents.length >= 1 && !this.nodes[i].visited) {
                this.nodes[i].x = this.pixelWidth * (this.nodes[i].blockWidth / maxNodeWidth);
                this.nodes[i].visited = true;

                // Count parent leaf nodes
                var parentLeafNodeCount = 0;
                for (var x = 0; x < this.nodes[i].parents.length; x++) {
                    if (this.getNodeById(this.nodes[i].parents[x]).blockWidth === 1) {
                        parentLeafNodeCount++;
                    }
                }

                // Calculate parent leaf node available width
                var availableParentLeafNodeWidth = this.pixelWidth * (parentLeafNodeCount / maxNodeWidth);
                var startingLeafNodeX = (this.pixelWidth * (this.nodes[i].blockWidth / maxNodeWidth)) - (availableParentLeafNodeWidth / 3)
                var allocatedSpacePerParentLeafNode = availableParentLeafNodeWidth / parentLeafNodeCount;

                // X coordinate for leaf nodes
                for (var x = 0; x < this.nodes[i].parents.length; x++) {
                    if (this.getNodeById(this.nodes[i].parents[x]).blockWidth === 1) {
                        this.getNodeById(this.nodes[i].parents[x]).x = startingLeafNodeX;
                        this.getNodeById(this.nodes[i].parents[x]).visited = true;


                        // Single parent leaf nodes should be drawn
                        // TODO: not ideal aglorithm, need to brainstorm high-level approach to visualizing single node hierarchies
                        if (this.getNodeById(this.nodes[i].parents[x]).parents.length === 1) {
                            this.getNodeById(this.getNodeById(this.nodes[i].parents[x]).parents[0]).x = startingLeafNodeX;
                            this.getNodeById(this.getNodeById(this.nodes[i].parents[x]).parents[0]).visited = true;
                        }

                        startingLeafNodeX += allocatedSpacePerParentLeafNode;
                    }
                }
            }

            // Y coordinate for all nodes
            this.nodes[i].y = this.layerHeight * this.nodes[i].layer;
        }
    }
}







//
