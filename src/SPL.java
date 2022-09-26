public class SPL extends Matrix {
    
    public SPL(int row, int col) {
        super(row, col);
    }

    public Matrix inverseSPL(Matrix augm) {
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
    
    public String displaySPL(Matrix m_sol) {
        String hasil = "";
        if (m_sol == null) {
            
            System.out.println("SPL tidak memiliki solusi.");
            return null;

        } else if (m_sol.col == 1) {
            
            for (int i = 0; i <= m_sol.getLastIdxRow(); i++) {
                hasil += String.format("x_%d = %.3f\n", i, m_sol.getELMT(i, 0));
            }

        } else {

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
                                    hasil += String.format(" + %.3fx_%d", m_sol.getELMT(i, j), j + 1);
                                } else if (m_sol.getELMT(i, j) < 0) {
                                    hasil += String.format(" - %.3fx_%d", -m_sol.getELMT(i, j), j + 1);
                                } else {
                                    hasil += String.format(" %.3fx_%d", m_sol.getELMT(i, j), j + 1);
                                }
                            }
                            
                            // if (m_sol.getELMT(i, j + 1) > 0) {
                            //     hasil += " + ";
                            // } else if (m_sol.getELMT(i, j + 1) < 0) {
                            //     hasil += " - ";
                            // }
    
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
        }

        return hasil;
    }

    public Matrix cramer(Matrix augm) {          // Driver checked
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

    public void makeSatuUtama(Matrix augm) {       // Bikin bentuk matrix jd punya satu utama di semua baris
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

    public Matrix gauss(Matrix augm){          
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

            SPL spl_obj = new SPL(1, 1);
            augm = spl_obj.SolFormatting(augm);
            return augm;

        } else {
            return null;
        }
    }   



    public Matrix gaussJordan(Matrix augm){
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
            SPL spl_obj = new SPL(1,1);
            augm = spl_obj.SolFormatting(augm);
            return augm;
        } else {
            return null;
        }
    }


    public Matrix SolFormatting(Matrix m_sol) {

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
}


