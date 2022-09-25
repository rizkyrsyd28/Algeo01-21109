public class App{
    public static void main(String[] args){
        String file = "test/text.txt";
        
        Matrix m = new Matrix(IOFile.getRow(file), IOFile.getCol(file));
        
        m.setMatFromFile(IOFile.readFileMat(file, IOFile.getRow(file), IOFile.getCol(file)));
        
        Matrix invMat = m.inverseMatrix();
        invMat.displayMatrix();

        // invMat.displayMatrix();
    }
}