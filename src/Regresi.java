public class Regresi{
    public static float[] solveRegresi(float[][] m){
        float[][] augMat = new float[m.length][m[1].length + 1];
        
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
        float[][] NEE = new float[m[1].length][m[1].length + 1];

        for (int i = 0; i < m[i].length; i++){
            for (int j = 0; j <= m[i].length; j++){
                NEE[i][j] = 0;
                for (int k = 0; k < m.length; k++){
                    NEE[i][j] += augMat[k][i] * augMat[k][j];
                }
            }
        }

        Matrix nee = new Matrix(NEE.length, NEE[1].length);

        nee.data = NEE;
        
        
        nee.displayMatrix();
        
        SPL.gaussJordan(nee);
        

        float [] coef = new float[nee.row];

        for (int i = 0; i < nee.row; i++){
            coef[i] = nee.getELMT(i, nee.getLastIdxCol());
        }

        return coef;
    }

    public static void MultiRegresi(){

    }
}
