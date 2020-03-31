package dev.palgogo.matrix;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MatrixExplorer {

    public void simpleMatrix() {
        Mat m = new Mat(1, 3, CvType.CV_8UC1);
        double n = 1.0;

        for (int i = 0; i < m.rows(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                m.put(i, j, n++);
            }
        }
        System.out.println(m.dump());

        Mat mat2 = Mat.diag(m);
        System.out.println(mat2.dump());

    }
}
