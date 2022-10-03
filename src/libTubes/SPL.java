package libTubes;
import java.util.HashMap;
import java.util.*;
public class SPL extends Matrix {
    
    // Konstruktor
    public SPL(int row, int col) {
        super(row, col);
    }

    // Prosedur untuk membentuk matrix segitiga bawah
    public static void makeSatuUtama(Matrix augm) {
        int i = 0, j = 0; 
        while (i < augm.row && j < augm.col){
            if (augm.getELMT(i, j) == 0){
                boolean cp = false;
                for (int k = i + 1; k < augm.row; k++){
                    if (augm.getELMT(k, j) != 0){
                        double[] temp = augm.data[i];
                        augm.data[i] = augm.data[k];
                        augm.data[k] = temp;
                        cp = true;
                        break;
                    }
                }
                if (augm.rowZero(i) && i != augm.getLastIdxRow()){
                    double[] temp = augm.data[i];
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
                double ratio = 1;
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

    // Formatting untuk solusi parametrik
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

    // Fungsi yang menghasilkan solusi dari SPL dengan metode Gauss
    public static Matrix gauss(Matrix augm){
        makeSatuUtama(augm);

        // Inisialisasi matrix
        Matrix mHasil = new Matrix(augm.col-1, 1);
        
        // Percabangan Unique Solution, Parametric Solution, atau No Solution
        if (augm.isUniqueSolution()) {
            
            double hasil;
            for (int i = augm.getLastIdxCol() - 1; i >= 0; i--) {
                hasil = augm.getELMT(i, augm.getLastIdxCol());
                for (int j = i + 1; j <= augm.getLastIdxCol()-1 ; j++) {
                    hasil -= augm.getELMT(i, j)*mHasil.getELMT(j,0);
                }
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

    // Fungsi yang menghasilkan solusi dari SPL dengan metode Gauss
    public static Matrix gaussJordan(Matrix augm){
        
        // Inisialisasi matrix hasil
        Matrix mHasil = new Matrix(augm.col-1, 1);  
        
        double pengali = 1; 
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
        
        // Percabangan Unique Solution, Parametric Solution, atau No Solution
        if (augm.isUniqueSolution()) {
            for (int i = 0; i < mHasil.row; i++) {
                mHasil.setELMT(i, 0, augm.getELMT(i, augm.getLastIdxCol()));
            }
            return mHasil;
        } else if (augm.isNoSolution()) {
            return null;
        } else {
            augm = SolFormatting(augm);
            return augm;
        }
    }
    
    // Fungsi yang menghasilkan solusi dari SPL dengan metode Balikan
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
            return null;
        } else {
            Matrix x = invMat.multiplyMatrix(b);
            return x;
        }
    }
    
    // Fungsi yang menghasilkan solusi dari SPL dengan metode Cramer
    public static Matrix cramer(Matrix augm) {
        // Inisialisasi matrix A dan b
        Matrix A = new Matrix(augm.row, augm.col - 1);
        Matrix b = new Matrix(augm.row, 1);

        // Pengisian elemen matrix A
        for (int i = 0; i < A.row; i++) {
            for (int j = 0; j < A.col; j++) {
                A.setELMT(i, j, augm.getELMT(i, j));
            }
        }

        // Pengisian elemen matrix b
        for (int i = 0; i < b.row; i++) {
            b.setELMT(i, 0, augm.getELMT(i, augm.col - 1));
        }

        double x = A.determinantOBE();
        if (x == 0) {
            return null;
        }
        
        // Matrix hasil penukaran kolom
        Matrix m2;
        
        // Inisialisasi Matrix hasil
        Matrix mHasil = new Matrix(A.col, 1);
        
        double hasil;
        if (A.isSquare()) {
            for (int i = 0; i<=A.getLastIdxCol(); i++) {
                m2 = A.switchCol(b, i);
                hasil = m2.determinantOBE()/x;
                mHasil.setELMT(i, 0, hasil);
            }
        }
        return mHasil;

    }

    // Fungsi yang menghasilkan string dari hasil SPL
    public static String displaySPL(Matrix m_sol) {
        String hasil = "";
        if (m_sol == null) {    // String yang dihasilkan jika tidak ada solusi
            
            hasil += "SPL tidak memiliki solusi.";
            return hasil;

        } else if (m_sol.col == 1) {    // String yang dihasilkan jika diperoleh Unique Solution
            
            for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                hasil += String.format("x_%d = %.4f\n", i, m_sol.getELMT(i, 0));
            }

        } else {    // String yang dihasilkan jika diperoleh Parametric Solution

            // HashMap untuk penyimpanan index kolom dari satu utama tiap baris
            HashMap<Integer, Integer> idxColSatuUtama = new HashMap<Integer, Integer>();
            
            HashMap<Integer, String> colType = new HashMap<Integer, String>();
            // Jenis kolom dinyatakan dalam bentuk string, yaitu a, b, a_idxPar.
            // a : kolom satu utama
            // b : kolom kosong (nilainya 0 semua)
            // a_idxPar : kolom dengan variabel yang dinyatakan dengan parametrik

            // Penyimpanan index kolom satu utama
            for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                idxColSatuUtama.put(i, m_sol.satuUtamaIdx(i));
            }
            
            // Penyimpanan jenis dari tiap kolom di dalam HashMap
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
            
            // Penulisan string untuk solusi parametrik
            for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                if (m_sol.satuUtamaIdx(i) < 0) {
                    continue;
                } else {
                    hasil += String.format("x_%d =", m_sol.satuUtamaIdx(i) + 1);
                    boolean first = true;
                    for (int j = m_sol.satuUtamaIdx(i) + 1; j <= m_sol.getLastIdxCol(); j++) {
                        if (j != m_sol.getLastIdxCol()) {
                            
                            if (m_sol.getELMT(i, j) == 0) {
                                continue;
                            } else {                            
                                if (m_sol.getELMT(i, j) > 0 && !first) {
                                    hasil += String.format(" + %.4f" + colType.get(j), m_sol.getELMT(i, j), j + 1);
                                } else if (m_sol.getELMT(i, j) < 0) {
                                    hasil += String.format(" - %.4f" + colType.get(j), -m_sol.getELMT(i, j), j + 1);
                                } else {
                                    hasil += String.format(" %.4f" + colType.get(j), m_sol.getELMT(i, j), j + 1);
                                    first = false;
                                }
                            }
                            
                        } else {
                            if (m_sol.getELMT(i, j) == 0) {
                                continue;
                            } else {                            
                                if (m_sol.getELMT(i, j) > 0 && j != m_sol.satuUtamaIdx(i) + 1) {
                                    hasil += String.format(" + %.4f", m_sol.getELMT(i, j), j + 1);
                                } else if (m_sol.getELMT(i, j) < 0) {
                                    hasil += String.format(" - %.4f", -m_sol.getELMT(i, j), j + 1);
                                } else {
                                    hasil += String.format(" %.4f", m_sol.getELMT(i, j), j + 1);
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

    // Driver untuk penyelesaian SPL
    public static void driverSPL() {
        boolean notValid = false;

        while (!notValid) {
            int x;
            System.out.println("\nMetode SPL yang tersedia");
            System.out.println("    1. Eliminasi Gauss");
            System.out.println("    2. Eliminasi Gauss-jordan");
            System.out.println("    3. Matrix Balikan");
            System.out.println("    4. Kaidah Cramer");
            Scanner sc = new Scanner(System.in);
            System.out.print("Pilih metode yang diinginkan\n>> ");
            x = sc.nextInt();
            if (x == 1) {       //1. Eliminasi gauss
                boolean notValid2 = false;
                int x2;
                while (!notValid2) {
                    Matrix augm, mHasil;

                    sc = new Scanner(System.in);
                    System.out.println("\nJenis input yang tersedia");
                    System.out.println("    1. Terminal");
                    System.out.println("    2. File txt");
                    System.out.print("Pilih jenis input yang diinginkan\n>> ");
                    x2 = sc.nextInt();
                    if (x2 == 1) {
                        int m, n;
                        Matrix a, b;
                        sc = new Scanner(System.in);

                        System.out.print("\nMasukkan jumlah baris peubah m\n>> ");
                        m = sc.nextInt();
                        System.out.print("Masukkan jumlah kolom peubah n\n>> ");
                        n = sc.nextInt();
            
                        a = new Matrix(m, n);
                        b = new Matrix(m, 1);
                        System.out.print("\n");
            
                        System.out.println("Masukkan value matrix peubah dengan ukuran m x n\n>> ");
                        a.readMatrixPeubah();
                        System.out.print("\n");
                        System.out.println("Masukkan value matrix hasil dengan ukuran m x 1\n>> ");
                        b.readMatrixHasil();

                        // ======== jawaban-start==========//
                        augm = a.concatCol(b);
                        mHasil = gauss(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        System.out.println(SPL.displaySPL(mHasil));
                        UI.simpan(SPL.displaySPL(mHasil));

                        notValid2 = true;
                    }

                    else if (x2 == 2) {
                        sc = new Scanner(System.in);
                        String fileName;

                        System.out.print("\nMasukkan nama file\n>> ");
                        fileName = sc.nextLine();
                        augm = IOFile.readFileMat("test/" + fileName + ".txt");

                        while (augm == null) {
                            System.out.print("\nUlangi masukkan nama file\n>> ");
                            fileName = sc.nextLine();
                            augm = IOFile.readFileMat("test/" + fileName + ".txt");
                        }

                        // ======== jawaban-start==========//
                        mHasil = gauss(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        System.out.println(SPL.displaySPL(mHasil));
                        UI.simpan(SPL.displaySPL(mHasil));

                        notValid2 = true;
                    }
                    else {
                        System.out.println("Input tidak valid! Ulangi");
                    }
                }
                notValid = true;
            }
    
            else if (x==2) {       //2. Eliminasi gauss-jordan
                boolean notValid2 = false;
                int x2;
                while (!notValid2) {
                    Matrix augm, mHasil;

                    sc = new Scanner(System.in);
                    System.out.println("\nJenis input yang tersedia");
                    System.out.println("    1. Terminal");
                    System.out.println("    2. File txt");
                    System.out.print("Pilih jenis input yang diinginkan\n>> ");
                    x2 = sc.nextInt();
                    if (x2 == 1) {
                        int m, n;
                        Matrix a, b;
                        sc = new Scanner(System.in);

                        System.out.print("\nMasukkan jumlah baris peubah m\n>> ");
                        m = sc.nextInt();
                        System.out.print("Masukkan jumlah kolom peubah n\n>> ");
                        n = sc.nextInt();
            
                        a = new Matrix(m, n);
                        b = new Matrix(m, 1);
                        System.out.print("\n");
            
                        System.out.println("Masukkan value matrix peubah dengan ukuran m x n\n>> ");
                        a.readMatrixPeubah();
                        System.out.print("\n");
                        System.out.println("Masukkan value matrix hasil dengan ukuran m x 1\n>> ");
                        b.readMatrixHasil();

                        // ======== jawaban-start==========//
                        augm = a.concatCol(b);
                        mHasil = gaussJordan(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        System.out.println(SPL.displaySPL(mHasil));
                        UI.simpan(SPL.displaySPL(mHasil));

                        notValid2 = true;
                    }

                    else if (x2 == 2) {
                        sc = new Scanner(System.in);
                        String fileName;

                        System.out.print("\nMasukkan nama file\n>> ");
                        fileName = sc.nextLine();
                        augm = IOFile.readFileMat("test/" + fileName + ".txt");

                        while (augm == null) {
                            System.out.print("\nUlangi masukkan nama file\n>> ");
                            fileName = sc.nextLine();
                            augm = IOFile.readFileMat("test/" + fileName + ".txt");
                        }

                        // ======== jawaban-start==========//
                        mHasil = gaussJordan(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        System.out.println(SPL.displaySPL(mHasil));
                        UI.simpan(SPL.displaySPL(mHasil));

                        notValid2 = true;
                    }
                    else {
                        System.out.println("Input tidak valid! Ulangi");
                    }
                }
                notValid = true; 
            }
            else if (x==3) {       //3. Matrix balikan
                boolean notValid2 = false;
                int x2;
                while (!notValid2) {
                    Matrix augm, mHasil;

                    sc = new Scanner(System.in);
                    System.out.println("\nJenis input yang tersedia");
                    System.out.println("    1. Terminal");
                    System.out.println("    2. File txt");
                    System.out.print("Pilih jenis input yang diinginkan\n>> ");
                    x2 = sc.nextInt();
                    if (x2 == 1) {
                        int n;
                        Matrix a, b;
                        sc = new Scanner(System.in);

                        System.out.print("\nMasukkan jumlah baris dan kolom peubah n\n>> ");
                        n = sc.nextInt();
            
                        a = new Matrix(n, n);
                        b = new Matrix(n, 1);
                        System.out.print("\n");
            
                        System.out.println("Masukkan value matrix peubah dengan ukuran n x n\n>> ");
                        a.readMatrixPeubah();
                        System.out.print("\n");
                        System.out.println("Masukkan value matrix hasil dengan ukuran n x 1\n>> ");
                        b.readMatrixHasil();

                        // ======== jawaban-start==========//
                        augm = a.concatCol(b);
                        mHasil = inverseSPL(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        if (mHasil == null) {
                            String hasil = "SPL ini memiliki solusi banyak (lebih dari satu) atau SPL tidak memiliki solusi karena tidak memiliki invers.";
                            System.out.println(hasil);
                            UI.simpan(hasil);
                        } else {
                            System.out.println(SPL.displaySPL(mHasil));
                            UI.simpan(SPL.displaySPL(mHasil));
                        }

                        notValid2 = true;
                    }

                    else if (x2 == 2) {
                        sc = new Scanner(System.in);
                        String fileName;
                        int row=0, col=0;

                        System.out.print("\nMasukkan nama file\n>> ");
                        fileName = sc.nextLine();
                        augm = IOFile.readFileMat("test/" + fileName + ".txt");

                        if (augm != null) {
                            row = IOFile.getRow("test/" + fileName + ".txt");
                            col = IOFile.getCol("test/" + fileName + ".txt");
                        }
           
                        while (augm == null | col-1!=row) {
                            if (augm != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                                if (col-1!=row) {
                                    System.out.println("Bukan merupakan matrix dengan peubah n x n !");
                                }
                            }

                            System.out.print("\nUlangi masukkan nama file\n>> ");
                            fileName = sc.nextLine();
                            augm = IOFile.readFileMat("test/" + fileName + ".txt");
                            if (augm != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                            }
                        }

                        // ======== jawaban-start==========//
                        mHasil = inverseSPL(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        if (mHasil == null) {
                            String hasil = "SPL ini memiliki solusi banyak atau SPL tidak memiliki solusi karena tidak memiliki invers.";
                            System.out.println(hasil);
                            UI.simpan(hasil);
                        } else {
                            System.out.println(SPL.displaySPL(mHasil));
                            UI.simpan(SPL.displaySPL(mHasil));
                        }

                        notValid2 = true;
                    }
                    else {
                        System.out.println("Input tidak valid! Ulangi");
                    }
                }
                notValid = true; 
            }

            else if (x==4) {        // Kaidah cramer
                boolean notValid2 = false;
                int x2;
                while (!notValid2) {
                    Matrix augm, mHasil;

                    sc = new Scanner(System.in);
                    System.out.println("\nJenis input yang tersedia");
                    System.out.println("    1. Terminal");
                    System.out.println("    2. File txt");
                    System.out.print("Pilih jenis input yang diinginkan\n>> ");
                    x2 = sc.nextInt();
                    if (x2 == 1) {
                        int n;
                        Matrix a, b;
                        sc = new Scanner(System.in);

                        System.out.print("\nMasukkan jumlah baris dan kolom peubah n\n>> ");
                        n = sc.nextInt();
            
                        a = new Matrix(n, n);
                        b = new Matrix(n, 1);
                        System.out.print("\n");
            
                        System.out.println("Masukkan value matrix peubah dengan ukuran n x n\n>> ");
                        a.readMatrixPeubah();
                        System.out.print("\n");
                        System.out.println("Masukkan value matrix hasil dengan ukuran n x 1\n>> ");
                        b.readMatrixHasil();

                        // ======== jawaban-start==========//
                        augm = a.concatCol(b);
                        mHasil = cramer(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        System.out.println(SPL.displaySPL(mHasil));
                        UI.simpan(SPL.displaySPL(mHasil));

                        notValid2 = true;
                    }

                    else if (x2 == 2) {
                        sc = new Scanner(System.in);
                        String fileName;
                        int row=0, col=0;

                        System.out.print("\nMasukkan nama file\n>> ");
                        fileName = sc.nextLine();
                        augm = IOFile.readFileMat("test/" + fileName + ".txt");

                        if (augm != null) {
                            row = IOFile.getRow("test/" + fileName + ".txt");
                            col = IOFile.getCol("test/" + fileName + ".txt");
                        }
           
                        while (augm == null | col-1!=row) {
                            if (augm != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                                if (col-1!=row) {
                                    System.out.println("Bukan merupakan matrix dengan peubah n x n !");
                                }
                            }

                            System.out.print("\nUlangi masukkan nama file\n>> ");
                            fileName = sc.nextLine();
                            augm = IOFile.readFileMat("test/" + fileName + ".txt");
                            if (augm != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                            }
                        }

                        // ======== jawaban-start==========//
                        mHasil = cramer(augm);
                        // ======== jawaban-end==========//
                        System.out.println("\nHasil SPL:");
                        if (mHasil == null) {
                            String hasil = "SPL memiliki solusi banyak atau tidak memiliki solusi karena determinan matrix sama dengan 0.";
                            System.out.println(hasil);
                            UI.simpan(hasil);
                        } else {
                            System.out.println(SPL.displaySPL(mHasil));
                            UI.simpan(SPL.displaySPL(mHasil));
                        }

                        notValid2 = true;
                    }
                    else {
                        System.out.println("Input tidak valid! Ulangi");
                    }
                }
                notValid = true; 
            }            

            else {
                System.out.println("\n======Input tidak valid! Ulangi======");
            }

        }
    }

}


