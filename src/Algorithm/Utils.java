package Algorithm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Scanner;

/**
 * A Utility class that has several helper functions
 */
public class Utils {

    /**
     * @param reader Scanner instance
     * @return An array where, index 0 contains the wanted Width of the grid,
                index 1 contains the wanted height of the grid,
                index 2 contains the wanted via Cost
     *
     */
    public static int[] takeGridDimsAndViaCost(Scanner reader) {
        System.out.print("Grid_Width: ");
        int w = reader.nextInt();

        System.out.print("Grid_Height: ");
        int h = reader.nextInt();

        System.out.print("Via_Cost: ");
        int via = reader.nextInt();

        return new int[]{h, w, via};
    }

    /**
     * @param msg Message to be displayed in the console before taking the coordinates
     * @param reader Scanner instance
     * @return An array of the coordinates x in index 0, y in index 1, z in index 2
     */
    public static int[] takeCoordinates(String msg, Scanner reader) {
        System.out.println(msg);
        System.out.print("x: ");
        int x = reader.nextInt();

        System.out.print("y: ");
        int y = reader.nextInt();

        System.out.print("z: ");
        int z = reader.nextInt();

        return new int[]{x, y, z};
    }

    /**
     * @return Current CPU time in nanoseconds.
     */
    public static long getCurrentCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }

}
