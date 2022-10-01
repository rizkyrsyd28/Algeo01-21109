import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import src.libTubes.*;
import java.util.*;

public class ImageResize extends Bicubic{
    
    
    public static BufferedImage imageResize(BufferedImage img) {
        BufferedImage paddedImg = null;
        BufferedImage resultImg = null;
        
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        WritableRaster imgRaster = img.getRaster();
        
        double[] imgPixels = new double[imgWidth * imgHeight];
        double[] imgPixelsLeft = new double[imgHeight];
        double[] imgPixelsUp = new double[imgWidth];
        double[] imgPixelsDown = new double[imgWidth];
        double[] imgPixelsRight = new double[imgHeight];
        
        imgPixels = imgRaster.getPixels(0, 0, imgWidth, imgHeight, imgPixels);
        
        imgPixelsLeft = imgRaster.getPixels(0, 0, 1, imgHeight, imgPixelsLeft);
        
        imgPixelsRight = imgRaster.getPixels(imgWidth - 1, 0, 1, imgHeight, imgPixelsRight);
        
        imgPixelsUp = imgRaster.getPixels(0, 0, imgWidth, 1, imgPixelsUp);
        
        imgPixelsDown = imgRaster.getPixels(0, imgHeight - 1, imgWidth, 1, imgPixelsDown);

        
        paddedImg = new BufferedImage(imgWidth + 2, imgHeight + 2, BufferedImage.TYPE_BYTE_GRAY);
        
        int paddedImgWidth = paddedImg.getWidth();
        int paddedImgHeight = paddedImg.getHeight();
        
        WritableRaster paddedImgRaster = paddedImg.getRaster();
        
        paddedImgRaster.setPixels(1, 1, imgWidth, imgHeight, imgPixels);
        
        paddedImgRaster.setPixels(0, 1, 1, imgHeight, imgPixelsLeft);
        
        paddedImgRaster.setPixels(paddedImgWidth - 1, 1, 1, imgHeight, imgPixelsRight);
        
        paddedImgRaster.setPixels(1, 0, imgWidth, 1, imgPixelsUp);
        
        paddedImgRaster.setPixels(1, paddedImgHeight - 1, imgWidth, 1, imgPixelsDown);

        resultImg = new BufferedImage(imgWidth * 2 - 1, imgHeight * 2 - 1, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster resultImgRaster = resultImg.getRaster();

        libTubes.Matrix m = new libTubes.Matrix(4, 4);
        double[] subImg = new double[16];
        double[] resultBicubic = new double[9];
        double total_process = (imgWidth - 1) * (imgHeight - 1);
        double percent = 0;
        for (int i = 0; i < imgHeight - 1; i++) {
            for (int j = 0; j < imgWidth - 1; j++) {
                System.out.print(String.format("%.2f", (percent / (total_process - 1)) * 100));
                percent++;
                System.out.println("%...");
                subImg = paddedImgRaster.getPixels(j, i, 4, 4, subImg);
                int idxSub = 0;
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        m.setELMT(k, l, subImg[idxSub]);
                        idxSub++;
                    }
                }
                double a = 0, b = 0;
                libTubes.Matrix a_koef = bicubicInterpolationKoef(m);
                for (int k = 0; k < 9; k++) {
                    resultBicubic[k] = bicubicInterpolationVal(a_koef, a, b);
                    if (resultBicubic[k] < 0) {
                        resultBicubic[k] = 0;
                    }

                    if (resultBicubic[k] > 255) {
                        resultBicubic[k] = 255;
                    }
                    
                    a += 0.5;
                    if (a > 1) {
                        b += 0.5;
                        a = 0;
                    }
                }

                resultImgRaster.setPixels(j * 2, i * 2, 3, 3, resultBicubic);
            }
        }

        return resultImg;
    }
    
    public static void driverImageResize() {
        System.out.print("\nMasukkan nama file gambar lengkap dengan extensionnnya (Ex: img.jpg)\n>> ");
        Scanner sc = new Scanner(System.in);
        String filename;
        filename = sc.nextLine();
        
        BufferedImage img = libTubes.IOFile.readImage(filename);
        while (img == null) {
            System.out.print("\nUlangi masukkan nama file\n>>");
            filename = sc.nextLine();
            img = libTubes.IOFile.readImage(filename);
        }

        BufferedImage resultImg = imageResize(img);

        String fileExtension;
        System.out.print("\nMasukkan nama file output gambar (hanya nama saja, tidak dengan extension)\n>> ");
        filename = sc.nextLine();
        System.out.print("\nMasukkan extension file output gambar (Ex: jpg, png, bmp)\n>> ");
        fileExtension = sc.nextLine();
        
        libTubes.IOFile.writeImageResize(resultImg, filename, fileExtension);
    }

}
