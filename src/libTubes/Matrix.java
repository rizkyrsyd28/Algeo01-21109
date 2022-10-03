package libTubes;
import java.util.*;

public class Matrix {
    
    // Deklarasi variabel baris, kolom, dan data matrix
    protected int row;
    protected int col;
    public double data[][];

    // Konstruktor
    public Matrix(int row, int col) {       
        this.row = row;
        this.col = col;
        this.data = new double[row][col];
    }

    /* *** SELEKTOR *** */
    public void setELMT(int i, int j, double val) {         
        this.data[i][j] = val;
    }

    public double getELMT(int i, int j) {               
        return this.data[i][j];
    }

    public double[][] getData(){
        return this.data;
    }

    
    /* *** Selektor: Untuk sebuah matriks m yang terdefinisi: *** */
    /* Mengirimkan Index baris terbesar m */
    public int getLastIdxRow()               
    {
        return this.row-1;
    }
    
    /* Mengirimkan Index kolom terbesar m */
    public int getLastIdxCol()                
    {
        return this.col-1;
    }
    
    /* Mengirimkan elemen m(i,i) */
    public double getElmtDiagonal(int i)           
    {
        return getELMT(i, i);
    }
    
    /* Mengirimkan true jika i, j adalah index yang valid untuk matriks apa pun */
    public boolean isMatrixIdxValid(int i, int j)                 
    {
        return ((i<=getLastIdxRow()) && (i>=0) && (j<=getLastIdxCol()) && (j>=0));
    }

