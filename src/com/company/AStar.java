package com.company;

import java.util.*;


public class AStar {
    public static final int M1 = 0;
    public static final int M2 = 1;
    public static final int M3 = 2;

    private static int M1_COST = 1;
    private static int M2_COST = 5;
    private static int M3_COST = 10;

    private static int M1_M2_COST = 60;     // Cost Of passing from M1 to M2 or vice versa
    private static int M2_M3_COST = 100;    // Cost Of passing from M2 to M3 or vice versa

    private final int height;
    private final int cols;
    private final int rows;

    private Node[][][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;

    public AStar(int rows, int cols, int height, Node initialNode, Node finalNode) {
        this.rows = rows;
        this.cols = cols;
        this.height = height;

        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols][height];
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        setNodes();
        this.closedSet = new HashSet<>();
    }

    //public AStar(int rows, int cols, int height, Node initialNode, Node finalNode) {
      //  this(rows, cols, height, initialNode, finalNode, M1_COST, M2_COST, M3_COST);
    //}

    private void setNodes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < height; k++) {
                    Node node = new Node(i, j, k);
                    node.calculateHeuristic(getFinalNode());
                    this.searchArea[i][j][k] = node;
                }
            }
        }
    }

    public void setBlocks(int[][] blocksArray) {
        for (int[] aBlocksArray : blocksArray) {
            int x = aBlocksArray[0];
            int y = aBlocksArray[1];
            int z = aBlocksArray[2];
            setBlock(x, y, z);
        }
    }

    public List<Node> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                System.out.println("Found");
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        return new ArrayList<Node>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private void addAdjacentNodes(Node currentNode) {
        if(currentNode.getZ() == M1 || currentNode.getZ() == M3) {
            addAdjacentMiddleRow(currentNode);
        }
        else {  // is Metal 2
            addAdjacentUpperRow(currentNode);
            addAdjacentLowerRow(currentNode);
        }
    }

    private void addAdjacentLowerRow(Node currentNode) {
        int x = currentNode.getX();
        int y = currentNode.getY();
        int z = currentNode.getZ();

        int lowerRow = x + 1;
        if (lowerRow < rows) {
            //if (col - 1 >= 0) {
                //checkNode(currentNode, col - 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            //}
            //if (col + 1 < getSearchArea()[0].length) {
                //checkNode(currentNode, col + 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            //}
            checkNode(currentNode, lowerRow, y, M2, getM2Cost());
        }
    }

    private void addAdjacentUpperRow(Node currentNode) {
        int x = currentNode.getX();
        int y = currentNode.getY();
        int z = currentNode.getZ();

        int upperRow = x - 1;
        if (upperRow >= 0) {
            //if (col - 1 >= 0) {
            //checkNode(currentNode, col - 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            //}
            //if (col + 1 < getSearchArea()[0].length) {
            //    checkNode(currentNode, col + 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            //}
            checkNode(currentNode, upperRow, y, M2, getM2Cost());
        }
    }

    private void addAdjacentMiddleRow(Node currentNode) {
        int x = currentNode.getX();
        int y = currentNode.getY();
        int z = currentNode.getZ();

        if (y - 1 >= 0) {   // Check left
            int cost;
            if(z == M1)
                cost = getM1Cost();
            else if(z == M3)
                cost = getM3Cost();
            else {
                cost = 100;
                System.out.println("Logic Error: It went left on a Metal 2");
            }
            checkNode(currentNode, x, y - 1, z, cost);
        }
        if (y + 1 < cols) {     // Check right
            int cost;
            if(z == M1)
                cost = getM1Cost();
            else if(z == M3)
                cost = getM3Cost();
            else {
                cost = 100;
                System.out.println("Logic Error: It went right on a Metal 2");
            }
            checkNode(currentNode, x, y + 1, z, cost);
        }
        if (z - 1 >= 0) {   // Check down
            int cost;
            if(z == M2)
                cost = getM1M2Cost();
            else if(z == M3)
                cost = getM2M3Cost();
            else
                cost = 100;
            checkNode(currentNode, x, y, z-1, cost);
        }
        if (z + 1 < height) {   // Check up
            int cost;
            if(z == M1)
                cost = getM1M2Cost();
            else if(z == M2)
                cost = getM2M3Cost();
            else
                cost = 100;
            checkNode(currentNode, x, y, z+1, cost);
        }

    }


    private void checkNode(Node currentNode, int x, int y, int z,  int cost) {
        Node adjacentNode = getSearchArea()[x][y][z];
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return (currentNode.getX() == finalNode.getX() && currentNode.getY() == finalNode.getY());
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    private void setBlock(int x, int y, int z) {
        this.searchArea[x][y][z].setBlock(true);
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }


    public int getM1Cost() {
        return M1_COST;
    }

    public void setM1Cost(int m1Cost) {
        this.M1_COST = m1Cost;
    }

    public int getM2Cost() {
        return M2_COST;
    }

    public void setM2Cost(int m2Cost) {
        this.M2_COST = m2Cost;
    }

    public int getM3Cost() {
        return M3_COST;
    }

    public void setM3Cost(int m3Cost) {
        this.M3_COST = m3Cost;
    }

    public static int getM1M2Cost() {
        return M1_M2_COST;
    }

    public static void setM1M2Cost(int m1M2Cost) {
        M1_M2_COST = m1M2Cost;
    }

    public static int getM2M3Cost() {
        return M2_M3_COST;
    }

    public static void setM2M3Cost(int m2M3Cost) {
        M2_M3_COST = m2M3Cost;
    }

}
