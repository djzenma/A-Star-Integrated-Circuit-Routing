package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Interface between the Algorithm's Main Class and the AStar Class
 */
public class Maze {
    private static final int M1_CELL = 1;
    private static final int M2_CELL = 2;
    private static final int M3_CELL = 3;
    private static final int SOURCE_CELL = 4;
    private static final int TARGET_CELL = 5;

    private int[][][] maze;
    private AStar aStar;

    private int rows, cols;
    private int height = 3;

    private Node src, target;

    private List<int[]> blocks;

    public List<Node> sourcesList, targetList; // used only by the GUI

    /**
     * @param rows: rows of the Grid
     * @param cols: columns of the Grid
     * @param sourceNode: The first source node to be placed
     * @param targetNode: The first target node to be placed
     */
    public Maze(int rows, int cols, Node sourceNode, Node targetNode) {
        this.rows = rows;
        this.cols = cols;
        maze = new int[this.rows][this.cols][this.height];
        for (int i=0; i<this.rows; i++) {
            for (int j=0; j<this.cols; j++) {
                for(int k=0; k<this.height; k++)
                    maze[i][j][k] = 0;
            }
        }

        blocks = new ArrayList<>();

        sourceNode.setObstacle(false);
        targetNode.setObstacle(false);
        this.aStar = new AStar(this.rows, this.cols, this.height, sourceNode, targetNode);

        this.src = sourceNode;
        this.target = targetNode;

        // Used by the GUI
        this.sourcesList = new ArrayList<>();
        this.targetList = new ArrayList<>();
    }

    /**
     * @param x coordinate of the source pin
     * @param y coordinate of the source pin
     * @param z coordinate of the source pin
     * @throws Exception in case the source is already occupied
     */
    public void setSource(int x, int y, int z) throws Exception {
        if(this.aStar.isObstacle(new Node(x,y,z)))
            throw new Exception("Source cell in a node that is already occupied!");
        else{
            this.maze[x][y][z] = SOURCE_CELL;
            this.src = new Node(x, y, z);
            this.src.setObstacle(false);
            this.aStar.setInitialNode(this.src);
        }
    }

    /**
     * @param x coordinate of the target pin
     * @param y coordinate of the target pin
     * @param z coordinate of the target pin
     * @throws Exception in case the target is already occupied
     */
    public void setTarget(int x, int y, int z) throws Exception {
        if(this.aStar.isObstacle(new Node(x,y,z)))
            throw new Exception("Target cell in a node that is already occupied!");
        else {
            this.maze[x][y][z] = TARGET_CELL;
            this.target = new Node(x, y, z);
            this.target.setObstacle(false);
            this.aStar.setFinalNode(this.target);
        }
    }

    public Node getSource() {
        return this.src;
    }

    public Node getTarget() {
        return this.target;
    }

    /**
     * Sets the obstacles in the Grid
     * @param blocksArray which is an array of the obstacles to be placed, each element in this array
     *                    is another array of size 3 which contains the x,y,z coordinates of the obstacle
     */
    public void setObstacles(int[][] blocksArray) {
        this.blocks.addAll(Arrays.asList(blocksArray));
        this.aStar.setBlocks(convertListTo2dArray(this.blocks));
        //this.blocks = blocksArray;
    }

    public void setMetal(int x, int y, int metalNumber) {
        this.maze[x][y][metalNumber] = 1;
    }


    /**
     * @return The Shortest Path
     */
    public List<Node> findShortestPath() {
        this.sourcesList.add(this.getSource());  // Used by the GUI
        this.targetList.add(this.getTarget());   // Used by the GUI
        return this.aStar.findPath();
    }

    /**
     * @param list which is an array f type List
     * @return the list but in type 2d array
     */
    private int[][] convertListTo2dArray(List<int[]> list) {
        int[][] array = new int[list.size()][];
        for (int i = 0; i < array.length; i++) {
            array[i] = new int[list.get(i).length];
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Prints in the console the grid
     */
    public String print() {
        String val;
        StringBuilder output = new StringBuilder();

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
                    if(val.equals("")) {
                        System.out.print(maze[i][j][k] + " ");
                        output.append(maze[i][j][k]).append(" ");
                    }
                    else {
                        System.out.print(val + " ");
                        output.append(val).append(" ");
                    }
                } // End of j loop
                 System.out.println("");
            } // End of i loop
            int metalNum = k + 1;
            System.out.println("\nMetal " + metalNum + "\n");
        } // End of k loop

        return output.toString();
    }

    /** Prints the new Path in the Grid*/
    public String printPath(List<Node> path) {
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
            this.maze[node.getX()][node.getY()][node.getZ()] = val;
        }
        return this.print();
    }


    /**
     * @return CPU Time calculated by the AStar class
     */
    public long getCpuTime() {
        return this.aStar.getCpuTime();
    }

    /**
     * @return the 3d Maze Grid
     */
    public int[][][] getMaze() {
        return maze;
    }

}
