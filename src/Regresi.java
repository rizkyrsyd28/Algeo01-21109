public class Regresi extends UI {
    public static double[] getCoefRegresi(double[][] m){
        
        double[][] augMat = new double[m.length][m[1].length + 1];
        
        // tambah kolom di awal untuk koef b0, isinya 1 
        for (int i = 0; i < m.length; i++){
            augMat[i][0] = 1;    
        }

        // konkat matrix input 
        for (int i = 0; i < m.length; i++){
            for (int j = 1; j <= m[i].length; j++){
                augMat[i][j] = m[i][j-1];
            }
        }
        // normal estimation equation 
        double[][] NEE = new double[m[1].length][m[1].length + 1];

        for (int i = 0; i < m[i].length; i++){
            for (int j = 0; j <= m[i].length; j++){
                NEE[i][j] = 0;
                for (int k = 0; k < m.length; k++){
                    NEE[i][j] += augMat[k][i] * augMat[k][j];
                }
            }
        }

        Matrix nee = new Matrix(NEE.length, NEE[1].length);

        nee.data = NEE;
        
        
        nee.displayMatrix();
        
        SPL.gaussJordan(nee);
        

        double [] coef = new double[nee.row];

        for (int i = 0; i < nee.row; i++){
            coef[i] = nee.getELMT(i, nee.getLastIdxCol());
        }

        return coef;
    }

    public static void MultiRegresi(){
        System.out.println("pilihan input\n1. Input Terminal\n2. Input File");
        int mode; 
        double[][] augm;
        mode = Pilih(2);

        if (mode == 1){
            int n, m;

            System.out.print("Masukan peubah : ");
            n = sc.nextInt();             
            System.out.print("Masukan banyak persamaan : ");
            m = sc.nextInt();
            System.out.printf("Masukan %d persamaan\n", m);
            augm = new double[m][n+1];
            for (int i = 0; i < m; i++){
                for (int j = 0; j < n; j++){
                    augm[i][j] = sc.nextDouble();
                }
            }
        } else {
            String fileName; 

            System.out.print("Masukkan nama file\n> ");
            fileName = sc.next();

            while(!IOFile.isFileExist(fileName)){
                System.out.println("Masukkan salah, silahkan masukkan ulang!");
                System.out.print("Masukkan nama file\n> ");
                fileName = sc.nextLine();
            }
            augm = new double[IOFile.getRow(fileName)][IOFile.getCol(fileName)];
            augm = IOFile.readFileMat(fileName).data;
        }
        
        double[] coef = getCoefRegresi(augm);

        System.out.println("Persamaan regresi linier berganda : ");
        
        if (coef[0] == 0){
            System.out.printf("y = %fx1", coef[1]);
            for (int i = 2; i < coef.length; i++){
                if (coef[i] > 0){
                    System.out.printf(" + %f x%d", coef[i], i);
                } else if (coef[i] < 0){
                    System.out.printf(" %f x%d", coef[i], i);
                }
            }
        } else {
            System.out.printf("y = %f ", coef[0]);
            for (int i = 1; i < coef.length; i++){
                if (coef[i] > 0){
                    System.out.printf(" + %f x%d", coef[i], i);
                } else if (coef[i] < 0){
                    System.out.printf(" %f x%d", coef[i], i);
                }
            }
        }

        System.out.println("Menaksir nilai fungsi");
        System.out.printf("Masukkan %d peubah yang akan ditaksir nilai fungsinyan\n", coef.length - 1);

        double taksir = coef[0];
        
        double[] input = new double[coef.length-1];

        for (int i = 0; i < coef.length-1; i++){
            input[0]=  sc.nextDouble();
        }
        for (int i = 0; i < input.length; i++){
            taksir += input[0] * coef[i+1];
        }

        System.out.printf("Nilai taksirannya adalah %f\n\n", taksir);

        boolean saveFile = save2File();

        if (saveFile){
            String output = "Persamaan Regresi Linier yang diperoleh\n";

            if (coef[0] == 0){
                output += ("y = " + coef[1] + " x1");
                for (int i = 2; i < coef.length; i++){
                    if (coef[i] > 0){
                        output += (" + " + coef[i] + "x" + i);
                    } else if (coef[i] < 0){
                        output += (" - " + (-1 * coef[i]) + "x" + i);
                    }
                }
            } else {
                output += ("y = " + coef[0]);
                for (int i = 1; i < coef.length; i++){
                    if (coef[i] > 0){
                        output += (" + " + coef[i] + "x" + i);
                    } else if (coef[i] < 0){
                        output += (" - " + (-1 * coef[i]) + "x" + i);
                    }
                }
            }
            output += ("\nNilai taksirannya adalah " + taksir);


            System.out.print("Masukkan nama File\n> ");
            String fileDir = sc.next();
            if (!IOFile.isFileExist(fileDir)){
                IOFile.createEmptyFile(fileDir);
            }
            IOFile.writeString(fileDir, output);

        }

        // double[] ans;
        // // ans = solveRegresi(m.data);

        // System.out.print(ans[0]);
        // for (int i = 1; i < ans.length; i++){
        //     System.out.print(" + " + ans[i] + "x" + i);
        // }
        // System.out.println(" ");
    }
}
