package dev.palgogo.chapter8;

import dev.palgogo.utils.CvUtils;
import dev.palgogo.utils.CvUtilsFX;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        CvUtilsFX.showImage(imgGray, "GRAY");
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 80, 200, 3, true);
        //can be cool with slider
        CvUtilsFX.showImage(edges, "Canny");
        Mat img3 = new Mat();
        Imgproc.threshold(imgGray, img3, 100, 255,
                Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Mat edges2 = new Mat();
        Imgproc.Canny(img3, edges2, 80, 200);
        CvUtilsFX.showImage(edges2, "Canny + THRESH_OTSU");
        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(imgGray, img4, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY, 3, 5);
        Mat edges3 = new Mat();
        Imgproc.Canny(img4, edges3, 80, 200);
        CvUtilsFX.showImage(edges3, "Canny + adaptiveThreshold");
        img.release();
        img3.release();
        img4.release();
        imgGray.release();
        edges.release();
        edges2.release();
        edges3.release();
    }

    public void findAndDrawContour() {
        Path path = Paths.get("src", "test", "resources", "photos", "meXS.jpg");
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
        CvUtilsFX.showImage(edges, "Canny");
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
        CvUtils.showImage(img, "drawContours");
        img.release();
        imgGray.release();
        edges.release();
        edgesCopy.release();
        hierarchy.release();
    }
}
