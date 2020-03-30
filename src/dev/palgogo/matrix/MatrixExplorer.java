package dev.palgogo.matrix;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MatrixExplorer {

    public void simpleMatrix() {
        Mat m = new Mat(3, 2, CvType.CV_8UC1);
        System.out.println(m.dump());
        System.out.println(m.cols());
        System.out.println(m.rows());
        System.out.println(m.width());
        System.out.println(m.height());
    }
}
