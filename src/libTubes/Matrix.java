package libTubes;
import java.util.*;

public class Matrix {
    protected int row;
    protected int col;
    public double data[][];

    public Matrix(int row, int col) {       // driver checked
        this.row = row;
        this.col = col;
        this.data = new double[row][col];
    }

    
    /* *** Selektor "Dunia Matrix" *** */

    public void setELMT(int i, int j, double val) {         // driver checked
        this.data[i][j] = val;
    }

    public double getELMT(int i, int j) {               // driver checked
        return this.data[i][j];
    }

    public double[][] getData(){
        return this.data;
    }

    public boolean isMatrixIdxValid(int i, int j)             // driver checked    
    /* Mengirimkan true jika i, j adalah index yang valid untuk matriks apa pun */
    {
        return ((i<=getLastIdxRow()) && (i>=0) && (j<=getLastIdxCol()) && (j>=0));
    }

    /* *** Selektor: Untuk sebuah matriks m yang terdefinisi: *** */
    public int getLastIdxRow()               // driver checked
    /* Mengirimkan Index baris terbesar m */
    {
        return this.row-1;
    }

    public int getLastIdxCol()                // driver checked
    /* Mengirimkan Index kolom terbesar m */
    {
        return this.col-1;
    }


    public double getElmtDiagonal(int i)           // driver checked
    /* Mengirimkan elemen m(i,i) */
    {
        return getELMT(i, i);
    }


    /* ********** KELOMPOK BACA/TULIS ********** */

    public void readMatrix()           //driver checked
    /* I.S. isIdxValid(nRow,nCol) */
    /* F.S. m terdefinisi nilai elemen efektifnya, berukuran nRow x nCol */
    /* Proses: Melakukan CreateMatrix(m,nRow,nCol) dan mengisi nilai efektifnya */
    /* Selanjutnya membaca nilai elemen per baris dan kolom */
    /* Contoh: Jika nRow = 3 dan nCol = 3, maka contoh cara membaca isi matriks :
    1 2 3
    4 5 6
    8 9 10 
    */
    {
        // Kamus
        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan value matrix: ");
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                setELMT(i, j, sc.nextDouble());
            }
        }

        //sc.close();
        
    }
    public void readMatrixPeubah()           //Khusus input matrix peubah
    //input berupa a[i][j] 
    {
        // Kamus
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                System.out.print("a["+i+"]["+j+"]: ");
                setELMT(i, j, sc.nextDouble());
            }
        }

        //sc.close();
        
    }

    public void readMatrixHasil()            //Khusus input matrix hasil
   //input berupa b[i]
    {
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

    public void displayMatrix()                //driver checked
    /* I.S. m terdefinisi */
    /* F.S. Nilai m(i,j) ditulis ke layar per baris per kolom, masing-masing elemen per baris 
    dipisahkan sebuah spasi. Baris terakhir tidak diakhiri dengan newline */
    /* Proses: Menulis nilai setiap elemen m ke layar dengan traversal per baris dan per kolom */
    /* Contoh: menulis matriks 3x3 (ingat di akhir tiap baris, tidak ada spasi)
    1 2 3
    4 5 6
    8 9 10
    */
    {
        // Kamus

        // Algoritma   
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                System.out.print(String.format("%.4f", getELMT(i, j)));
                if (j!=getLastIdxCol()) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");   
        }     
        //System.out.print("=====================\n");
    }

    public String matrixToString()                
    {
        // Kamus
        String hasil="";
        // Algoritma   
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

    /* ********** Operasi lain ********** */
    public int countElmt()                      //driver checked
    /* Mengirimkan banyaknya elemen m */
    {
        return this.row*this.col;
    }

    /* ********** KELOMPOK TEST TERHADAP Matrix ********** */
    public boolean isSquare()                      //driver checked
    /* Mengirimkan true jika m adalah matriks dg ukuran baris dan kolom sama */
    {
        return this.row == this.col;
    }

    /* ********** Assignment  Matrix ********** */
    public Matrix copyMatrix()                      // driver checked
    /* Melakukan assignment mOut <- mIn */
    {
        // Kamus
        Matrix mOut = new Matrix(this.row, this.col);

        // Algoritma
        for (int i = 0; i<=getLastIdxRow(); i++) {
            for (int j = 0; j<=getLastIdxCol(); j++) {
                mOut.setELMT(i, j, this.getELMT(i, j)); 
            }
        }
        return mOut;
    }    

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

    public double determinantOBE()
    /* Prekondisi: isSquare(m) */
    /* Menghitung nilai determinan m menggunakan metode operasi baris elementer*/
    {
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

    // Menghasilkan adjoint dari suatu matrix
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

    public boolean rowZero(int row){
        for (int j = 0; j < this.col; j++){
            if (getELMT(row, j) != 0){
                return false;
            }
        }
        return true;
    }

    public int satuUtamaIdx(int row){

        for (int j = 0; j < this.col; j++){
            if (getELMT(row, j) != 0){
                return j;
            }
        }
        
        return -1;
    }

    

// 1 2 3
// 0 1 4
// 0 0 1    
    public Matrix switchCol(Matrix mCol, int colIdx) {  
        // Switch suatu kolom dengan matrix tertentu yang memiliki ukuran (row, 1)
        Matrix mOut = copyMatrix();

        for (int i = 0; i<=mOut.getLastIdxRow(); i++) {
            mOut.setELMT(i, colIdx, mCol.getELMT(i, 0));
        }
        return mOut;
    }

    public void setMatFromFile(double[][] m){
        this.data = m;
    }

    public boolean isParametricSolution(){
        if (this.row <  this.col-1){
            return true;

        }
        else {
            for (int j = 0; j < this.col; j++){
                if (getELMT(this.getLastIdxRow(), j) != 0){
                    return false; 
                }
            }
            return true;
        }
    }

    public boolean isNoSolution(){
        for( int j = 0; j < this.col; j++){
            if(j != this.getLastIdxCol() && getELMT(this.getLastIdxRow(), j) != 0){
                return false;                
            }
            else if (j == this.getLastIdxCol() && getELMT(this.getLastIdxRow(), j) == 0){
                return false;
            }
        }
        return true;
    }

    public boolean isUniqueSolution(){
        return (!this.isParametricSolution() && !this.isNoSolution());
    }
    
    // Multiply matrix
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

// 1 2 3
// 0 1 4
// 0 0 1