import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import src.libTubes.*;
import java.util.*;

public class ImageResize extends Bicubic{
    
    // Fungsi untuk pembesaran citra dengan ukuran n menjadi 2n-1
    public static BufferedImage imageResize(BufferedImage img) {
        // Inisialisasi variabel gambar
        BufferedImage paddedImg = null;
        BufferedImage resultImg = null;
        
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        // Raster dari gambar awal
        WritableRaster imgRaster = img.getRaster();
        
        // Inisialisasi array untuk menyimpan nilai pixel gambar
        double[] imgPixels = new double[imgWidth * imgHeight];
        double[] imgPixelsLeft = new double[imgHeight];
        double[] imgPixelsUp = new double[imgWidth];
        double[] imgPixelsDown = new double[imgWidth];
        double[] imgPixelsRight = new double[imgHeight];
        double[] imgPixelUpLeftCorner = new double[1];
        double[] imgPixelUpRightCorner = new double[1];
        double[] imgPixelDownLeftCorner = new double[1];
        double[] imgPixelDownRightCorner = new double[1];

        
        // Nilai seluruh pixel gambar
        imgPixels = imgRaster.getPixels(0, 0, imgWidth, imgHeight, imgPixels);
        
        // Nilai pixel gambar di sisi paling kiri
        imgPixelsLeft = imgRaster.getPixels(0, 0, 1, imgHeight, imgPixelsLeft);
        
        // Nilai pixel gambar di sisi paling kanan
        imgPixelsRight = imgRaster.getPixels(imgWidth - 1, 0, 1, imgHeight, imgPixelsRight);
        
        // Nilai pixel gambar di sisi paling atas
        imgPixelsUp = imgRaster.getPixels(0, 0, imgWidth, 1, imgPixelsUp);
        
        // Nilai pixel gambar di sisi paling bawah
        imgPixelsDown = imgRaster.getPixels(0, imgHeight - 1, imgWidth, 1, imgPixelsDown);

        // Nilai pixel ujung2 gambar
        imgPixelUpLeftCorner = imgRaster.getPixel(0, 0, imgPixelUpLeftCorner);
        imgPixelUpRightCorner = imgRaster.getPixel(imgWidth - 1, 0, imgPixelUpRightCorner);
        imgPixelDownLeftCorner = imgRaster.getPixel(0, imgHeight - 1, imgPixelDownLeftCorner);
        imgPixelDownRightCorner = imgRaster.getPixel(imgWidth - 1, imgHeight - 1, imgPixelDownRightCorner);

        // Inisialisasi gambar yang akan di-padding
        paddedImg = new BufferedImage(imgWidth + 2, imgHeight + 2, BufferedImage.TYPE_BYTE_GRAY);
        
        int paddedImgWidth = paddedImg.getWidth();
        int paddedImgHeight = paddedImg.getHeight();
        
        // Raster dari padded image
        WritableRaster paddedImgRaster = paddedImg.getRaster();
        
        // Set pixel pada sisi-sisi padded image
        paddedImgRaster.setPixels(1, 1, imgWidth, imgHeight, imgPixels);
        
        paddedImgRaster.setPixels(0, 1, 1, imgHeight, imgPixelsLeft);
        
        paddedImgRaster.setPixels(paddedImgWidth - 1, 1, 1, imgHeight, imgPixelsRight);
        
        paddedImgRaster.setPixels(1, 0, imgWidth, 1, imgPixelsUp);
        
        paddedImgRaster.setPixels(1, paddedImgHeight - 1, imgWidth, 1, imgPixelsDown);

        // Set pixel pada ujung-ujung padded image
        paddedImgRaster.setPixel(0, 0, imgPixelUpLeftCorner);
        
        paddedImgRaster.setPixel(paddedImgWidth - 1, 0, imgPixelUpRightCorner);
        
        paddedImgRaster.setPixel(0, paddedImgHeight - 1, imgPixelDownLeftCorner);
        
        paddedImgRaster.setPixel(paddedImgWidth - 1, paddedImgHeight - 1, imgPixelDownRightCorner);

        // Inisialisasi variabel gambar hasil
        resultImg = new BufferedImage(imgWidth * 2 - 1, imgHeight * 2 - 1, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster resultImgRaster = resultImg.getRaster();

        libTubes.Matrix m = new libTubes.Matrix(4, 4);
        double[] subImg = new double[16];
        double[] resultBicubic = new double[9];
        double total_process = (imgWidth - 1) * (imgHeight - 1);
        double percent = 0;
        for (int i = 0; i < imgHeight - 1; i++) {
            for (int j = 0; j < imgWidth - 1; j++) {
                // Persentase proses
                System.out.print(String.format("%.2f", (percent / (total_process - 1)) * 100));
                percent++;
                System.out.println("%...");
                
                // Pengambilan sub image 4x4 untuk proses interpolasi
                subImg = paddedImgRaster.getPixels(j, i, 4, 4, subImg);
                int idxSub = 0;
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        m.setELMT(k, l, subImg[idxSub]);
                        idxSub++;
                    }
                }
                
                // Proses interpolasi
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

                // Set pixel gambar hasil
                resultImgRaster.setPixels(j * 2, i * 2, 3, 3, resultBicubic);
            }
        }

        return resultImg;
    }
    
    // Driver pembesaran citra
    public static void driverImageResize() {
        System.out.print("\nMasukkan nama file gambar lengkap dengan extensionnnya (Ex: img.jpg)\n>> ");
        Scanner sc = new Scanner(System.in);
        String filename;
        filename = sc.nextLine();
        
        BufferedImage img = libTubes.IOFile.readImage(filename);
        while (img == null) {
            System.out.print("\nUlangi masukkan nama file\n>> ");
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
