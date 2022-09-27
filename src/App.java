public class App{
    public static void main(String[] args){
        String file = "test/text.txt";
        
        Matrix m = new Matrix(IOFile.getRow(file), IOFile.getCol(file));
        
        IOFile.readFileMat(m, file, IOFile.getRow(file), IOFile.getCol(file));

        float[][] temp = Regresi.solveRegresi(m.data);


        IOFile.createEmptyFile("main");
        
        
        IOFile.writeMatrix("main", temp);
    }
}