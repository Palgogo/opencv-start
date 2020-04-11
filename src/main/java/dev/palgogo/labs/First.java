package dev.palgogo.labs;

import dev.palgogo.utils.CvUtils;
import dev.palgogo.utils.CvUtilsFX;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class First {
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
        CvUtilsFX.showImage(img, "original image");

        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 100, 200);
        CvUtilsFX.showImage(edges, "Canny");

        Mat edgesCopy = edges.clone(); //create Copy
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edgesCopy, contours, hierarchy,
                Imgproc.RETR_TREE,
                Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(img, contours, -1, CvUtils.COLOR_WHITE);
        CvUtilsFX.showImage(img, "drawContours");

        img.release();
        imgGray.release();
        edges.release();
        edgesCopy.release();
        hierarchy.release();
    }
}
