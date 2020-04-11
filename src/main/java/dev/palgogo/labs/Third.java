package dev.palgogo.labs;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.palgogo.utils.CvUtilsFX.showImage;
import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.cvtColor;

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

    public void applyFilters() {
        Mat original = getMatWithImage();
        if (original == null) return;
        showImage(original, "original image");

        Imgproc.resize(original, original, new Size(), 0.70, 0.70, ADAPTIVE_THRESH_MEAN_C);
        showImage(original, "resized");

        Mat img2 = new Mat();
        cvtColor(original, img2, Imgproc.COLOR_BGR2GRAY);
        showImage(img2, "gray image");

        Mat img3 = new Mat();
        Imgproc.GaussianBlur(original, img3, new Size(45, 45), 0);
        showImage(img3, "gaussian blur");

        Mat img4 = new Mat();
        Imgproc.pyrMeanShiftFiltering(original, img4, 20, 50, 1, new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 5, 1));
        showImage(img4, "grouping with similar features");

        original.release();
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
