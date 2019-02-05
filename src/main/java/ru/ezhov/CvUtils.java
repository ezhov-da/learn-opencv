package ru.ezhov;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class CvUtils {

    public static BufferedImage matToBufferedImage(Mat mat) {
        if (mat == null || mat.empty()) return null;
        if (mat.depth() == CvType.CV_8U) {
        } else if (mat.depth() == CvType.CV_16U) {
            Mat m16 = new Mat();
            mat.convertTo(m16, CvType.CV_8U, 255.0 / 65535);
            mat = m16;

        } else if (mat.depth() == CvType.CV_32F) {
            Mat m32 = new Mat();
            mat.convertTo(m32, CvType.CV_8U, 255);
            mat = m32;
        } else {
            return null;
        }
        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;

        } else if (mat.channels() == 4) {
            type = BufferedImage.TYPE_4BYTE_ABGR;
        } else {
            return null;
        }
        byte[] buf = new byte[mat.channels() * mat.cols() * mat.rows()];
        mat.get(0, 0, buf);
        byte tmp = 0;
        if (mat.channels() == 4) {
            for (int i = 0; i < buf.length; i += 4) {
                tmp = buf[i + 3];
                buf[i + 3] = buf[i + 3];
                buf[i + 2] = buf[i + 1];
                buf[i + 1] = buf[i];
                buf[i] = tmp;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buf, 0, data, 0, buf.length);
        return image;
    }

    public static void showImage(Mat mat, String title) {
        BufferedImage image = matToBufferedImage(mat);
        if (image == null) return;
        JFrame frame = new JFrame(title);
        int w = 1000;
        int h = 600;
        frame.setSize(w, h);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel label = new JLabel(imageIcon);
        JScrollPane scrollPane = new JScrollPane(label);
        frame.setContentPane(scrollPane);
        if (image.getWidth() < w && image.getHeight() < h) {
            frame.pack();
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
