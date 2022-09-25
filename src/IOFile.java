import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IOFile{

    public static int getRow(String fileName){
        FileReader file = null;
        try {
            file = new FileReader(fileName);
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
            file = new FileReader(fileName);
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

        colScan.close();

        return colVal;
    }

    public static void readFileMat(Matrix mat, String fileName, int row, int col){
        
        FileReader file = null;
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException notFound){
            System.out.println("File tidak ditemukan");
        }

        Scanner rowScan = new Scanner(file);

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                mat.setELMT(i, j, rowScan.nextFloat());
            }
        }
        rowScan.close();
    }

    public static void createEmptyFile(String fileName){
        try{
            File myFile = new File("test/" + fileName + ".txt");
            if(myFile.createNewFile()){
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exist");
            }
        } catch(IOException e){
            System.out.println("ERROR");
        }
    }

    public static void writeMatrix(String fileName, float[][] data){
        try {
            FileWriter myWrite = new FileWriter("test/" + fileName + ".txt");

            for (int i = 0; i < data.length; i++){
                for(int j = 0; j < data[i].length; j++){
                    myWrite.write(Float.toString(data[i][j]) + " ");
                }
                myWrite.write("\n");
            }
            myWrite.close();
        }
        catch (IOException e){
            System.out.println("duar ERROR");
        }

        
    }
}