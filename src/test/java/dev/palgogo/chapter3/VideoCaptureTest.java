package dev.palgogo.chapter3;

import dev.palgogo.utils.CvUtils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class VideoCaptureTest {

    public static boolean isRun = true;
    public static boolean isEnd = true;

    @BeforeClass
    public void setUp() {
        System.load(Paths.get(".").toAbsolutePath().normalize().toString() + "\\libs\\opencv_java420.dll");
    }

    @Test
    public void checkVideo() {
        JFrame window = new JFrame("show video");
        window.setSize(1000, 1500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        JLabel label = new JLabel();
        window.setContentPane(label);
        window.setVisible(true);

        VideoCapture capture = new VideoCapture("D:\\film.mkv");

        if (!capture.isOpened()) {
            System.out.println("can't open the video");
            return;
        }

        Mat mat = new Mat();
        BufferedImage img = null;
        while (capture.read(mat)) {
            Imgproc.resize(mat, mat, new Size(960, 540));
            img = CvUtils.matToBufferedImage(mat);

            if (img != null) {
                ImageIcon imageIcon = new ImageIcon(img);
                label.setIcon(imageIcon);
                label.repaint();
                window.pack();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("Exit");
        capture.release();
    }

    @Test
    public void checkWebCam() {


        JFrame window = new JFrame("Click EXIT for disabling camera");
        window.setSize(640, 480);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setLocationRelativeTo(null);
        //handle exit button
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isRun = false;
                if (isEnd) {
                    window.dispose();
                    System.exit(0);
                } else {
                    System.out.println("Click Esc, then Close");
                }
            }
        });

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
        //to camera
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            window.setTitle("can't connect to camera");
            isRun = false;
            isEnd = true;
            return;
        }
        try {
            //set capture size
            camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
            camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
            //load capture
            Mat frame = new Mat();
            BufferedImage img = null;
            while (isRun) {
                if (camera.read(frame)) {
                    //code frame
                    img = CvUtils.matToBufferedImage(frame);
                    if (img != null) {
                        ImageIcon imageIcon = new ImageIcon(img);
                        label.setIcon(imageIcon);
                        label.repaint();
                        window.pack();
                    }
                    try {
                        Thread.sleep(33); //100 frames per seconds
                    } catch (InterruptedException e) {

                    }
                } else {
                    System.out.println("Can't handle capture frame");
                    break;
                }
            }
        } finally {
            camera.release();
            isRun = false;
            isEnd = true;
        }
        window.setTitle("Camera disabled");
    }
}
