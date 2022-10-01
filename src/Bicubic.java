import java.lang.Math;
import java.util.*;
import src.libTubes.*;


public class Bicubic {

    public static double bicubicInterpolation(libTubes.Matrix m, double a, double b) {
        libTubes.Matrix X = new libTubes.Matrix(16, 16);
        libTubes.Matrix Y = new libTubes.Matrix(16, 1);
        
        int k = 0, l = 0, x = -1, y = -1;
        for (int i = 0; i <= X.getLastIdxRow(); i++) {
            for (int j = 0; j <= X.getLastIdxCol(); j++) {

               // Assignment of matrix X
                X.setELMT(i, j, (Math.pow(x, k) * Math.pow(y, l)));
                k += 1;
                if (k == 4) {
                    k = 0;
                    l += 1;
                }
                if (l == 4) {
                    l = 0;
                }

            }
            
            Y.setELMT(i, 0, m.getELMT(x+1, y+1));
            x += 1;
            if (x == 3) {
                x = -1;
                y += 1;
            }

        }
        
        // Coefficient calculation
        libTubes.Matrix augm = X.concatCol(Y);
        libTubes.Matrix a_koef = libTubes.SPL.gaussJordan(augm);

        double value_I = 0.0;
        k = 0; l = 0;
        for (int i = 0; i <= a_koef.getLastIdxRow(); i++) { 
            value_I += a_koef.getELMT(i, 0) * (Math.pow(a, k) * Math.pow(b, l));
            k += 1;
            if (k == 4) {
                k = 0;
                l += 1;
            }
        }
        return value_I;
    
    }

    public static void driverBicubic() {
        boolean notValid = false;
        int x;
        while (!notValid) {
            String sHasil = "";
            double interpolate_val, a, b;
            libTubes.Matrix m;
            Scanner sc = new Scanner(System.in);
            System.out.println("\nJenis input yang tersedia");
            System.out.println("    1. Terminal");
            System.out.println("    2. File txt");
            System.out.print("Pilih jenis input yang diinginkan: ");
            x = sc.nextInt();

            if (x == 1) {
                notValid = true;
                System.out.print("Masukkan tiap elemen dari matrix 4 x 4: \n");
                m = new libTubes.Matrix(4, 4);
                m.readMatrixPeubah();
                
                System.out.print("Masukkan koordinat (a, b) yang akan diinterpolasi (Interval nilai a dan b adalah [0,1]): \n");
                sc = new Scanner(System.in);
                System.out.print("a: ");
                a = sc.nextDouble();
                System.out.print("b: ");
                b = sc.nextDouble();
                
                interpolate_val = Bicubic.bicubicInterpolation(m, a, b);
                System.out.println();
                sHasil += String.format("Nilai f(%.2f, %.2f) = %.4f", a, b, interpolate_val);
                System.out.println(sHasil);
                UI.simpan(sHasil);

            } else if (x == 2) {
                notValid = true;
                sc = new Scanner(System.in);
                String fileName;

                System.out.print("\nMasukkan nama file: ");
                fileName = sc.nextLine();
                m = IOFile.readBcb("test/" + fileName + ".txt");
                double[] coor = IOFile.coorBcb("test/" + fileName + ".txt");

                while (m == null || coor == null || m.getLastIdxRow()+1 != 4 || m.getLastIdxCol()+1 != 4 || coor.length != 2) {
                    System.out.print("\nUlangi masukkan nama file: ");
                    fileName = sc.nextLine();
                    m = IOFile.readBcb("test/" + fileName + ".txt");
                    coor = IOFile.coorBcb("test/" + fileName + ".txt");
                }

                interpolate_val = Bicubic.bicubicInterpolation(m, coor[0], coor[1]);
                System.out.println();
                sHasil += String.format("Nilai f(%.2f, %.2f) = %.4f", coor[0], coor[1], interpolate_val);
                System.out.println(sHasil);
                UI.simpan(sHasil);
            } else {
                System.out.println("Input tidak valid! Ulangi.");
            }
        }
    }

}

