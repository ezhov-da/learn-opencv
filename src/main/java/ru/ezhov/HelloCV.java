package ru.ezhov;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class HelloCV {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        try {
//            readOpenCV();
//            readJava();
//            readFromByteImageIO(500);
            readFromByteOpenCV(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void version() {
        System.out.println("VERSION " + Core.VERSION);
        System.out.println("VERSION_MAJOR " + Core.VERSION_MAJOR);
        System.out.println("VERSION_MINOR " + Core.VERSION_MINOR);
        System.out.println("VERSION_REVISION " + Core.VERSION_REVISION);
        System.out.println("NATIVE_LIBRARY_NAME " + Core.NATIVE_LIBRARY_NAME);
        System.out.println("" + Core.getBuildInformation());
    }

    private static void readJava() throws Exception {
        System.out.println("==============================================================");
        System.out.println("readJava");
        long start = System.currentTimeMillis();

        BufferedImage image = ImageIO.read(new File("images/Planets_Earth_442854.jpg"));
        System.out.println(image.getWidth());
        System.out.println(image.getHeight());
        System.out.println(image.getColorModel().getColorSpace().getType());

        System.out.println((System.currentTimeMillis() - start));
    }

    private static void readOpenCV() throws Exception {
        System.out.println("==============================================================");
        System.out.println("readOpenCV");
        long start = System.currentTimeMillis();

        Mat img = Imgcodecs.imread("images/Planets_Earth_442854.jpg");
//        Mat img = Imgcodecs.imread("images/Фото_товара.jpg");
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
        } else {
            System.out.println(img.width());
            System.out.println(img.height());
            System.out.println(CvType.typeToString(img.type()));
            System.out.println(img.channels());
        }

        System.out.println((System.currentTimeMillis() - start));
    }

    private static void readFromByteImageIO(int iteration) throws Exception {
        try (InputStream is = new FileInputStream(new File("images/Planets_Earth_442854.jpg"))) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);

            long sum = 0;

            System.out.println("==============================================================");
            System.out.println("readFromByte ImageIO");
            for (int i = 0; i < iteration; i++) {
                long start = System.currentTimeMillis();
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
//                System.out.println(image.getWidth());
//                System.out.println(image.getHeight());
//                System.out.println(image.getColorModel().getColorSpace().getType());
                sum += System.currentTimeMillis() - start;
                System.out.print(i + ".");
            }
            System.out.println();
            System.out.println(sum / iteration);
        }
    }

    private static void readFromByteOpenCV(int iteration) throws Exception {
        try (InputStream is = new FileInputStream(new File("images/Planets_Earth_442854.jpg"))) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);

            long sum = 0;
            System.out.println("==============================================================");
            System.out.println("readFromByte OpenCV");
            for (int i = 0; i < iteration; i++) {
                long start = System.currentTimeMillis();
                //https://stackoverflow.com/questions/33493941/how-to-convert-byte-array-to-mat-object-in-java
                //https://stackoverflow.com/questions/21050499/memory-leak-from-iterating-opencv-frames
                Mat imageMat = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
//                System.out.println(imageMat.width());
//                System.out.println(imageMat.height());
//                System.out.println(CvType.typeToString(imageMat.type()));
//                System.out.println(imageMat.channels());
                imageMat.release();
                sum += System.currentTimeMillis() - start;
                System.out.print(i + ".");
            }
            System.out.println();
            System.out.println(sum / iteration);
        }
    }
}
