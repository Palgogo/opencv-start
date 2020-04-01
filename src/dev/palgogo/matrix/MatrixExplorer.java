package dev.palgogo.matrix;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

public class MatrixExplorer {

    public void simpleMatrix() {
        Mat m = new Mat();
        m.create(2, 3, CvType.CV_8UC1);
        m.setTo(new Scalar(1));
        System.out.println(m.dump());

        m.create(new Size(2, 2), CvType.CV_8UC3);
        m.setTo(new Scalar(5));
        System.out.println(m.dump());

        System.out.println("elements: " + m.elemSize());
        //page 86
    }
}
