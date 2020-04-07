package dev.palgogo.chapter10;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static dev.palgogo.utils.CvUtilsFX.showImage;
import static org.opencv.imgproc.Imgproc.cvtColor;

public class CornerDetection {

    public static Mat getMatWithImageWords() {
        Path path = Paths.get("src", "main", "resources", "photos", "carpe_diem.jpg");
        Path fullResourcePath = path.toAbsolutePath();
        Mat img = Imgcodecs.imread(fullResourcePath.toString());
        if (img.empty()) {
            System.out.println("Can't load an image");
            return null;
        }
        return img;
    }

    public void cornerHarrisDetection() {
        Mat img = getMatWithImageWords();
        cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        showImage(img, "Original");

        Mat dst = new Mat();
        Imgproc.cornerHarris(img, dst, 2, 3, 0.04);

        Core.MinMaxLocResult m = Core.minMaxLoc(dst);
        Imgproc.threshold(dst, dst, m.maxVal * 0.01, 1.0, Imgproc.THRESH_BINARY);

        showImage(dst, "Result");

        img.release();
        dst.release();
    }

    public void cornerEigenValsAndVecsDetection() {
        Mat img = getMatWithImageWords();
        cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        showImage(img, "Original");

        Mat dst = new Mat();
        Imgproc.cornerMinEigenVal(img, dst, 2, 3);

        Mat dst2 = new Mat();
        Imgproc.cornerEigenValsAndVecs(img, dst2, 2, 3);

        Core.MinMaxLocResult m = Core.minMaxLoc(dst);
        System.out.println(dst2.channels());

        double[] maxDst = dst.get((int) m.maxLoc.y, (int) m.maxLoc.x);
        System.out.println(Arrays.toString(maxDst));

        double[] maxDst2 = dst2.get((int) m.maxLoc.y, (int) m.maxLoc.x);
        System.out.println(Arrays.toString(maxDst2));

        Imgproc.threshold(dst, dst, m.maxVal * 0.01, 1.0, Imgproc.THRESH_BINARY);
        dst.convertTo(dst, CvType.CV_8U, 255);

        showImage(dst, "Result");

        img.release();
        dst.release();
    }
}
