package main.java.dev.palgogo.chapter3;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Chapter3 {

    Mat mat;
    public void imgLoading() {
        mat = Imgcodecs.imread("C:\\Users\\moysy\\Pictures\\meXS.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        if (mat.empty()) {
            System.out.println("Can't load an image");
            return;
        }
        System.out.println(mat.width());
        System.out.println(mat.height());
        System.out.println(mat.channels());
        System.out.println(CvType.typeToString(mat.type()));
    }

    public void imgLoadingAndWriting() {
        imgLoading();
        boolean st = Imgcodecs.imwrite("C:\\Users\\moysy\\Pictures\\meXS.png", mat);
        if (!st) {
            System.out.println("Can't save an image");
        }
    }
}
