public class App{
    public static void main(String[] args){
        String dir = "text.txt";       
        // boolean run = true; int mode;


        // while (run){
        //     printMainMenu();
        //     mode = Pilih(7);
        //     switch (mode){
        //         case 1:
        //                 SPL.driverSPL();
        //         case 2:
        //                 Matrix.driverDeterminan();
        //         case 3: 
        //                 Matrix.driverInverse();
        //         case 6: 
        //                 Regresi.MultiRegresi();
        //         case 7:
        //                 run = false;
        //     }
        // }
        Matrix m = IOFile.readBcb(dir);

        m.displayMatrix();

        double[] coor = IOFile.coorBcb(dir);

        for (int i = 0; i < 2; i++){
                System.out.println(coor[i] + "\n");
        }   
    }
}