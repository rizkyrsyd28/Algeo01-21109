public class App{
    public static void main(String[] args){
        String file = "test/text.txt";
        
        Matrix m = IOFile.readFileMat(file);

        m.displayMatrix();

        // IOFile.writeMatrix("test10", m);

        // Matrix.driverInverse();
        // Matrix.driverDeterminan();
        SPL.driverSPL();
    }
}