    /* *** KELOMPOK BACA/TULIS *** */
    // Pembacaan Matrix
    public void readMatrix() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan value matrix: ");
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                setELMT(i, j, sc.nextDouble());
            }
        }
    }

    // Pembacaan Matrix Peubah
    public void readMatrixPeubah() {
        //input berupa a[i][j] 
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                System.out.print("a["+i+"]["+j+"]: ");
                setELMT(i, j, sc.nextDouble());
            }
        }
    }

    public void readMatrixHasil() {
        //input berupa b[i]
        // Kamus
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                System.out.print("b["+i+"]: ");
                setELMT(i, j, sc.nextDouble());
            }
        }

        //sc.close();
        
    }    

    // Menuliskan matrix ke layar, masing-masing elemen per baris dipisahkan sebuah spasi.
    public void displayMatrix() {
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                System.out.print(String.format("%.4f", getELMT(i, j)));
                if (j!=getLastIdxCol()) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");   
        }     
    }

    // Mengubah data matrix menjadi sebuah string dengan format yang sama seperti method displayMatrix()
    public String matrixToString() {
        String hasil=""; 
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                hasil += String.format("%.4f", getELMT(i, j));
                if (j!=getLastIdxCol()) {
                    hasil+=" ";
                }
            }
            hasil+="\n";   
        }     
        return hasil;
    }

    /* *** OPERASI LAIN *** */
    public int countElmt() {
    /* Mengirimkan banyaknya elemen m */
        return this.row*this.col;
    }

    /* ********** KELOMPOK TEST TERHADAP Matrix ********** */
    public boolean isSquare() {
    /* Mengirimkan true jika m adalah matriks dg ukuran baris dan kolom sama */
        return this.row == this.col;
    }

    /* ********** Assignment Matrix ********** */
    public Matrix copyMatrix() {
    /* Melakukan assignment mOut <- mIn */
        Matrix mOut = new Matrix(this.row, this.col);

        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                mOut.setELMT(i, j, this.getELMT(i, j)); 
            }
        }
        return mOut;
    }    

    /* *** LAIN-LAIN *** */
    // Mengembalikan matrix yang kolom dan baris tertentu sudah dihapus terlebih dahulu
    public Matrix subMatrix(int rowDel, int colDel){
        int rowSub, colSub; 

        Matrix subMat = new Matrix(this.row-1, this.col-1);

        rowSub = 0;
        for (int i = 0; i < this.row; i++){
            colSub = 0; 
            for (int j = 0; j < this.col; j++){
                if (i != rowDel && j != colDel){
                    subMat.setELMT(rowSub, colSub, this.getELMT(i, j));

                    colSub++;
                    if (colSub == this.col - 1){
                        rowSub++;
                        colSub = 0;
                    } 
                }
            }
        }
        return subMat;
    }
    
    // Menghitung determinan dengan metode Kofaktor
    public double determinanKof(){
        double det = 0; 
        int sign = 1; 
        
        if (this.row == 1){
            return this.data[0][0];
        }
        
        Matrix kof = new Matrix(this.row-1, this.col-1);
        
        for (int i = 0; i < this.row; i++){
            kof = this.subMatrix(0, i);
            det += (sign * this.data[0][i] * kof.determinanKof());

            sign = (-1) * sign;
        }

        return det;
        
    }

    // Menghitung determinan matrix dengan cara OBE
    public double determinantOBE() {
        // Kamus
        int rowSwitch = -1;       
        double pengali = 1, det = 1, tempdouble;
        Matrix mcopy = copyMatrix();

        // Algoritma
        if (!mcopy.isSquare()) {
            System.out.println("Harus matrix berbentuk persegi (ukuran n x n)! ");
            return Double.NaN;
        }

        else {
            if (mcopy.countElmt()==1) {      // Cek jika hanya 1 elemen
                return (double) mcopy.getELMT(0, 0);
            }    
            else {
                for (int j = 0; j<=mcopy.getLastIdxCol(); j++) {
                    for (int i = j+1; i<=mcopy.getLastIdxRow(); i++) {
                        if (mcopy.getELMT(j, j) == 0) {
                            for (int k = j+1; k<=mcopy.getLastIdxRow(); k++) {
                                if (mcopy.getELMT(k, j) != 0) {
                                    rowSwitch = k;
                                    break;
                                }
                            } 
                            if (rowSwitch == -1) {
                                return (double) 0;
                            }
                            else {
                                for (int k = 0; k<=mcopy.getLastIdxCol(); k++) {
                                    tempdouble = mcopy.getELMT(rowSwitch, k);
                                    mcopy.setELMT(rowSwitch, k, mcopy.getELMT(j, k));
                                    mcopy.setELMT(j, k, tempdouble);
                                }
                                rowSwitch = -1;
                                det *= -1;
                            }
                        }
    
                        pengali = mcopy.getELMT(i, j)/mcopy.getELMT(j, j);
                        for (int k = 0; k<=mcopy.getLastIdxRow(); k++) {
                            mcopy.setELMT(i, k, mcopy.getELMT(i, k) - pengali*mcopy.getELMT(j, k));
                        }
                    }
                } 
    
                //mcopy.displayMatrix();
                for (int i  = 0; i<=mcopy.getLastIdxCol(); i++) {
                    det*=mcopy.getELMT(i, i);
                }
                return det;          
            }            
            
        }
        
    }
    
    // Mengalikan setiap elemen matriks dengan suatu konstanta
    public void multiplyByConst(double f) {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                this.setELMT(i, j, this.getELMT(i, j) * f);
            }
        }
    }
    
    // Transpose matrix
    public void transposeMatrix() {
        double temp;

        for (int i = 1; i < this.row; i++) {
            for (int j = 0; j < i; j++) {
                temp = this.getELMT(i, j);
                this.setELMT(i, j, this.getELMT(j, i));
                this.setELMT(j, i, temp);
            }
        }
    }

    // Menghasilkan matrix adjoint dari suatu matrix
    public Matrix adjointMatrix() {
        Matrix adjMat = new Matrix(this.row, this.col);

        for (int i = 0; i < adjMat.row; i++) {
            for (int j = 0; j < adjMat.col; j++) {
                Matrix m_sub = this.subMatrix(i, j);

                if ((i + j) % 2 == 0) {
                    adjMat.setELMT(i, j, m_sub.determinantOBE());
                } else {
                    adjMat.setELMT(i, j, -m_sub.determinantOBE());
                }
            }
        }

        adjMat.transposeMatrix();
        return adjMat;
    }

    // Menghasilkan inverse dari suatu matrix
    public Matrix inverseMatrix() {
        Matrix invMat = new Matrix(this.row, this.col);

        double m_det = this.determinantOBE();
        if (m_det == 0) {
            System.out.println("Determinan matriks sama dengan nol. Sehingga matriks tidak mempunyai invers.");
            return null;
        } else {
            invMat = this.adjointMatrix();
            invMat.multiplyByConst(1 / m_det);
            return invMat;
        }
    }

    // Mengecek apakah seluruh elemen dari suatu baris adalah 0
    public boolean rowZero(int row){
        for (int j = 0; j < this.col; j++){
            if (getELMT(row, j) != 0){
                return false;
            }
        }
        return true;
    }

    // Mengembalikan nilai index kolom dari satu utama
    public int satuUtamaIdx(int row){

        for (int j = 0; j < this.col; j++){
            if (getELMT(row, j) != 0){
                return j;
            }
        }
        
        return -1;
    }
   
    // Switch suatu kolom dengan matrix tertentu yang memiliki ukuran (row, 1)
    public Matrix switchCol(Matrix mCol, int colIdx) {  
        Matrix mOut = copyMatrix();

        for (int i = 0; i<=mOut.getLastIdxRow(); i++) {
            mOut.setELMT(i, colIdx, mCol.getELMT(i, 0));
        }
        return mOut;
    }

    // Pengecekan solusi parametrik
    public boolean isParametricSolution(){
        if (!isNoSolution()) {
            if (this.row <  this.col-1){
                return true;
    
            } else {
                int countRowAllZero = 0;
                for (int i = this.getLastIdxRow(); i >= 0; i--) {
                    if (this.satuUtamaIdx(i) == -1) {
                        countRowAllZero++;
                    }
                    if (this.satuUtamaIdx(i) > 0) {
                        break;
                    }
                }
                return (this.row - countRowAllZero) < (this.col - 1);
            }
        } else {
            return false;
        }
    }

    // Pengecekan apakah ada solusi atau tidak
    public boolean isNoSolution(){
        for (int i = this.getLastIdxRow(); i >= 0; i--) {
            if (this.satuUtamaIdx(i) == this.getLastIdxCol()) {
                return true;
            }
            if (this.satuUtamaIdx(i) > 0) {
                break;
            }
        }
        return false;
    }

    // Pengecekan apakah solusinya Unique atau tidak
    public boolean isUniqueSolution(){
        return (!this.isParametricSolution() && !this.isNoSolution());
    }
    
    // Perkalian matrix
    // m1.multiplyMatrix(m2) == m1 * m2
    public Matrix multiplyMatrix(Matrix y) {
        Matrix m_mult = new Matrix(this.getLastIdxCol()+1, y.getLastIdxCol()+1);

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < y.getLastIdxCol()+1; j++) {

                for (int k = 0; k < this.col; k++) {
                    m_mult.data[i][j] += this.data[i][k] * y.data[k][j];
                }
            }
        }
        return m_mult;
    }

    // Menggabungkan dua matrix (secara kolom)
    public Matrix concatCol(Matrix m) {  // jumlah baris harus sama
        Matrix mOut = new Matrix(m.row, this.col + m.col);

        for (int i = 0; i<=this.getLastIdxRow(); i++){
            for (int j = 0; j<=this.getLastIdxCol(); j++) {
                mOut.setELMT(i, j, this.getELMT(i, j));
            }
        }

        for (int i = 0; i<= m.getLastIdxRow(); i++){
            for (int j = 0; j<= m.getLastIdxCol(); j++) {
                //System.out.println(i + " "+ j + this.col);
                mOut.setELMT(i , j + this.col, m.getELMT(i, j));
            }
        }
        return mOut;
    }

    // Driver perhitungan determinan
    public static void driverDeterminan() {
        boolean notValid = false;

        while (!notValid) {
            int x;
            System.out.println("\nMetode Determinan yang tersedia");
            System.out.println("    1. Reduksi Baris");
            System.out.println("    2. Ekspansi Kofaktor");
            Scanner sc = new Scanner(System.in);
            System.out.print("Pilih metode yang diinginkan\n>> ");
            x = sc.nextInt();
            if (x==1) {
                int n, x2;
                double hasil;
                String sHasil = ""; 
                boolean notValid2 = false;
                Matrix m;
                while (!notValid2) {
                    sc = new Scanner(System.in);
                    System.out.println("\nJenis input yang tersedia");
                    System.out.println("    1. Terminal");
                    System.out.println("    2. File txt");
                    System.out.print("Pilih jenis input yang diinginkan\n>> ");
                    x2 = sc.nextInt();
                    if (x2 == 1) {
                        System.out.print("Masukkan jumlah baris dan kolom matrix n\n>> ");
                        n = sc.nextInt();
                        m = new Matrix(n, n);
                        m.readMatrixPeubah();
                        hasil = m.determinantOBE();
                        System.out.print("\n");
                        sHasil += "Determinan dari matrix\n";
                        sHasil += m.matrixToString();
                        sHasil += "adalah: " + Double.toString(hasil);
                        System.out.println(sHasil);
                        UI.simpan(sHasil);

                        notValid2 = true;
                    }
                    else if (x2 == 2) {
                        sc = new Scanner(System.in);
                        String fileName;
                        int row=0, col=0;

                        System.out.print("\nMasukkan nama file\n>> ");
                        fileName = sc.nextLine();
                        m = IOFile.readFileMat("test/" + fileName + ".txt");

                        if (m != null) {
                            row = IOFile.getRow("test/" + fileName + ".txt");
                            col = IOFile.getCol("test/" + fileName + ".txt");
                        }
           
                        while (m == null | col!=row) {
                            if (m != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                                if (col!=row) {
                                    System.out.println("Bukan merupakan matrix dengan peubah n x n !");
                                }
                            }

                            System.out.print("\nUlangi masukkan nama file\n>> ");
                            fileName = sc.nextLine();
                            m = IOFile.readFileMat("test/" + fileName + ".txt");
                            if (m != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                            }
                        }
                        hasil = m.determinantOBE();
                        System.out.print("\n");
                        sHasil += "Determinan dari matrix\n";
                        sHasil += m.matrixToString();
                        sHasil += "adalah: " + Double.toString(hasil);
                        System.out.println(sHasil);
                        UI.simpan(sHasil);

                        notValid2 = true;
                    }
                    else {
                        System.out.println("Input tidak valid! Ulangi");
                    }
                }
                notValid = true;
            }

            else if (x==2) {
                int n, x2;
                double hasil;
                String sHasil = ""; 
                boolean notValid2 = false;
                Matrix m;
                while (!notValid2) {
                    sc = new Scanner(System.in);
                    System.out.println("\nJenis input yang tersedia");
                    System.out.println("    1. Terminal");
                    System.out.println("    2. File txt");
                    System.out.print("Pilih jenis input yang diinginkan\n>> ");
                    x2 = sc.nextInt();
                    if (x2 == 1) {
                        System.out.print("Masukkan jumlah baris dan kolom matrix n\n>> ");
                        n = sc.nextInt();
                        m = new Matrix(n, n);
                        m.readMatrixPeubah();
                        hasil = m.determinanKof();
                        System.out.print("\n");
                        sHasil += "Determinan dari matrix\n";
                        sHasil += m.matrixToString();
                        sHasil += "adalah: " + Double.toString(hasil);
                        System.out.println(sHasil);
                        UI.simpan(sHasil);

                        notValid2 = true;
                    }
                    else if (x2 == 2) {
                        sc = new Scanner(System.in);
                        String fileName;
                        int row=0, col=0;

                        System.out.print("\nMasukkan nama file\n>> ");
                        fileName = sc.nextLine();
                        m = IOFile.readFileMat("test/" + fileName + ".txt");

                        if (m != null) {
                            row = IOFile.getRow("test/" + fileName + ".txt");
                            col = IOFile.getCol("test/" + fileName + ".txt");
                        }
           
                        while (m == null | col!=row) {
                            if (m != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                                if (col!=row) {
                                    System.out.println("Bukan merupakan matrix dengan peubah n x n !");
                                }
                            }

                            System.out.print("\nUlangi masukkan nama file\n>> ");
                            fileName = sc.nextLine();
                            m = IOFile.readFileMat("test/" + fileName + ".txt");
                            if (m != null) {
                                row = IOFile.getRow("test/" + fileName + ".txt");
                                col = IOFile.getCol("test/" + fileName + ".txt");
                            }
                        }
                        hasil = m.determinanKof();
                        System.out.print("\n");
                        sHasil += "Determinan dari matrix\n";
                        sHasil += m.matrixToString();
                        sHasil += "adalah: " + Double.toString(hasil);
                        System.out.println(sHasil);
                        UI.simpan(sHasil);

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

    // Driver Inverse Matrix
    public static void driverInverse() {
        boolean notValid = false;        
        int x;
        while (!notValid) {
            String sHasil = "";
            Matrix m, mhasil;
            Scanner sc = new Scanner(System.in);
            System.out.println("\nJenis input yang tersedia");
            System.out.println("    1. Terminal");
            System.out.println("    2. File txt");
            System.out.print("Pilih jenis input yang diinginkan\n>> ");
            x = sc.nextInt();

            if (x == 1) {
                notValid = true;
                int n;
                System.out.print("Masukkan jumlah baris dan kolom matrix n\n>> ");
                sc = new Scanner(System.in);
                n = sc.nextInt();
                m = new Matrix(n, n);
                m.readMatrixPeubah();
                mhasil = m.inverseMatrix();
                if (mhasil == null) {
                    break;
                }
                System.out.println();
                sHasil += "Inverse dari matrix input adalah:\n";
                sHasil += mhasil.matrixToString();
                System.out.println(sHasil);
                UI.simpan(sHasil);

            } else if (x == 2) {
                notValid = true;
                sc = new Scanner(System.in);
                sc = new Scanner(System.in);
                String fileName;
                int row=0, col=0;

                System.out.print("\nMasukkan nama file\n>> ");
                fileName = sc.nextLine();
                m = IOFile.readFileMat("test/" + fileName + ".txt");

                if (m != null) {
                    row = IOFile.getRow("test/" + fileName + ".txt");
                    col = IOFile.getCol("test/" + fileName + ".txt");
                }
   
                while (m == null | col!=row) {
                    if (m != null) {
                        row = IOFile.getRow("test/" + fileName + ".txt");
                        col = IOFile.getCol("test/" + fileName + ".txt");
                        if (col!=row) {
                            System.out.println("Bukan merupakan matrix dengan peubah n x n !");
                        }
                    }

                    System.out.print("\nUlangi masukkan nama file\n>> ");
                    fileName = sc.nextLine();
                    m = IOFile.readFileMat("test/" + fileName + ".txt");
                    if (m != null) {
                        row = IOFile.getRow("test/" + fileName + ".txt");
                        col = IOFile.getCol("test/" + fileName + ".txt");
                    }
                }

                mhasil = m.inverseMatrix();
                if (mhasil == null) {
                    break;
                }
                System.out.println();
                sHasil += "Inverse dari matrix input adalah:\n";
                sHasil += mhasil.matrixToString();
                System.out.println(sHasil);
                UI.simpan(sHasil);
            } else {
                System.out.println("Input tidak valid! Ulangi.");
            }
        }
    }

}