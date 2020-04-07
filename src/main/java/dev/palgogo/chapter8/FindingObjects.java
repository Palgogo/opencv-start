package dev.palgogo.chapter8;

import dev.palgogo.labs.Third;
import dev.palgogo.utils.CvUtils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static dev.palgogo.utils.CvUtilsFX.showImage;

public class FindingObjects {

    public void borderSelection() {
        Path path = Paths.get("src", "test", "resources", "photos", "meXS.jpg");
        Path fullResourcePath = path.toAbsolutePath();
        Mat img = Imgcodecs.imread(fullResourcePath.toString());
        if (img.empty()) {
            System.out.println("Can't load an image");
            return;
        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        showImage(imgGray, "GRAY");
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 80, 200, 3, true);
        //can be cool with slider
        showImage(edges, "Canny");
        Mat img3 = new Mat();
        Imgproc.threshold(imgGray, img3, 100, 255,
                Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Mat edges2 = new Mat();
        Imgproc.Canny(img3, edges2, 80, 200);
        showImage(edges2, "Canny + THRESH_OTSU");
        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(imgGray, img4, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY, 3, 5);
        Mat edges3 = new Mat();
        Imgproc.Canny(img4, edges3, 80, 200);
        showImage(edges3, "Canny + adaptiveThreshold");
        img.release();
        img3.release();
        img4.release();
        imgGray.release();
        edges.release();
        edges2.release();
        edges3.release();
    }

    public void findAndDrawContour() {
        Path path = Paths.get("src", "test", "resources", "photos", "house.png");
        Path fullResourcePath = path.toAbsolutePath();
        Mat img = Imgcodecs.imread(fullResourcePath.toString());
        if (img.empty()) {
            System.out.println("Can't load an image");
            return;
        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 100, 200);
        showImage(edges, "Canny");
        Mat edgesCopy = edges.clone(); //create Copy
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edgesCopy, contours, hierarchy,
                Imgproc.RETR_TREE,
                Imgproc.CHAIN_APPROX_SIMPLE);
        System.out.println(contours.size());
        System.out.println(hierarchy.size());
        System.out.println(hierarchy.dump());

        Imgproc.drawContours(img, contours, -1, CvUtils.COLOR_WHITE);
        showImage(img, "drawContours");
        img.release();
        imgGray.release();
        edges.release();
        edgesCopy.release();
        hierarchy.release();
    }

    public void findObjectByColor() {
        Mat img = Third.getMatWithImage();
        showImage(img, "Original");
        Mat hsv = new Mat();
        Imgproc.cvtColor(img, hsv, Imgproc.COLOR_BGR2HSV);

        Mat h = new Mat();
        Core.extractChannel(hsv, h, 0);

        Mat img2 = new Mat();
        Core.inRange(h, new Scalar(40), new Scalar(80), img2);
        showImage(img2, "Green");

        Core.inRange(h, new Scalar(100), new Scalar(140), img2);
        showImage(img2, "Blue");

        Core.inRange(hsv, new Scalar(0, 200, 200),
                new Scalar(20, 256, 256), img2);
        showImage(img2, "Red");

        Core.inRange(hsv, new Scalar(0, 0, 0),
                new Scalar(0, 0, 50), img2);
        showImage(img2, "Black");

        img.release();
        img2.release();
        hsv.release();
        h.release();
    }

    public void findingDifference() {
        Mat img = Third.getMatWithImage();
        showImage(img, "Original");

        Mat img2 = img.clone();
        Imgproc.circle(img2, new Point(200, 200), 50, CvUtils.COLOR_RED,
                Core.FILLED);
        showImage(img2, "Original + Circle");

        Mat img3 = new Mat();
        Core.absdiff(img2, img, img3);
        showImage(img3, "Difference");

        Mat img4 = new Mat();
        Imgproc.cvtColor(img3, img4, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(img4, img4, 1, 255, Imgproc.THRESH_BINARY);
        showImage(img4, "Thereshold");

        img.release();
        img2.release();
        img3.release();
        img4.release();
    }
}
