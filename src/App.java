public class App{
    public static void main(String[] args){
        Matrix m = new Matrix(3, 3);
        m.readMatrix();
        m = m.subMatrix(0, 0);
        m.displayMatrix();
        System.out.println(m.getElmtDiagonal(1));
    }
}
