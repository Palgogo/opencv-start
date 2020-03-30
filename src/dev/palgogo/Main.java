package dev.palgogo;

import dev.palgogo.matrix.MatrixExplorer;
import org.opencv.core.Core;
import org.opencv.core.Point;

public class Main {
static {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
}

    public static void main(String[] args) {
        PointExplorer pointExplorer = new PointExplorer();
        MatrixExplorer matrixExplorer = new MatrixExplorer();

        matrixExplorer.simpleMatrix();


    }

}
