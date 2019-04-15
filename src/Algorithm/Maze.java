package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        sourceNode.setBlock(false);
        targetNode.setBlock(false);
        this.aStar = new AStar(this.rows, this.cols, this.height, sourceNode, targetNode);

        this.src = sourceNode;
        this.target = targetNode;

        // Used by the GUI
        this.sourcesList = new ArrayList<>();
        this.targetList = new ArrayList<>();
    }

    public void setSource(int x, int y, int z) throws Exception {
        if(this.aStar.isBlock(new Node(x,y,z)))
            throw new Exception("Source cell in a node that is already occupied!");
        else{
            this.maze[x][y][z] = SOURCE_CELL;
            this.src = new Node(x, y, z);
            this.src.setBlock(false);
            this.aStar.setInitialNode(this.src);
        }
    }

    public void setTarget(int x, int y, int z) throws Exception {
        if(this.aStar.isBlock(new Node(x,y,z)))
            throw new Exception("Target cell in a node that is already occupied!");
        else {
            this.maze[x][y][z] = TARGET_CELL;
            this.target = new Node(x, y, z);
            this.target.setBlock(false);
            this.aStar.setFinalNode(this.target);
        }
    }

    public Node getSource() {
        return this.src;
    }

    public Node getTarget() {
        return this.target;
    }

    public void setBlocks(int[][] blocksArray) {
        this.blocks.addAll(Arrays.asList(blocksArray));
        this.aStar.setBlocks(convertListTo2dArray(this.blocks));
        //this.blocks = blocksArray;
    }

    public void setMetal(int x, int y, int metalNumber) {
        this.maze[x][y][metalNumber] = 1;
    }

    public List<Node> findShortestPath() {
        this.sourcesList.add(this.getSource());  // Used by the GUI
        this.targetList.add(this.getTarget());   // Used by the GUI
        return this.aStar.findPath();
    }

    private int[][] convertListTo2dArray(List<int[]> list) {
        int[][] array = new int[list.size()][];
        for (int i = 0; i < array.length; i++) {
            array[i] = new int[list.get(i).length];
            array[i] = list.get(i);
        }
        return array;
    }

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

    public int[][][] getMaze() {
        return maze;
    }

}
