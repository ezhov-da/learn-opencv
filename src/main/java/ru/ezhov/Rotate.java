package ru.ezhov;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Rotate {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        rotate2();
    }

    private void rotate1() {
        Mat img = Imgcodecs.imread("images\\djatel.jpg");
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2BGRA);
        double angle = 90;
        Mat m = Imgproc.getRotationMatrix2D(
                new Point(img.width() / 2, img.height() / 2), angle, 1
        );
        Rect rect = new RotatedRect(
                new Point(img.width() / 2, img.height() / 2),
                new Size(img.width() / 2, img.height()), angle).boundingRect();
        double[] arrX = m.get(0, 2);
        double[] arrY = m.get(1, 2);
        arrX[0] -= rect.x;
        arrY[0] -= rect.y;
        m.put(0, 2, arrX);
        m.put(1, 2, arrY);
        Mat img2 = new Mat();
        Imgproc.warpAffine(
                img, img2, m, rect.size(),
                Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT,
                new Scalar(255, 255, 255, 255));
        CvUtils.showImage(img2, "rotate");
        img.release();
        m.release();
        img2.release();
    }

    private static void rotate2() {
        Mat img = Imgcodecs.imread("images\\djatel.jpg");
        Mat img2 = new Mat();
        Core.rotate(img, img2, Core.ROTATE_90_CLOCKWISE);
        CvUtils.showImage(img2, "rotate");
        img.release();
        img2.release();
    }
}
