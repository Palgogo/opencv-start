package dev.palgogo.chapter7;

import dev.palgogo.utils.CvUtils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilterApplyingTest {
    @BeforeClass
    public void setUp() {
        System.load(Paths.get(".").toAbsolutePath().normalize().toString() + "\\libs\\opencv_java420.dll");
    }

    @Test
    public void blurApplying() {
        Path path = Paths.get("src", "test", "resources", "photos", "house.png");
        Path fullResourcePath = path.toAbsolutePath();
        Mat img = Imgcodecs.imread(fullResourcePath.toString());
        if (img.empty()) {
            System.out.println("Can't load an image");
            return;
        }
        Mat img2 = new Mat();
        Imgproc.blur(img, img2, new Size(3, 3));
        CvUtils.showImage(img2, "Size(3, 3)");

        Mat img3 = new Mat();
        Imgproc.blur(img, img3, new Size(45, 45), new Point(-1, -1));
        CvUtils.showImage(img3, "Size(45, 45)");
        img.release();
        img2.release();
        img3.release();
    }
}
