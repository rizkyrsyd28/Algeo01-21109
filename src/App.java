public class App{
    public static void main(String[] args){
        String file = "test/text.txt";
        
        Matrix m = new Matrix(IOFile.getRow(file), IOFile.getCol(file));
        
        IOFile.readFileMat(m, file, IOFile.getRow(file), IOFile.getCol(file));

        
        // IOFile.createEmptyFile("main");
        SPL a = new SPL(1, 1);

        // Matrix m_sol = a.SolFormatting(m);
        // m_sol.displayMatrix(); System.out.println();

        Matrix m_sol = a.gauss(m);
        String hasil = a.displaySPL(m_sol);
        // m_sol.displayMatrix(); System.out.println();
        System.out.print(hasil);
        
        // IOFile.writeMatrix("main", m.getData());
    }
}