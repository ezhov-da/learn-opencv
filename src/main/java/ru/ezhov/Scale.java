package ru.ezhov;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Scale {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Imgcodecs.imread("images\\djatel.jpg");
        if (img.empty()) {
            System.out.println("УПС");
            return;
        }

        int w = img.width();
        int wP = w * 50 / 100;
        System.out.println(w);
        System.out.println(wP);
        int h = img.height();
        int hP = img.height() * 50 / 100;
        System.out.println(h);
        System.out.println(hP);

        Mat img2 = new Mat();

        Imgproc.resize(img, img2, new Size(wP, hP), Imgproc.INTER_NEAREST);
//        Imgproc.resize(img, img2, new Size(600, 600));

        CvUtils.showImage(img2, "scale");
        img.release();
        img2.release();
    }
}
