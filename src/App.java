public class App{
    public static void main(String[] args){
        String file = "test/text.txt";

        Matrix m = new Matrix(ReadFile.getRow(file), ReadFile.getCol(file));
        
        m.setMatFromFile(ReadFile.readFileMat(file, ReadFile.getRow(file), ReadFile.getCol(file)));

        m.GaussJordan();

        m.displayMatrix();
    }
}
