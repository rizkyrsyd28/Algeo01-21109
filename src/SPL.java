import java.util.HashMap;
import java.util.*;
public class SPL extends Matrix {
    
    public SPL(int row, int col) {
        super(row, col);
    }

    public static void driverSPL() {
        boolean notValid = false;

        while (!notValid) {
            int x;
            System.out.println("\nMetode SPL yang tersedia\n");
            System.out.println("    1. Eliminasi Gauss");
            System.out.println("    2. Eliminasi Gauss-jordan");
            System.out.println("    3. Matrix Balikan");
            System.out.println("    4. Kaidah Cramer\n");
            Scanner sc = new Scanner(System.in);
            System.out.print("Pilih metode yang diinginkan: ");
            x = sc.nextInt();
            if (x == 1) {
                int m, n;
                Matrix a, b, augm, mHasil;
                System.out.print("\nMasukkan jumlah baris peubah m: ");
                m = sc.nextInt();
                System.out.print("Masukkan jumlah kolom peubah n: ");
                n = sc.nextInt();
    
                a = new Matrix(m, n);
                b = new Matrix(m, 1);
                System.out.print("\n");
    
                System.out.println("Masukkan value matrix peubah dengan ukuran m x n: ");
                a.readMatrixPeubah();
                System.out.print("\n");
                System.out.println("Masukkan value matrix hasil dengan ukuran m x 1: ");
                b.readMatrixHasil();
    
                augm = a.concatCol(b);
                mHasil = gauss(augm);
                System.out.println("\nHasil SPL:");
                System.out.println(SPL.displaySPL(mHasil)); 
                notValid = true;
            }
    
            else if (x==2) {
                int m, n;
                Matrix a, b, augm, mHasil;
                System.out.print("\nMasukkan jumlah baris peubah m: ");
                m = sc.nextInt();
                System.out.print("Masukkan jumlah kolom peubah n: ");
                n = sc.nextInt();
    
                //System.out.print("\n");
                a = new Matrix(m, n);
                b = new Matrix(m, 1);
                System.out.print("\n");
    
                System.out.println("Masukkan value matrix peubah dengan ukuran m x n: ");
                a.readMatrixPeubah();
                System.out.print("\n");
                System.out.println("Masukkan value matrix hasil dengan ukuran m x 1: ");
                b.readMatrixHasil();
    
                augm = a.concatCol(b);
                mHasil = gaussJordan(augm);
                System.out.println("\nHasil SPL:");
                System.out.println(SPL.displaySPL(mHasil));
                notValid = true; 
            }
            else if (x==3) {
                int n;
                Matrix a, b, augm, mHasil;
                System.out.print("\nMasukkan jumlah baris dan kolom peubah n: ");
                n = sc.nextInt();
    
                //System.out.print("\n");
                a = new Matrix(n, n);
                b = new Matrix(n, 1);
                System.out.print("\n");
    
                System.out.println("Masukkan value matrix peubah dengan ukuran n x n: ");
                a.readMatrixPeubah();
                System.out.print("\n");
                System.out.println("Masukkan value matrix hasil dengan ukuran n x 1: ");
                b.readMatrixHasil();
    
                augm = a.concatCol(b);
                mHasil = inverseSPL(augm);
                System.out.println("\nHasil SPL:");
                System.out.println(SPL.displaySPL(mHasil)); 
                notValid = true;
            }
            else if (x==4) {
                int n;
                Matrix a, b, augm, mHasil;
                System.out.print("\nMasukkan jumlah baris dan kolom peubah n: ");
                n = sc.nextInt();
    
                //System.out.print("\n");
                a = new Matrix(n, n);
                b = new Matrix(n, 1);
                System.out.print("\n");
    
                System.out.println("Masukkan value matrix peubah dengan ukuran n x n: ");
                a.readMatrixPeubah();
                System.out.print("\n");
                System.out.println("Masukkan value matrix hasil dengan ukuran n x 1: ");
                b.readMatrixHasil();
    
                augm = a.concatCol(b);
                mHasil = cramer(augm);
                System.out.println("\nHasil SPL:");
                System.out.println(SPL.displaySPL(mHasil)); 
                notValid = true;
            }            

            else {
                System.out.println("\n======Input tidak valid! Ulangi======");
            }
        }
    }

