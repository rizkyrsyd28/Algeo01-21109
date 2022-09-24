public class App{
    public static void main(String[] args){
        String file = "test/text.txt";
        
        Matrix m = new Matrix(ReadFile.getRow(file), ReadFile.getCol(file));
        
        m.setMatFromFile(ReadFile.readFileMat(file, ReadFile.getRow(file), ReadFile.getCol(file)));
        
        Matrix invMat = m.inverseMatrix();
        invMat.displayMatrix();

        // invMat.displayMatrix();
    }
}
