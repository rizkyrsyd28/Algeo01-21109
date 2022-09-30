import java.util.*;
import src.libTubes.SPL;
import src.libTubes.Matrix;
import src.libTubes.IOFile;
import src.libTubes.UI;

public class InterpolasiPolinom {

    public static String interpolPolinom(Matrix koordinat, double val){       // input matrix ukuran (n, 2)
        libTubes.Matrix augm = new libTubes.Matrix(koordinat.getLastIdxRow()+1, koordinat.getLastIdxRow()+2);
        libTubes.Matrix mHasil;
        double hasil=0;
        String fx = "f(x) =";
        String fval = "f("+val+") ="; 
        String sOut;


        for (int i = 0; i<=augm.getLastIdxRow(); i++) {
            for (int j = 0; j<=augm.getLastIdxCol()-1; j++) {
                //System.out.println(koordinat.getELMT(j, 0) + "^" + k);
                //System.out.println(Math.pow(koordinat.getELMT(j, 0), k));
                augm.setELMT(i, j, Math.pow(koordinat.getELMT(i, 0), j));
            }
        }

        for (int i = 0; i<=augm.getLastIdxRow(); i++) {
            augm.setELMT(i, augm.getLastIdxCol(), koordinat.getELMT(i, 1));
        }

        mHasil = SPL.gauss(augm);
        for (int i = 0; i<=mHasil.getLastIdxRow(); i++) {
            hasil += (Math.pow(val, i))*mHasil.getELMT(i, 0);
            if (i!=0) {
                if (mHasil.getELMT(i, 0)>=0) {
                    fx += " + " + mHasil.getELMT(i, 0) + "x^"+i;
                    fval += " + " + mHasil.getELMT(i, 0) +"*"+(Math.pow(val, i));
                }
                else {
                    fx += " - " + (-1)*mHasil.getELMT(i, 0) + "x^"+i;
                    fval += " - " + (-1)*mHasil.getELMT(i, 0) +"*"+(Math.pow(val, i));
                }
                
            }
            else {
                if (mHasil.getELMT(i, 0)>=0) {
                    fx+= " " + mHasil.getELMT(i, 0);
                    fval += " " + mHasil.getELMT(i, 0);
                }
                else {
                    fx+= " - " +  mHasil.getELMT(i, 0);
                    fval += " - " + mHasil.getELMT(i, 0);
                }
                
            }
        }

        sOut = fx + "\n" + fval + "\n" + "f("+val+") = "+hasil; 
        System.out.println(sOut);

        return sOut;
    }

    public static void driverInterpolPolinom(){
        int x;
        Scanner sc = new Scanner(System.in);
        boolean notValid = false;

        while (!notValid) {
            libTubes.Matrix koordinat;
            double val;
            String hasil;
            System.out.println("\nJenis input yang tersedia");
            System.out.println("    1. Terminal");
            System.out.println("    2. File txt");
            System.out.print("Pilih jenis input yang diinginkan: ");
            x = sc.nextInt();

            if (x == 1) {
                int n;

                System.out.print("\nMasukkan nilai n: ");
                n = sc.nextInt();
                koordinat = new libTubes.Matrix(n+1, 2);
                System.out.println("Masukkan koordinat dalam format \nx0 y0\nx1 y1\n..\ndst sampai n+1 kali\n");
                koordinat.readMatrix();
                sc = new Scanner(System.in);
                System.out.print("\nMasukkan titik yang ingin ditaksir nilainya: ");
                val = sc.nextDouble();

                hasil = SPL.interpolPolinom(koordinat, val); 
                System.out.print("\n");
                UI.simpan(hasil);
                notValid = true;
            }

            else if (x==2) {
                String fileName;
                int row=0, col=0;

                System.out.println("Format koordinat dalam file adalah \nx1 y1\nx2 y2\n..\ndst sampai n+1 kali");
                System.out.print("\nMasukkan nama file: ");

                sc = new Scanner(System.in);

                fileName = sc.nextLine();
                koordinat = IOFile.readFileMat("test/" + fileName + ".txt");

                if (koordinat != null) {
                    col = IOFile.getCol("test/" + fileName + ".txt");
                }

                while (koordinat == null | col!=2) {
                    if (koordinat != null) {
                        col = IOFile.getCol("test/" + fileName + ".txt");
                        if (col!=2) {
                            System.out.println("Tidak sesuai format !");
                        }
                    }

                    System.out.print("\nUlangi masukkan nama file: ");
                    fileName = sc.nextLine();
                    koordinat = IOFile.readFileMat("test/" + fileName + ".txt");
                    
                    if (koordinat != null) {
                        col = IOFile.getCol("test/" + fileName + ".txt");
                    }
                    
                }

                sc = new Scanner(System.in);
                System.out.print("\nMasukkan titik yang ingin ditaksir nilainya: ");
                val = sc.nextDouble();


                hasil = SPL.interpolPolinom(koordinat, val); 
                System.out.print("\n");
                UI.simpan(hasil);
                notValid = true;
            }
            else {
                System.out.println("Input tidak valid! Ulangi");
            }
        }
    }
}
