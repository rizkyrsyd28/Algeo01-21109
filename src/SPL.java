public class SPL extends Matrix {
    
    public SPL(int row, int col) {
        super(row, col);
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
            System.out.println("SPL ini tidak memiliki solusi tunggal karena tidak memiliki invers.");
            return null;
        } else {
            Matrix x = invMat.multiplyMatrix(b);
            return x;
        }
    }
    
    
    public static void displaySPL(Matrix m_sol) {
        for (int i = 0; i < m_sol.row; i++) {
            System.out.println(String.format("x_%d = %.4f", i, m_sol.getELMT(i, 0)));
        }
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
        //augm.displayMatrix();
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
        }

        return mHasil;


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
        }




        return mHasil;
    }

}