    public static Matrix inverseSPL(Matrix augm) {
        Matrix A = new Matrix(augm.row, augm.col - 1);
        Matrix b = new Matrix(augm.row, 1);

        for (int i = 0; i < A.row; i++) {
            for (int j = 0; j < A.col; j++) {
                A.setELMT(i, j, augm.getELMT(i, j));
            }
        }

        for (int i = 0; i < b.row; i++) {
            b.setELMT(i, 0, augm.getELMT(i, augm.col - 1));
        }

        Matrix invMat = A.inverseMatrix();
        if (invMat == null) {
            System.out.println("SPL ini memiliki solusi banyak (lebih dari satu) atau SPL tidak memiliki solusi karena tidak memiliki invers.");
            return null;
        } else {
            Matrix x = invMat.multiplyMatrix(b);
            return x;
        }
    }
    
    public static String displaySPL(Matrix m_sol) {
        String hasil = "";
        if (m_sol == null) {
            
            System.out.println("SPL tidak memiliki solusi.");
            return null;

        } else if (m_sol.col == 1) {
            
            for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                hasil += String.format("x_%d = %.3f\n", i, m_sol.getELMT(i, 0));
            }

        } else {

            HashMap<Integer, String> colType = new HashMap<Integer, String>();
            HashMap<Integer, Integer> idxColSatuUtama = new HashMap<Integer, Integer>();
            // Bisa tiga jenis nilai char, yaitu a, b, c.
            // a : kolom satu utama
            // b : kolom kosong (nilainya 0 semua)
            // a_idxPar : kolom dengan variabel yang dinyatakan dengan parametrik

            for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                idxColSatuUtama.put(i, m_sol.satuUtamaIdx(i));
            }
            
            int idxPar = 0;

