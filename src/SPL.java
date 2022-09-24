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
            System.out.println("SPL ini tidak memiliki solusi tunggal karena tidak memiliki invers.");
            return null;
        } else {
            Matrix x = invMat.multiplyMatrix(b);
            return x;
        }
    }
    
    
    public void displaySPL(Matrix m_sol) {
        for (int i = 0; i < m_sol.row; i++) {
            System.out.println(String.format("x_%d = %.4f", i, m_sol.getELMT(i, 0)));
        }
    }

}


