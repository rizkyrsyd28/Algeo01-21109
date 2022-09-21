public class App{
    public static void main(String[] args){

        Matrix m = new Matrix(3, 4);
        m.readMatrix();
        m.displayMatrix();
        m.GaussJordan();
        System.out.println("=========\n");
        m.displayMatrix();
    }
}
