package Algorithm;

/**
 * A node in the AStar search area grid
 */
public class Node {

    private int g;
    private int f;
    private int h;
    private int x;
    private int y;
    private boolean isObstacle;
    private Node parent;
    private int z;

    /**
     * @param x coordinate of this node
     * @param y coordinate of this node
     * @param z coordinate of this node
     */
    public Node(int x, int y, int z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /** Calculates the Heuristic value from this node to the target node
     * @param finalNode Target Node
     */
    public void calculateHeuristic(Node finalNode) {
        this.h = Math.abs(finalNode.getX() - getX()) + Math.abs(finalNode.getY() - getY()) + Math.abs(finalNode.getZ() - getZ());
    }

    /** Setter for the Node's Data
     * @param currentNode Parent of this node
     * @param cost Movement Cost
     */
    public void setNodeData(Node currentNode, int cost) {
        setParent(currentNode);
        setG(currentNode.getG() + cost);
        calculateFinalCost();
    }

    /**
     * @param currentNode Node with a cost to be compared with
     * @param cost a cost of movement
     * @return false if it is not a better path, true if it is a better path.
     */
    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    /**
     * Calculate the Final Cost of this node (G + H)
     */
    private void calculateFinalCost() {
        setF(getG() + getH());
    }

    /**
     * @param arg0 the node to be compared with
     * @return true if the the arg0 Node and this node have the same coordinates
     */
    @Override
    public boolean equals(Object arg0) {
        Node other = (Node) arg0;
        return this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() == other.getZ();
    }

    /**
     * @return The coordinates of this node in a nice way
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    /**
     * A bunch of getters and setters
     *
     */

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean isBlock) {
        this.isObstacle = isBlock;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}