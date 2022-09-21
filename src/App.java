public class App{
    public static void main(String[] args){
        Matrix m = new Matrix(2, 3);
        m.readMatrix();
        m.displayMatrix();
        System.out.println(m.determinantOBE());
    }
}
