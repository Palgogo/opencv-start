package dev.palgogo.labs;

import dev.palgogo.utils.CvUtils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Fourth {

    private static final String FRONTALFACE_CASCADE = "/haarcascades/haarcascade_frontalface_alt2.xml";

    static boolean isRun = true;
    static boolean isEnd = false;

    public void facesOnVideo() {
        final Path pathToResources = Paths.get("src", "main", "resources");
        final Path resources = pathToResources.toAbsolutePath();

        CascadeClassifier face_detector = new CascadeClassifier();
        final boolean isLoadCascade = face_detector.load(resources + FRONTALFACE_CASCADE);

        JFrame window = new JFrame("Esc for exit");
        window.setSize(1000, 1000);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);



        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    isRun = false;
                }
            }
        });

        JLabel label = new JLabel();
        window.setContentPane(label);
        window.setVisible(true);


        final MatOfRect faces = new MatOfRect();

        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            isRun = false;
            isEnd = true;
            return;
        }
        try {
            camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 1000);
            camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1000);

            //read frames
            Mat frame = new Mat();
            BufferedImage img = null;

            while (isRun) {
                if (camera.read(frame)) {

                    face_detector.detectMultiScale(frame, faces, 1.1, 2, 0, new Size(30, 30));
                    for (Rect r : faces.toList()) {
                        Imgproc.rectangle(frame, new Point(r.x, r.y),
                                new Point(r.x + r.width, r.y + r.height),
                                CvUtils.COLOR_WHITE, 2);
                    }

                    img = CvUtils.matToBufferedImage(frame);

                    if (img != null) {
                        ImageIcon imageIcon = new ImageIcon(img);
                        label.setIcon(imageIcon);
                        label.repaint();
                        window.pack();
                    }
                    try {
                        Thread.sleep(33);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    break;
                }
            }

        } finally {
            camera.release();
            isRun = false;
            isEnd = true;
        }

    }
}
