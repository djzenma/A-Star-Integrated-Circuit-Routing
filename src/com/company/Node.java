package com.company;

public class Node {

    private int g;
    private int f;
    private int h;
    private int x;
    private int y;
    private boolean isBlock;
    private Node parent;
    private int z;

    public Node(int x, int y, int z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void calculateHeuristic(Node finalNode) {
        this.h = Math.abs(finalNode.getX() - getX()) + Math.abs(finalNode.getY() - getY()) + Math.abs(finalNode.getZ() - getZ());
    }

    public void setNodeData(Node currentNode, int cost) {
        setParent(currentNode);
        setG(currentNode.getG() + cost);
        calculateFinalCost();
    }

    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        setF(getG() + getH());
    }

    @Override
    public boolean equals(Object arg0) {
        Node other = (Node) arg0;
        return this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() == other.getZ();
    }

    @Override
    public String toString() {
        return "Node (" + x + ", " + y + ", " + z + ")";
    }

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

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
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