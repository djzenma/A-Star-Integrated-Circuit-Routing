package Algorithm;

import java.util.*;


/**
 * The actual AStar Algorithm Class
 */
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

    private long cpuTime, cpuTimeStart;

    /**
     * @param rows number of rows in the search Area
     * @param cols number of columns in the search Area
     * @param height height of the search Area
     * @param initialNode The very first Source node in the search Area
     * @param finalNode The very first Target node in the search Area
     */
    public AStar(int rows, int cols, int height, Node initialNode, Node finalNode) {
        this.rows = rows;
        this.cols = cols;
        this.height = height;

        this.setInitialNode(initialNode);
        this.setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols][height];
        this.setNodes();

        cpuTime = 0;
        cpuTimeStart = 0;
    }


    /**
     * Initializes the The search area with idle nodes
     */
    private void setNodes() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < height; k++) {
                    Node node = new Node(i, j, k);
                    node.setObstacle(false);
                    node.calculateHeuristic(this.getFinalNode());
                    this.searchArea[i][j][k] = node;
                }
            }
        }
    }


    /**
     *  Resets the openList and ClosedSet, i.e destroys them and makes new ones.
     *  And Recalculates the heuristic of each node as the final Node most probably have changed when the user entered new coordinates.
     *  Used When calculating a new path by the findPath method.
     */
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

    /**
     * @param blocksArray coordinates of the obstacles to be placed
     */
    public void setBlocks(int[][] blocksArray) {
        for (int[] aBlocksArray : blocksArray) {
            int x = aBlocksArray[0];
            int y = aBlocksArray[1];
            int z = aBlocksArray[2];
            this.setObstacle(x, y, z);
        }
    }

    public void getBlocks() {
        for (Node[][] nodeAr2 : this.searchArea) {
            for (Node[] nodeAr1: nodeAr2) {
                for (Node node:nodeAr1) {
                    if(node.isObstacle())
                        System.out.println("Block: " + node.getX() + node.getY() + node.getZ());
                }
            }
        }
    }

    /**
     * The Actual A* Algorithm
     * @return Path List containing the nodes used by the path.
     * If path.size() is 0 then no path had been found.
     */
    public List<Node> findPath() {
        this.reset();

        this.cpuTimeStart = Utils.getCurrentCpuTime();   // to measure performance, Start recording

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


    /**
     * Traces the parent from a given current Node all the way up to the source node To return the Path between them.
     * @param currentNode
     * @return Path from the currentNode to the oldest parent in the path.
     */
    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }

        this.cpuTime = Utils.getCurrentCpuTime() - cpuTimeStart;
        return path;
    }

    /** Looks what in what metal is the given Node in, then sees what Nodes it can check
     * @param currentNode
     */
    private void addAdjacentNodes(Node currentNode) {
        if(currentNode.getZ() == M1 || currentNode.getZ() == M3) {
            this.addYZPlane(currentNode);
        }
        else {  // is Metal 2
            this.addXZPlanePos(currentNode);
            this.addXZPlaneNeg(currentNode);
        }
    }

    /** Checks a row down, a metal down, and a metal up
     * @param currentNode
     */
    private void addXZPlaneNeg(Node currentNode) {
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

    /** Checks a row up, a metal down, and a metal up
     * @param currentNode
     */
    private void addXZPlanePos(Node currentNode) {
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

    /** Checks left col, right col, a metal down, and a metal up
     * @param currentNode
     */
    private void addYZPlane(Node currentNode) {
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


    /** Checks a Metal Level Higher
     * @param currentNode
     */
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

    /** Checks a Metal Level Lower
     * @param currentNode
     */
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

    /** Checks for Column Left
     * @param currentNode
     */
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

    /** Checks for Column Right
     * @param currentNode
     */
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
        if (!adjacentNode.isObstacle() && !this.getClosedSet().contains(adjacentNode)) {
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


    /** Checks if the input node is the target node
     * @param currentNode input node
     * @return true if final node, false otherwise
     */
    private boolean isFinalNode(Node currentNode) {
        return (currentNode.getX() == this.finalNode.getX() && currentNode.getY() == this.finalNode.getY() && currentNode.getZ() == this.finalNode.getZ());
    }

    /**
     * @param openList the Open List
     * @return true if the open List is empty, otherwise false.
     */
    private boolean isEmpty(PriorityQueue<Node> openList) {
        return this.openList.size() == 0;
    }


    /**
     * @param x coordinate of the Node becoming an obstacle
     * @param y coordinate of the Node becoming an obstacle
     * @param z coordinate of the Node becoming an obstacle
     */
    private void setObstacle(int x, int y, int z) {
        this.searchArea[x][y][z].setObstacle(true);
    }

    /**
     * @param node input node
     * @return true of the node is an obstacle, false otherwise.
     */
    public boolean isObstacle(Node node) {
        return this.searchArea[node.getX()][node.getY()][node.getZ()].isObstacle();
    }


    /**
     * Then, A Bunch of Getters and Setters
     *
     */

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

    public long getCpuTime() {
        return this.cpuTime;
    }

}