            for (int j = 0; j < m_sol.getLastIdxCol(); j++) {
                boolean allZeroCol = true;
                for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                    if (j == idxColSatuUtama.get(i)) {
                        colType.put(j, "a");
                        break;
                    }

                    if (m_sol.getELMT(i, j) != 0) {
                        allZeroCol = false;
                    }

                    if (i == m_sol.getLastIdxRow()) {
                        if (allZeroCol) {
                            colType.put(j, "b");
                        } else {
                            colType.put(j, String.format("a_%d", idxPar));
                            idxPar++;
                        }
                    }
                }
            }
            
            
            for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                if (m_sol.satuUtamaIdx(i) < 0) {
                    continue;
                } else {
                    hasil += String.format("x_%d =", m_sol.satuUtamaIdx(i) + 1);
                    for (int j = m_sol.satuUtamaIdx(i) + 1; j <= m_sol.getLastIdxCol(); j++) {
                        if (j != m_sol.getLastIdxCol()) {
                            
                            if (m_sol.getELMT(i, j) == 0) {
                                continue;
                            } else {                            
                                if (m_sol.getELMT(i, j) > 0 && j != m_sol.satuUtamaIdx(i) + 1) {
                                    hasil += String.format(" + %.3f" + colType.get(j), m_sol.getELMT(i, j), j + 1);
                                } else if (m_sol.getELMT(i, j) < 0) {
                                    hasil += String.format(" - %.3f" + colType.get(j), -m_sol.getELMT(i, j), j + 1);
                                } else {
                                    hasil += String.format(" %.3f" + colType.get(j), m_sol.getELMT(i, j), j + 1);
                                }
                            }
                            
                        } else {
                            if (m_sol.getELMT(i, j) == 0) {
                                continue;
                            } else {                            
                                if (m_sol.getELMT(i, j) > 0 && j != m_sol.satuUtamaIdx(i) + 1) {
                                    hasil += String.format(" + %.3f", m_sol.getELMT(i, j), j + 1);
                                } else if (m_sol.getELMT(i, j) < 0) {
                                    hasil += String.format(" - %.3f", -m_sol.getELMT(i, j), j + 1);
                                } else {
                                    hasil += String.format(" %.3f", m_sol.getELMT(i, j), j + 1);
                                }
                            }
                        }                
                    }
                    hasil += "\n";
                }
            }

            for (int j = 0; j < m_sol.getLastIdxCol(); j++) {
                if (colType.get(j) != "a" && colType.get(j) != "b") {
                    hasil += String.format("x_%d = " + colType.get(j), j + 1);
                    hasil += "\n";
                }
            }
        }

        return hasil;
    }

    public static Matrix cramer(Matrix augm) {          // Driver checked
        Matrix A = new Matrix(augm.row, augm.col - 1);
        Matrix b = new Matrix(augm.row, 1);

        for (int i = 0; i < A.row; i++) {
            for (int j = 0; j < A.col; j++) {
                A.setELMT(i, j, augm.getELMT(i, j));
            }
        }

        for (int i = 0; i < b.row; i++) {
            b.setELMT(i, 0, augm.getELMT(i, augm.col - 1));
        }

        float x = A.determinantOBE();
        float hasil;
        Matrix m2;
        Matrix mHasil = new Matrix(A.col, 1);
        //mHasil.displayMatrix();
        //System.out.println(x);

        if (A.isSquare()) {           // Jika berbentuk kotak
            for (int i = 0; i<=A.getLastIdxCol(); i++) {
                m2 = A.switchCol(b, i);
                //m2.displayMatrix();
                hasil = m2.determinantOBE()/x;
                //System.out.println(i);
                //System.out.println(m2.determinantOBE() + " / " + x + "-> x"+(i+1)+" : "+hasil);
                mHasil.setELMT(i, 0, hasil);
            }
        }
        return mHasil;

    }

    public static void makeSatuUtama(Matrix augm) {       // Bikin bentuk matrix jd punya satu utama di semua baris
        int i = 0, j = 0; 
        while (i < augm.row && j < augm.col){
            if (augm.getELMT(i, j) == 0){
                boolean cp = false;
                for (int k = i + 1; k < augm.row; k++){
                    if (augm.getELMT(k, j) != 0){
                        float[] temp = augm.data[i];
                        augm.data[i] = augm.data[k];
                        augm.data[k] = temp;
                        cp = true;
                        break;
                    }
                }
                if (augm.rowZero(i) && i != augm.getLastIdxRow()){
                    float[] temp = augm.data[i];
                    augm.data[i] = augm.data[i+1];
                    augm.data[i+1] = temp;
                }
                if (!cp){
                    j++;
                }
                // this.displayMatrix();
            }
            // safety  breaker
            if (i >= augm.row || j >= augm.col){
                break;
            }

            if (augm.getELMT(i, j) != 0){
                float ratio = 1;
                if (augm.getELMT(i, j) != 1){
                    ratio = augm.getELMT(i, j);
                    for (int k = j; k < augm.col; k++){
                        augm.setELMT(i, k, augm.getELMT(i, k)/ratio);
                    }
                }
                ratio = 1;
                for (int k = i + 1; k < augm.row; k++){
                    ratio = augm.getELMT(k, j)/augm.getELMT(i, j);
                    for (int l = j; l < augm.col; l++){
                        augm.setELMT(k, l, augm.getELMT(k, l) - ratio*augm.getELMT(i, l));
                    }
                }
                i++;
                j++;
                // this.displayMatrix();
            }
        }
    }

    public static Matrix gauss(Matrix augm){          
        makeSatuUtama(augm);
        Matrix mHasil = new Matrix(augm.col-1, 1);      // Inisialisasi output matrix hasil
        // augm.displayMatrix();
        if (augm.isUniqueSolution()) {
            
            float hasil;

            for (int i = augm.getLastIdxRow(); i >= 0; i--) {
                hasil = augm.getELMT(i, augm.getLastIdxCol());
                for (int j = i + 1; j <= augm.getLastIdxCol()-1 ; j++) {
                    //mHasil.displayMatrix();
                    //System.out.println(augm.getLastIdxRow() + " - " + i);
                    //System.out.println(hasil + " - " + augm.getELMT(i, j) + " * " + mHasil.getELMT(mHasil.getLastIdxRow() - i,0));
                    hasil -= augm.getELMT(i, j)*mHasil.getELMT(j,0);
                    //System.out.println(i +" " + j);
                }
                //System.out.println(i);
                //System.out.println(" ======== " + hasil + " ======== ");
                mHasil.setELMT(i, 0, hasil);
            }

            return mHasil;

        } else if (augm.isParametricSolution()) {

            augm = SolFormatting(augm);
            return augm;

        } else {
            return null;
        }
    }   



    public static Matrix gaussJordan(Matrix augm){
        float pengali = 1; 
        Matrix mHasil = new Matrix(augm.col-1, 1);          // Inisialisasi output matrix hasil    

        makeSatuUtama(augm);
        for (int i = 0; i < augm.row - 1; i++){
            for (int k = i+1; k < augm.row; k++){
                if (augm.satuUtamaIdx(k)!=-1) {
                    pengali = augm.getELMT(i, augm.satuUtamaIdx(k))/augm.getELMT(k, augm.satuUtamaIdx(k));
                    for (int j = augm.satuUtamaIdx(k); j < augm.col; j++){
                        augm.setELMT(i, j, augm.getELMT(i, j) - pengali * augm.getELMT(k, j));
                    }
                }
            }
        }
        
        if (augm.isUniqueSolution()) {
            for (int i = 0; i < mHasil.row; i++) {
                mHasil.setELMT(i, 0, augm.getELMT(i, augm.getLastIdxCol()));
            }
            return mHasil;
        } else if (augm.isParametricSolution()) {
            augm = SolFormatting(augm);
            return augm;
        } else {
            return null;
        }
    }


    public static Matrix SolFormatting(Matrix m_sol) {

        for (int i = m_sol.getLastIdxRow(); i >= 0; i--) {
            // boolean foundUtama = false;
            
            if (m_sol.satuUtamaIdx(i) < 0) {    // Tidak ada satu utama di baris tersebut
                continue;
            } else {
                for (int j = m_sol.satuUtamaIdx(i) + 1; j <= m_sol.getLastIdxCol(); j++) {
                    if (j != m_sol.getLastIdxCol()) {
                        m_sol.setELMT(i, j, m_sol.getELMT(i, j) * -1);
                    }
                }
            }
        }
        for (int i = m_sol.getLastIdxRow(); i >= 0; i--) {
            // boolean foundUtama = false;
            
            if (m_sol.satuUtamaIdx(i) < 0) {    // Tidak ada satu utama di baris tersebut
                continue;
            } else {
                for (int j = i - 1; j >= 0; j--) {
                    for (int k = m_sol.satuUtamaIdx(i) + 1; k <= m_sol.getLastIdxCol(); k++) {
                        // m_sol.displayMatrix(); System.out.println();
                        m_sol.setELMT(j, k, m_sol.getELMT(i, k) * m_sol.getELMT(j, m_sol.satuUtamaIdx(i)) + m_sol.getELMT(j, k));
                    }
                    m_sol.setELMT(j, m_sol.satuUtamaIdx(i), 0);
                } 
            }
        }
        return m_sol;
    }

    public static float interpolPolinom(Matrix koordinat, float val){       // input matrix ukuran (n, 2)
        Matrix augm = new Matrix(koordinat.row, koordinat.row + 1);
        Matrix mHasil;
        float hasil=0;

        for (int i = 0; i<=augm.getLastIdxRow(); i++) {
            for (int j = 0; j<=augm.getLastIdxCol()-1; j++) {
                //System.out.println(koordinat.getELMT(j, 0) + "^" + k);
                //System.out.println(Math.pow(koordinat.getELMT(j, 0), k));
                augm.setELMT(i, j, (float) Math.pow(koordinat.getELMT(i, 0), j));
            }
        }

        for (int i = 0; i<=augm.getLastIdxRow(); i++) {
            augm.setELMT(i, augm.getLastIdxCol(), koordinat.getELMT(i, 1));
        }

        mHasil = gauss(augm);
        for (int i = 0; i<=mHasil.getLastIdxRow(); i++) {
            hasil += ((float) Math.pow(val, i))*mHasil.getELMT(i, 0);
        }

        return hasil;

    }


}


