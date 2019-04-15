package Algorithm;

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

        this.setInitialNode(initialNode);
        this.setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols][height];
        //this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        this.setNodes();
        //this.closedSet = new HashSet<>();
    }

    private void setNodes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < height; k++) {
                    Node node = new Node(i, j, k);
                    node.setBlock(false);
                    node.calculateHeuristic(this.getFinalNode());
                    this.searchArea[i][j][k] = node;
                }
            }
        }
    }

    private void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < height; k++) {
                    Node node = this.searchArea[i][j][k];
                    node.calculateHeuristic(this.getFinalNode());
                    this.searchArea[i][j][k] = node;
                }
            }
        }
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        this.closedSet = new HashSet<>();
    }

    public void setBlocks(int[][] blocksArray) {
        for (int[] aBlocksArray : blocksArray) {
            int x = aBlocksArray[0];
            int y = aBlocksArray[1];
            int z = aBlocksArray[2];
            this.setBlock(x, y, z);
        }
    }

    public void getBlocks() {
        for (Node[][] nodeAr2 : this.searchArea) {
            for (Node[] nodeAr1: nodeAr2) {
                for (Node node:nodeAr1) {
                    if(node.isBlock())
                        System.out.println("Block: " + node.getX() + node.getY() + node.getZ());
                }
            }
        }
    }

    public List<Node> findPath() {
        this.reset();
        this.openList.add(this.getInitialNode());
        while (!this.isEmpty(this.openList)) {
            Node currentNode = this.openList.poll();
            this.closedSet.add(currentNode);
            if (this.isFinalNode(currentNode)) {
                return this.getPath(currentNode);
            } else {
                this.addAdjacentNodes(currentNode);
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
            this.addMiddleRow(currentNode);
        }
        else {  // is Metal 2
            this.addUpperRow(currentNode);
            this.addLowerRow(currentNode);
        }
    }

    private void addLowerRow(Node currentNode) {
        int x = currentNode.getX();
        int y = currentNode.getY();
        int z = currentNode.getZ();

        int lowerRow = x + 1;
        if (lowerRow < this.rows) {  // Check row down
            this.checkNode(currentNode, lowerRow, y, M2, this.getM2Cost());
        }
        if (currentNode.getZ() - 1 >= 0) {   // Check down
            this.checkLevelDown(currentNode);
        }
        if (currentNode.getZ() + 1 < height) {   // Check up
            this.checkLevelUp(currentNode);
        }
    }

    private void addUpperRow(Node currentNode) {
        int x = currentNode.getX();
        int y = currentNode.getY();
        int z = currentNode.getZ();

        int upperRow = x - 1;
        if (upperRow >= 0) {    // Check a row up
            this.checkNode(currentNode, upperRow, y, M2, this.getM2Cost());
        }
        if (currentNode.getZ() - 1 >= 0) {   // Check down
            this.checkLevelDown(currentNode);
        }
        if (currentNode.getZ() + 1 < height) {   // Check up
            this.checkLevelUp(currentNode);
        }
    }

    private void addMiddleRow(Node currentNode) {
        if (currentNode.getY() - 1 >= 0) {   // Check left
            this.checkLevelLeft(currentNode);
        }
        if (currentNode.getY() + 1 < this.cols) {     // Check right
            this.checkLevelRight(currentNode);
        }
        if (currentNode.getZ() - 1 >= 0) {   // Check down
            this.checkLevelDown(currentNode);
        }
        if (currentNode.getZ() + 1 < this.height) {   // Check up
            this.checkLevelUp(currentNode);
        }
    }

    private void checkLevelUp(Node currentNode) {
        int cost;
        if(currentNode.getZ() == M1)
            cost = getM1M2Cost();
        else if(currentNode.getZ() == M2)
            cost = getM2M3Cost();
        else {
            cost = 100;
            System.out.println("Logic Error: It went Up on a Metal 3");
        }
        this.checkNode(currentNode, currentNode.getX(), currentNode.getY(), currentNode.getZ()+1, cost);
    }

    private void checkLevelDown(Node currentNode) {
        int cost;
        if(currentNode.getZ() == M2)
            cost = getM1M2Cost();
        else if(currentNode.getZ() == M3)
            cost = getM2M3Cost();
        else {
            cost = 100;
            System.out.println("Logic Error: It went Up on a Metal 3");
        }
        this.checkNode(currentNode, currentNode.getX(), currentNode.getY(), currentNode.getZ()-1, cost);
    }

    private void checkLevelLeft(Node currentNode) {
        int cost;
        if(currentNode.getZ() == M1)
            cost = getM1Cost();
        else if(currentNode.getZ() == M3)
            cost = getM3Cost();
        else {
            cost = 100;
            System.out.println("Logic Error: It went left on a Metal 2");
        }
        this.checkNode(currentNode, currentNode.getX(), currentNode.getY() - 1, currentNode.getZ(), cost);
    }

    private void checkLevelRight(Node currentNode) {
        int cost;
        if(currentNode.getZ() == M1)
            cost = getM1Cost();
        else if(currentNode.getZ() == M3)
            cost = getM3Cost();
        else {
            cost = 100;
            System.out.println("Logic Error: It went right on a Metal 2");
        }
        this.checkNode(currentNode, currentNode.getX(), currentNode.getY() + 1, currentNode.getZ(), cost);
    }


    private void checkNode(Node currentNode, int x, int y, int z,  int cost) {
        Node adjacentNode = this.getSearchArea()[x][y][z];
        if (!adjacentNode.isBlock() && !this.getClosedSet().contains(adjacentNode)) {
            if (!this.getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                this.getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    this.getOpenList().remove(adjacentNode);
                    this.getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return (currentNode.getX() == this.finalNode.getX() && currentNode.getY() == this.finalNode.getY() && currentNode.getZ() == this.finalNode.getZ());
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return this.openList.size() == 0;
    }

    private void setBlock(int x, int y, int z) {
        this.searchArea[x][y][z].setBlock(true);
    }

    public boolean isBlock(Node node) {
        return this.searchArea[node.getX()][node.getY()][node.getZ()].isBlock();
    }

    public Node getInitialNode() {
        return this.initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return this.finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][][] getSearchArea() {
        return this.searchArea;
    }

    public void setSearchArea(Node[][][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return this.openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return this.closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }


    public int getM1Cost() {
        return M1_COST;
    }

    public void setM1Cost(int m1Cost) {
        M1_COST = m1Cost;
    }

    public int getM2Cost() {
        return M2_COST;
    }

    public void setM2Cost(int m2Cost) {
        M2_COST = m2Cost;
    }

    public int getM3Cost() {
        return M3_COST;
    }

    public void setM3Cost(int m3Cost) {
        M3_COST = m3Cost;
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
