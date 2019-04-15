package Algorithm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Scanner;

public class Utils {

    public static int[] takeGridDimsAndViaCost(Scanner reader) {
        System.out.print("Grid_Width: ");
        int w = reader.nextInt();

        System.out.print("Grid_Height: ");
        int h = reader.nextInt();

        System.out.print("Via_Cost: ");
        int via = reader.nextInt();

        return new int[]{w, h, via};
    }

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

    /** Get CPU time in nanoseconds. */
    public static long getCurrentCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }

}
