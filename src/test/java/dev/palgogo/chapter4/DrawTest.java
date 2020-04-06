package dev.palgogo.chapter4;

import dev.palgogo.utils.CvUtils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class DrawTest {
    @BeforeClass
    public void setUp() {
        System.load(Paths.get(".").toAbsolutePath().normalize().toString() + "\\libs\\opencv_java420.dll");
    }

    @Test
    public void drawLine() {
        Mat img = new Mat(300, 300, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.line(img, new Point(50, 50), new Point(250, 50), CvUtils.COLOR_RED);

        Imgproc.line(img, new Point(50, 100), new Point(250, 150), CvUtils.COLOR_BLUE, 5);
        //without antialiasing
        Imgproc.line(img, new Point(50, 150), new Point(250, 200),
                CvUtils.COLOR_GREEN, 5, Imgproc.LINE_4, 0);
        //with antialiasing
        Imgproc.line(img, new Point(50, 200), new Point(250, 250),
                CvUtils.COLOR_BLACK, 5, Imgproc.LINE_AA, 0);

        try {
            CvUtils.showImage(img, "Draw lines");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
