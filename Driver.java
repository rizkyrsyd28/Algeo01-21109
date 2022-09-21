
public class Driver {
    public static void main(String[] args) {
        Matrix m = new Matrix(2, 2);
        m.readMatrix();
        m.displayMatrix();
        System.out.println(m.getElmtDiagonal(1));
    }
}
