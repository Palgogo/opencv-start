package dev.palgogo.chapter10;

import dev.palgogo.utils.CvUtils;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    public void keyPointsComparison() {
        Path pathToImage = Paths.get("src", "main", "resources", "photos", "carpe_diem.jpg");
        Path pathToImage90Degree = Paths.get("src", "main", "resources", "photos", "carpe_diem_90.jpg");
        Path imagePath = pathToImage.toAbsolutePath();
        Path imagePath90Degree = pathToImage90Degree.toAbsolutePath();

        Mat img = Imgcodecs.imread(imagePath.toString());
        Mat img2 = Imgcodecs.imread(imagePath90Degree.toString());

        if (img.empty() || img2.empty()) {
            System.out.println("Can't load an Images");
            return;
        }

        cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        cvtColor(img2, img2, Imgproc.COLOR_BGR2GRAY);
        //Finding key points
        MatOfKeyPoint kp_img = new MatOfKeyPoint();
        MatOfKeyPoint kp_img2 = new MatOfKeyPoint();

        ORB orb = ORB.create();
        orb.detect(img, kp_img);
        orb.detect(img2, kp_img2);

        Mat result = new Mat();
        Features2d.drawKeypoints(img, kp_img, result,
                CvUtils.COLOR_BLUE, 0);
        showImage(result, "Result");

        Mat result2 = new Mat();
        Features2d.drawKeypoints(img2, kp_img2, result2,
                CvUtils.COLOR_BLUE, 0);
        showImage(result2, "result2");
        //calculate descriptors
        Mat descriptors_img = new Mat();
        Mat descriptors_img2 = new Mat();
        orb.compute(img, kp_img, descriptors_img);
        orb.compute(img2, kp_img2, descriptors_img2);

        MatOfDMatch matches = new MatOfDMatch();
        DescriptorMatcher dm = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        dm.match(descriptors_img, descriptors_img2, matches);
        //calculate min and max
        double max_dist = Double.MIN_VALUE;
        double min_dist = Double.MAX_VALUE;
        float dist = 0;
        List<DMatch> list = matches.toList();
        for (int i = 0; i < list.size(); i++) {
            dist = list.get(i).distance;
            if (dist == 0) continue;
            if (dist < min_dist) min_dist = dist;
            if (dist > max_dist) max_dist = dist;
        }
        System.out.println("min = " + min_dist + " max = " + max_dist);
        //finding best matches
        LinkedList<DMatch> dMatches = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).distance < min_dist * 3) {
                dMatches.add(list.get(i));
            }
        }
        System.out.println(dMatches.size());
        MatOfDMatch mat_good = new MatOfDMatch();
        mat_good.fromList(dMatches);
        //draw result
        Mat outImg = new Mat(img.rows() + img2.rows() + 10,
                img.cols() + img.cols() + 10,
                CvType.CV_8UC3, CvUtils.COLOR_BLACK);
        Features2d.drawMatches(img, kp_img, img2, kp_img2, mat_good, outImg,
                new Scalar(0, 100, 25), Scalar.all(-1), new MatOfByte(),
                Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
        CvUtils.showImage(outImg, "Matches result");

        img.release();
        img2.release();
        kp_img.release();
        kp_img2.release();
        descriptors_img.release();
        descriptors_img2.release();
        matches.release();
        mat_good.release();
        result.release();
        result2.release();
        outImg.release();
    }
}
