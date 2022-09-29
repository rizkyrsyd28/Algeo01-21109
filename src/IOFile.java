import java.io.*;
import java.util.*;


public class IOFile{

    public static int getRow(String fileName){
        FileReader file = null;
        try {
            file = new FileReader("test/" + fileName);
        } catch (FileNotFoundException notFound){
            System.out.println("File tidak ditemukan");
        }

        int rowVal = 0;
        String row = null;
        Scanner rowScan = new Scanner(file);
        
        while (rowScan.hasNextLine()){
            rowVal++;
            row = rowScan.nextLine();
        }

        rowScan.close();

        return rowVal;
    }

    public static int getCol(String fileName){
        FileReader file = null;
        try {
            file = new FileReader("test/" + fileName);
        } catch (FileNotFoundException notFound){
            System.out.println("File tidak ditemukan");
        }

        int colVal = 0;
        Scanner rowScan = new Scanner(file);
        Scanner colScan = null;
        String row = null;
        if (rowScan.hasNextLine()){
            row = rowScan.nextLine();
        }
        colScan = new Scanner(row);
        while (colScan.hasNextFloat()){
            colVal++;
            colScan.nextFloat();
        } 
        
        rowScan.close();
        colScan.close();

        return colVal;
    }

    public static Matrix readFileMat(String fileName){  //Berubah jd ngereturn Matrix dan skrg parameternya cmn filename
        FileReader file = null;

        try {
            file = new FileReader("test/" + fileName);
            int row = getRow(fileName), col = getCol(fileName);

            Scanner rowScan = new Scanner(file);
            Matrix mOut = new Matrix(row, col);

            for (int i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    mOut.setELMT(i, j, rowScan.nextFloat());
                }
            }

            rowScan.close();

            return  mOut;


        } catch (Exception notFound){
            System.out.println("File tidak ditemukan");
        }
        return null;
    }


    public static Matrix readBcb(String fileName){  //Berubah jd ngereturn Matrix dan skrg parameternya cmn filename
        FileReader file = null;

        try {
            file = new FileReader("test/" + fileName);
            int row = getRow(fileName) - 1, col = getCol(fileName);

            Scanner rowScan = new Scanner(file);
            Matrix mOut = new Matrix(row, col);

            for (int i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    mOut.setELMT(i, j, rowScan.nextFloat());
                }
            }

            rowScan.close();

            return  mOut;


        } catch (Exception notFound){
            System.out.println("File tidak ditemukan");
        }
        return null;
    }

    public static float[] coorBcb(String fileName){
        FileReader file = null;
        float[] coor = new float[2];
        try {
            file = new FileReader("test/" + fileName);
            int row = getRow(fileName);

            Scanner rowScan = new Scanner(file);

            for (int i = 0; i < row; i++){
                for (int j = 0; j < 2; j++){
                    if (i == row-1){
                        coor[j] = rowScan.nextFloat();
                    }
                }
            }

            rowScan.close();

            return  coor;


        } catch (Exception notFound){
            System.out.println("File tidak ditemukan");
        }
        return null;
    }


    public static void createEmptyFile(String fileName){
        try{
            File myFile = new File("test/output/" + fileName);
            if(myFile.createNewFile()){
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exist");
            }
        } catch(IOException e){
            System.out.println("ERROR");
        }
    }

    public static void writeMatrix(String fileName, Matrix data){
        try {
            FileWriter myWrite = new FileWriter("test/output/" + fileName);

            for (int i = 0; i <= data.getLastIdxRow(); i++){
                for(int j = 0; j <= data.getLastIdxCol(); j++){
                    myWrite.write(Float.toString(data.getELMT(i, j)) + " ");
                }
                myWrite.write("\n");
            }
            myWrite.close();
            System.out.println("Berhasil menyimpan hasil ke file " + fileName + " di folder ./test/output");
        }
        catch (IOException e){
            System.out.println("duar ERROR");
        }
    }

    public static void writeString(String fileName, String s){
        try {
            FileWriter myWrite = new FileWriter("test/output/" + fileName);
            myWrite.write(s);
            myWrite.close();
            System.out.println("Berhasil menyimpan hasil ke file " + fileName + " di folder ./test/output");
        }
        catch (IOException e){
            System.out.println("duar ERROR");
        }
    }

    public static boolean isFileExist(String fileName){
        FileReader file = null;
        try {
            file = new FileReader("test/" + fileName);
        } catch (FileNotFoundException notFound){
            return false;
        }
        return true;
    }
}