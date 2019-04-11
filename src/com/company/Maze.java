package com.company;

import java.util.List;

public class Maze {
    private static final int M1_CELL = 1;
    private static final int M2_CELL = 2;
    private static final int M3_CELL = 3;
    private static final int SOURCE_CELL = 4;
    private static final int TARGET_CELL = 5;

    private int[][][] maze;
    private int rows, cols;
    private int height = 3;
    private Node src, target;
    private int[][] blocks;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new int[this.rows][this.cols][this.height];

        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<this.cols; j++) {
                for(int k=0; k<this.height; k++)
                    maze[i][j][k] = 0;
            }
        }
    }

    public void setSource(int x, int y) {
        for(int z = 0; z<this.height; z++)
            maze[x][y][z] = SOURCE_CELL;
        this.src = new Node(x,y, AStar.M1);
    }

    public void setTarget(int x, int y) {
        for(int z = 0; z<this.height; z++)
            maze[x][y][z] = TARGET_CELL;
        this.target = new Node(x,y, AStar.M1);
    }

    public Node getSource() {
        return this.src;
    }

    public Node getTarget() {
        return this.target;
    }

    public void setBlocks(int[][] blocksArray) {
        this.blocks = blocksArray;
    }

    public void setMetal(int x, int y, int metalNumber) {
        maze[x][y][metalNumber] = 1;
    }

    public List<Node> findShortestPath() {
        AStar aStar = new AStar(this.rows, this.cols, this.height, getSource(), getTarget());
        aStar.setBlocks(this.blocks);
        return aStar.findPath();
    }

    public void print() {
        String val;

        for(int k=0; k<this.height; k++) {
             for (int i=0; i<this.rows; i++) {
                 for (int j=0; j<this.cols; j++) {
                    switch (maze[i][j][k]) {
                        case SOURCE_CELL:
                            val = "S";
                            break;
                        case TARGET_CELL:
                            val = "T";
                            break;
                        default:
                            val = "";
                            break;
                    }
                    if(val.equals(""))
                        System.out.print(maze[i][j][k] + " ");
                    else
                        System.out.print(val + " ");
                } // End of j loop
                 System.out.println("");
            } // End of i loop
            int metalNum = k + 1;
            System.out.println("\nMetal " + metalNum + "\n");
        } // End of k loop
    }

    public void printPath(List<Node> path) {
        int val;
        for (Node node : path) {
            switch (node.getZ()) {
                case AStar.M1:
                    val = M1_CELL;
                    break;
                case AStar.M2:
                    val = M2_CELL;
                    break;
                case AStar.M3:
                    val = M3_CELL;
                    break;
                default:
                    val = 0;
                    break;
            }
            maze[node.getX()][node.getY()][node.getZ()] = val;
        }
        this.print();
    }



}
