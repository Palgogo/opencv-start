package dev.palgogo.labs;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.palgogo.utils.CvUtilsFX.showImage;

public class Third {

    public static Mat getMatWithImage() {
        Path path = Paths.get("src", "test", "resources", "photos", "house.png");
        Path fullResourcePath = path.toAbsolutePath();
        Mat img = Imgcodecs.imread(fullResourcePath.toString());
        if (img.empty()) {
            System.out.println("Can't load an image");
            return null;
        }
        return img;
    }

    public void blurApplying() {
        Mat img = getMatWithImage();
        if (img == null) return;
        Mat img2 = new Mat();
        Imgproc.blur(img, img2, new Size(3, 3));
        showImage(img2, "Size(3, 3)");

        Mat img3 = new Mat();
        Imgproc.blur(img, img3, new Size(45, 45), new Point(-1, -1));
        showImage(img3, "Size(45, 45)");
        img.release();
        img2.release();
        img3.release();
    }

    public void gaussianBlurApplying() {
        Mat img = getMatWithImage();
        if (img == null) return;
        Mat img2 = new Mat();
        Imgproc.GaussianBlur(img, img2, new Size(3, 3), 0);
        showImage(img2, "Size(3, 3)");

        Mat img3 = new Mat();
        Imgproc.GaussianBlur(img, img3, new Size(45, 45), 0);
        showImage(img3, "Size(45, 45)");

        Mat img4 = new Mat();
        Imgproc.GaussianBlur(img, img4, new Size(0, 0), 1.5);
        showImage(img4, "Size(0, 0) , 1.5");

        img.release();
        img2.release();
        img3.release();
        img4.release();
    }

    public void medianBlurApplying() {
        Mat img = getMatWithImage();
        Mat img2 = new Mat();
        Imgproc.medianBlur(img, img2, 3);
        showImage(img2, "3");

        Mat img3 = new Mat();
        Imgproc.medianBlur(img, img3, 5);
        showImage(img3, "5");

        Mat img4 = new Mat();
        Imgproc.medianBlur(img, img4, 45);
        showImage(img4, "45");

        img.release();
        img2.release();
        img3.release();
        img4.release();
    }
}
