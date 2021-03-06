package dev.palgogo.labs;

import dev.palgogo.utils.CvUtils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Seventh {


    private static final String UPPERBODY = "/haarcascades/haarcascade_upperbody.xml";
    private static final String FULL_BODY = "/haarcascades/haarcascade_fullbody.xml";
    private static final String PROFILEFACE = "/haarcascades/haarcascade_profileface.xml";
    private static final String FRONTAL_FACE = "/haarcascades/haarcascade_frontalface_alt2.xml";
    private static final String PEOPLES = "/haarcascades/case.xml";

    public void detectPeopleOnImage() {
        final Path pathToResources = Paths.get("src", "main", "resources");
        final Path resources = pathToResources.toAbsolutePath();

        CascadeClassifier face_detector = new CascadeClassifier();
        final boolean isLoadCascade = face_detector.load(resources + FULL_BODY);

        if (!isLoadCascade) {
            System.out.println("Can't load classificator " + FULL_BODY);
            return;
        }

        Path path = Paths.get("src", "main", "resources", "photos", "peoples3.jpg");
        Path fullResourcePath = path.toAbsolutePath();
        Mat frame = Imgcodecs.imread(fullResourcePath.toString());
        if (frame.empty()) {
            System.out.println("Can't load an image");
            return;
        }

        final MatOfRect faces = new MatOfRect();
        Imgproc.resize(frame, frame, new Size(), 0.6, 0.6, Imgproc.ADAPTIVE_THRESH_MEAN_C);

        //TODO should work with another parameters
        face_detector.detectMultiScale(frame, faces, 1.09, 1, 0);
        for (Rect r : faces.toList()) {
            Imgproc.rectangle(frame, new Point(r.x, r.y),
                    new Point(r.x + r.width, r.y + r.height),
                    CvUtils.COLOR_WHITE, 2);
        }

        CvUtils.showImage(frame, "Eis");
        frame.release();
    }
}
