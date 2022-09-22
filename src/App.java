public class App{
    public static void main(String[] args){

        Matrix m1 = new Matrix(4, 4);
        //float det;
        Matrix m2 = new Matrix(4, 1);
        Matrix m3;        

        m1.readMatrix();
        m1.displayMatrix();
        System.out.println("=========\n");  
        m2.readMatrix();
        m2.displayMatrix();       
        
        m1.cramer(m2);
        
        // det = m1.determinantOBE();
        // System.out.print(det);
   



    }
}
