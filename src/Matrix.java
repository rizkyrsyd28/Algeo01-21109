import java.util.*;

public class Matrix {
    private int row;
    private int col;
    private float data[][];

    public Matrix(int row, int col) {       // driver checked
        this.row = row;
        this.col = col;
        this.data = new float[row][col];
    }

    
    /* *** Selektor "Dunia Matrix" *** */

    public void setELMT(int i, int j, float val) {         // driver checked
        this.data[i][j] = val;
    }

    public float getELMT(int i, int j) {               // driver checked
        return this.data[i][j];
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


    public float getElmtDiagonal(int i)           // driver checked
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
                setELMT(i, j, sc.nextFloat());
            }
        }
        sc.close();
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
                System.out.print(getELMT(i, j));
                if (j!=getLastIdxCol()) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");   
        }     
        System.out.print("=====================\n");
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

    public float determinanKof(){
        float det = 0; 
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

    public float determinantOBE()
    /* Prekondisi: isSquare(m) */
    /* Menghitung nilai determinan m */
    {
        // Kamus
        int rowSwitch = -1;       
        float pengali = 1, det = 1, tempfloat;

        // Algoritma
        if (!isSquare()) {
            System.out.println("Harus matrix berbentuk persegi (ukuran n x n)! ");
            return -9999;
        }

        else {
            if (countElmt()==1) {      // Cek jika hanya 1 elemen
                return (float) getELMT(0, 0);
            }    
            else {
                for (int j = 0; j<=getLastIdxCol(); j++) {
                    for (int i = j+1; i<=getLastIdxRow(); i++) {
                        if (getELMT(j, j) == 0) {
                            for (int k = j+1; k<=getLastIdxRow(); k++) {
                                if (getELMT(k, j) != 0) {
                                    rowSwitch = k;
                                }
                            } 
                            if (rowSwitch == -1) {
                                return (float) 0;
                            }
                            else {
                                for (int k = 0; k<=getLastIdxCol(); k++) {
                                    tempfloat = getELMT(rowSwitch, k);
                                    setELMT(rowSwitch, k, getELMT(j, k));
                                    setELMT(j, k, tempfloat);
                                }
                                rowSwitch = -1;
                                det *= -1;
                            }
                        }
    
                        pengali = getELMT(i, j)/getELMT(j, j);
                        for (int k = 0; k<=getLastIdxRow(); k++) {
                            setELMT(i, k, getELMT(i, k) - pengali*getELMT(j, k));
                        }
                    }
                } 
    
                for (int i  = 0; i<=getLastIdxCol(); i++) {
                    det*=getELMT(i, i);
                }
                return det;          
            }            
            
        }
        
    }
    /*
     * branch1 = bawah ada korban
     * branch2 = utama satu
     */
    public boolean rowZero(int row){
        for (int j = 0; j < this.col; j++){
            if (getELMT(row, j) != 0){
                return false;
            }
        }
        return true;
    }

    public void Gauss(){
        int i = 0, j = 0; 
        while (i < this.row && j < this.col){
            if (this.getELMT(i, j) == 0){
                boolean cp = false;
                for (int k = i + 1; k < this.row; k++){
                    if (this.getELMT(k, j) != 0){
                        float[] temp = this.data[i];
                        this.data[i] = this.data[k];
                        this.data[k] = temp;
                        cp = true;
                        break;
                    }
                }
                if (rowZero(i) && i != this.getLastIdxRow()){
                    float[] temp = this.data[i];
                    this.data[i] = this.data[i+1];
                    this.data[i+1] = temp;
                }
                if (!cp){
                    j++;
                }
                // this.displayMatrix();
            }
            // safety  breaker
            if (i >= this.row || j >= this.col){
                break;
            }

            if (this.getELMT(i, j) != 0){
                float ratio = 1;
                if (this.getELMT(i, j) != 1){
                    ratio = this.getELMT(i, j);
                    for (int k = j; k < this.col; k++){
                        setELMT(i, k, getELMT(i, k)/ratio);
                    }
                }
                ratio = 1;
                for (int k = i + 1; k < this.row; k++){
                    ratio = getELMT(k, j)/getELMT(i, j);
                    for (int l = j; l < this.col; l++){
                        setELMT(k, l, getELMT(k, l) - ratio*getELMT(i, l));
                    }
                }
                i++;
                j++;
                // this.displayMatrix();
            }
            
            //this.displayMatrix();
            // break;
        }
    }

    public int satuUtamaIdx(int row){

        for (int j = 0; j < this.col; j++){
            if (getELMT(row, j) != 0){
                return j;
            }
        }    
        return -1;
    }

    public void GaussJordan(){
        float pengali = 1; 
        
        this.Gauss();

        for (int i = 0; i < this.row - 1; i++){
            for (int k = i+1; k < this.row; k++){
                pengali = getELMT(i, this.satuUtamaIdx(k))/getELMT(k, this.satuUtamaIdx(k));
                for (int j = satuUtamaIdx(k); j < this.col; j++){
                    setELMT(i, j, getELMT(i, j) - pengali * getELMT(k, j));
                }
            }
        }
    }

    public void setMatFromFile(float[][] m){
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

}
// 1 2 3
// 0 1 4
// 0 0 1