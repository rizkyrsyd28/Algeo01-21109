import java.lang.Math;

public class Bicubic extends SPL {
    
    public Bicubic(int row, int col) {
        super(row, col);
    }

    public static float bicubicInterpolation(Matrix m, float a, float b) {
        Matrix X = new Matrix(16, 16);
        Matrix Y = new Matrix(16, 1);
        
        int k = 0, l = 0, x = -1, y = -1;
        for (int i = 0; i < X.row; i++) {
            for (int j = 0; j < X.col; j++) {

               // Assignment of matrix X
                X.data[i][j] = (float)(Math.pow(x, k) * Math.pow(y, l));
                k += 1;
                if (k == 4) {
                    k = 0;
                    l += 1;
                }
                if (l == 4) {
                    l = 0;
                }

            }
            
            Y.data[i][0] = m.data[y + 1][x + 1];
            x += 1;
            if (x == 3) {
                x = -1;
                y += 1;
            }

        }
        
        // Coefficient calculation
        Matrix X_inv = X.inverseMatrix();
        Matrix a_koef = X_inv.multiplyMatrix(Y);
        
        // Value Checking (can be erased later)
        // Matrix test = X_inv.copyMatrix();
        // test.multiplyByConst(36.0f);
        // test.displayMatrix();
        // System.out.println();
        // X_inv.displayMatrix();
        // System.out.println();
        // a_koef.displayMatrix();
        // System.out.println();

        float value_I = 0.0f;
        k = 0; l = 0;
        for (int i = 0; i < a_koef.row; i++) {
            value_I += a_koef.data[i][0] * (float)(Math.pow(a, k) * Math.pow(b, l));
            k += 1;
            if (k == 4) {
                k = 0;
                l += 1;
            }
        }
        return value_I;
    
    }
}
