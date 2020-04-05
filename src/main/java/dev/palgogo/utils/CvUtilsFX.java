package dev.palgogo.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class CvUtilsFX {
    public static WritableImage matToWritableImage(Mat m) {
        BufferedImage bim = CvUtils.matToBufferedImage(m);
        if (bim == null) return null;
        else return SwingFXUtils.toFXImage(bim, null);
    }

    public static WritableImage matToImageFX(Mat m) {
        if (m == null || m.empty()) return null;
        if (m.depth() == CvType.CV_16U) {
            Mat m_16 = new Mat();
            m.convertTo(m_16, CvType.CV_8U, 255.0 / 65535);
            m = m_16;
        } else if (m.depth() == CvType.CV_32F) {
            Mat m_32 = new Mat();
            m.convertTo(m_32, CvType.CV_8U, 255);
            m = m_32;
        } else return null;

        if (m.channels() == 1) {
            Mat m_bgra = new Mat();
            Imgproc.cvtColor(m, m_bgra, Imgproc.COLOR_GRAY2BGRA);
            m = m_bgra;
        } else if (m.channels() == 3) {
            Mat m_bgra = new Mat();
            Imgproc.cvtColor(m, m_bgra, Imgproc.COLOR_BGR2BGRA);
            m = m_bgra;
        } else if (m.channels() == 4) {

        } else return null;

        byte[] buf = new byte[m.channels() * m.cols() * m.rows()];
        m.get(0, 0, buf);

        WritableImage wim = new WritableImage(m.cols(), m.rows());
        PixelWriter pw = wim.getPixelWriter();
        pw.setPixels(0, 0, m.cols(), m.rows(),
                WritablePixelFormat.getByteBgraPreInstance(),
                buf, 0, m.cols() * 4);

        return wim;
    }

    public static Mat imageFXToMat(Image img) {
        if (img == null) return new Mat();
        PixelReader pr = img.getPixelReader();
        int width = (int) img.getWidth();
        int height = (int) img.getHeight();
        byte[] buf = new byte[width * height * 4];

        pr.getPixels(0, 0, width, height, WritablePixelFormat.getByteBgraInstance(),
                buf, 0, width * 4);

        Mat m = new Mat(height, width, CvType.CV_8UC3);
        m.put(0, 0, buf);

        return m;
    }

    public static boolean saveMat(Mat m, String path) {
        if (m == null || m.empty()) return false;
        if (path == null || path.length() < 5 || !path.endsWith(".mat")) return false;
        if (m.depth() == CvType.CV_8U) {
        } else if (m.depth() == CvType.CV_16U) {
            Mat m_16 = new Mat();
            m.convertTo(m_16, CvType.CV_8U, 255.0 / 65535);
            m = m_16;
        } else if (m.depth() == CvType.CV_32F) {
            Mat m_32 = new Mat();
            m.convertTo(m_32, CvType.CV_8U, 255);
            m = m_32;
        } else return false;

        if (m.channels() == 2 || m.channels() > 4) return false;
        byte[] buf = new byte[m.channels() * m.cols() * m.rows()];
        m.get(0, 0, buf);

        try (
                OutputStream out = new FileOutputStream(path);
                BufferedOutputStream bout = new BufferedOutputStream(out);
                DataOutputStream dout = new DataOutputStream(bout);
        ) {
            dout.writeInt(m.rows());
            dout.writeInt(m.cols());
            dout.writeInt(m.channels());
            dout.write(buf);
            dout.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
