package main.java.dev.palgogo.chapter2;

import org.opencv.core.*;
import org.opencv.utils.Converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    }

    public void matOfInt() {
        int[] arr = {1, 2, 3};
        MatOfInt m1 = new MatOfInt();
        MatOfInt m2 = new MatOfInt(arr);

        System.out.println("working with MatInt");
        System.out.println(m1.dump());
        System.out.println(m2.dump());
    }

    public void matToVector() {
        ArrayList<Integer> arr = new ArrayList<>();
        MatOfInt matOfInt = new MatOfInt(1, 2, 3);
        Converters.Mat_to_vector_int(matOfInt, arr);
        System.out.println(arr);
    }

    public void matOfPoint() {
        MatOfPoint matOfPoint = new MatOfPoint();
        matOfPoint.fromArray(new Point(0, 0), new Point(1, 1));
        System.out.println(matOfPoint.dump());

        Point[] points = matOfPoint.toArray();
        System.out.println(Arrays.toString(points));

        ArrayList<Point> list = new ArrayList<>();
        Collections.addAll(list, new Point(0, 0), new Point(1, 1));
        MatOfPoint m2 = new MatOfPoint();
        m2.fromList(list);
        System.out.println(m2.dump());

        List<Point> list1 = m2.toList();
        System.out.println(list1);

    }
}
