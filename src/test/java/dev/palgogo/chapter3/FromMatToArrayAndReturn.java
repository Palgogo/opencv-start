package dev.palgogo.chapter3;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FromMatToArrayAndReturn {

    @BeforeClass
    public void setUp() {
        System.load(Paths.get(".").toAbsolutePath().normalize().toString() + "\\libs\\opencv_java420.dll");
    }

    /**
     * if get() from Mat have 0, 0 and array in parameters - all mat data will copy to array.
     */
    @Test
    public void fromMatToArray() {
        Mat mat = new Mat(2, 3, CvType.CV_8UC1);
        double n = 1.0;
        for (int i = 0; i < mat.rows(); i++) {
            for (int j = 0; j < mat.cols(); j++) {
                mat.put(i, j, n);
            }
        }

        byte[] arr = new byte[mat.channels() * mat.rows() * mat.cols()];
        System.out.println(mat.get(0, 0, arr));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * if mat method put() have 0,0 and array - all array data will copy to Mat
     */
    @Test
    public void fromArrayToMat() {
        Mat mat = new Mat(3, 2, CvType.CV_8UC1);
        byte[] barr = {1, 2, 3, 4, 5, 6};
        mat.put(0, 0, barr);

        System.out.println(mat.dump());

        Assert.assertTrue(mat.dump().contains("3"));
    }

    @Test
    public void imageFromFileToBuffer() {
        Path path = Paths.get("src", "test", "resources", "photos", "meXS.jpg");
        Path fullResourcePath = path.toAbsolutePath();
        Mat img = Imgcodecs.imread(fullResourcePath.toString());
        if (img.empty()) {
            System.out.println("can't load an image");
        }

        MatOfByte buf = new MatOfByte();
        boolean st = Imgcodecs.imencode(".jpg", img, buf, new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 100));
        if (!st) {
            System.out.println("can't load an image to buffer");
        }

        System.out.println("buf size is " + buf.size().height);
    }
}
