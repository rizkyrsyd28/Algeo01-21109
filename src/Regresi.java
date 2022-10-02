import java.util.*;
import libTubes.Matrix;
import src.libTubes.*;

public class Regresi {

    public static double[] getCoefRegresi(double[][] m){
        double[][] augMat = new double[m.length][m[1].length + 1];

        // tambah kolom di awal untuk koef b0, isinya 1 
        for (int i = 0; i < m.length; i++){
            augMat[i][0] = 1;    
        }

        // konkat matrix input 
        for (int i = 0; i < m.length; i++){
            for (int j = 1; j <= m[i].length; j++){
                augMat[i][j] = m[i][j-1];
            }
        }
        // normal estimation equation 
        double[][] NEE = new double[m[1].length][m[1].length + 1];

        for (int i = 0; i < m[i].length; i++){
            for (int j = 0; j <= m[i].length; j++){
                NEE[i][j] = 0;
                for (int k = 0; k < m.length; k++){
                    NEE[i][j] += augMat[k][i] * augMat[k][j];
                }
            }
        }

        libTubes.Matrix nee = new libTubes.Matrix(NEE.length, NEE[1].length);

        nee.data = NEE;
        
        System.out.println("\nBentuk Normal Estimation Equation untuk Regresi Linier Barganda :\n");
        nee.displayMatrix();
        
        SPL.gaussJordan(nee);
        

        double [] coef = new double[nee.getLastIdxRow()+1];

        for (int i = 0; i <= nee.getLastIdxRow(); i++){
            coef[i] = nee.getELMT(i, nee.getLastIdxCol());
        }

        return coef;
    }

    public static void MultiRegresi(){
        System.out.println("pilihan input\n    1. Input Terminal\n    2. Input File");
        int mode; 
        Matrix augm;
        mode = UI.Pilih(2);

        if (mode == 1){
            int n, m;

            System.out.print("Masukan peubah\n>> ");
            n = UI.sc.nextInt();             
            System.out.print("Masukan banyak persamaan\n>> ");
            m = UI.sc.nextInt();
            while (m <= n + 1){
                System.out.printf("Banyak persamaan harus lebih dari %d\n", n+1);
                System.out.print("Input ulang banyak persamaan\n>> ");
                m = UI.sc.nextInt();
            }
            System.out.printf("Masukkan %d Persamaan dalam bentuk matrix augmented\n\n", m);
            augm = new Matrix(m, n+1);
            augm.readMatrix();
        } else {
            String fileName; 

            System.out.print("Masukkan nama file\n>> ");
            fileName = UI.sc.next();

            while(!IOFile.isFileExist("test/" + fileName + ".txt")){
                System.out.println("Masukkan salah, silahkan masukkan ulang!");
                System.out.print("Masukkan nama file\n>> ");
                fileName = UI.sc.nextLine();
            }

            while(IOFile.getRow("test/" + fileName + ".txt") <= IOFile.getCol("test/" + fileName + ".txt")){
                Scanner scc = new Scanner(System.in);
                System.out.println("Ukuran matrix augmented tidak sesuai! pastikan kolom > baris\n");
                System.out.print("Masukkan nama file\n>> ");
                fileName = scc.nextLine();
            }
            // augm = new double[IOFile.getRow(fileName)][IOFile.getCol(fileName)];
            augm = IOFile.readFileMat("test/" + fileName + ".txt");
        }
        
        double[] coef = getCoefRegresi(augm.data);

        System.out.println("\nHasil persamaan Regresi Linier Berganda :\n");
        
        if (coef[0] == 0){
            System.out.printf("y = %fx1", coef[1]);
            for (int i = 2; i < coef.length; i++){
                if (coef[i] > 0){
                    System.out.printf(" + %f x%d", coef[i], i);
                } else if (coef[i] < 0){
                    System.out.printf(" %f x%d", coef[i], i);
                }
            }
        } else {
            System.out.printf("y = %f ", coef[0]);
            for (int i = 1; i < coef.length; i++){
                if (coef[i] > 0){
                    System.out.printf(" + %f x%d", coef[i], i);
                } else if (coef[i] < 0){
                    System.out.printf(" %f x%d", coef[i], i);
                }
            }
        }

        System.out.println("\n\nMenaksir nilai dari fungsi Reegresi Linier");
        System.out.printf("Masukkan %d peubah yang akan ditaksir nilai fungsinya\n>> ", coef.length - 1);

        double taksir = coef[0];
        
        double[] input = new double[coef.length-1];

        for (int i = 0; i < coef.length-1; i++){
            input[0]=  UI.sc.nextDouble();
        }
        for (int i = 0; i < input.length; i++){
            taksir += input[0] * coef[i+1];
        }

        System.out.printf("Nilai taksirannya adalah %f\n\n", taksir);
        
        String output = "Persamaan Regresi Linier yang diperoleh\n";

        if (coef[0] == 0){
            output += ("y = " + coef[1] + " x1");
            for (int i = 2; i < coef.length; i++){
                if (coef[i] > 0){
                    output += (" + " + coef[i] + "x" + i);
                } else if (coef[i] < 0){
                    output += (" - " + (-1 * coef[i]) + "x" + i);
                }
            }
        } else {
            output += ("y = " + coef[0]);
            for (int i = 1; i < coef.length; i++){
                if (coef[i] > 0){
                    output += (" + " + coef[i] + "x" + i);
                } else if (coef[i] < 0){
                    output += (" - " + (-1 * coef[i]) + "x" + i);
                }
            }
        }
        output += ("\nNilai taksirannya adalah " + taksir);
        UI.simpan(output);
    }
}
