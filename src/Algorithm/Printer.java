package Algorithm;

/**
 * Interface so that the Controller receives the maze grid data from the Algorithm's Main to pass it to the GUI's Main
 */
public interface Printer {
    void result(String output);
    void setMaze(int[][][] maze, int rows, int cols);
}