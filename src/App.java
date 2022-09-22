public class App{
    public static void main(String[] args){
        String file = "test/text.txt";

<<<<<<< HEAD
        Matrix m1 = new Matrix(4, 4);
        //float det;
        Matrix m2 = new Matrix(4, 1);
        Matrix m3;        

        m1.readMatrix();
        m1.displayMatrix();
        System.out.println("=========\n");  
        m2.readMatrix();
        m2.displayMatrix();       
        
        m1.cramer(m2);
        
        // det = m1.determinantOBE();
        // System.out.print(det);
   



=======
        Matrix m = new Matrix(ReadFile.getRow(file), ReadFile.getCol(file));
        
        m.setMatFromFile(ReadFile.readFileMat(file, ReadFile.getRow(file), ReadFile.getCol(file)));
        
        Matrix invMat = m.inverseMatrix();
        invMat.displayMatrix();

        // invMat.displayMatrix();
>>>>>>> 71f00e41f6190487753899a6e67d56092ef201de
    }
}
