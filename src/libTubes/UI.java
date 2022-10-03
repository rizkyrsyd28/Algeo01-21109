package libTubes;
import java.util.*;

public class UI{
    public static Scanner sc = new Scanner(System.in);

    // Menampilkan main menu pada layar
    public static void printMainMenu(){
        System.out.println("MAIN MENU");
        System.out.println("    1. Sistem Persamaan Linier");
        System.out.println("    2. Determinan");
        System.out.println("    3. Matriks Balikan");
        System.out.println("    4. Interpolasi Polinom");
        System.out.println("    5. Interpolasi Bicubic");
        System.out.println("    6. Regresi Linier Berganda");
        System.out.println("    7. Pembesaran Citra");
        System.out.println("    8. Keluar");
    }

    // Menentukan berapa banyak jenis input yang bisa dimasukkan
    public static int Pilih(int n){

        int input = 0; boolean valid = false;
        System.out.println("Pilih fitur diatas: ");
        
        while(!valid){
            System.out.print(">> ");
            input = sc.nextInt();
            if (input > 0 && input <= n){
                valid = true;
            } else {
                System.out.println("Input pilihan invalid, Silakan input ulang ! ");
            }
        }
        return input;
    }

    // Menyimpan output ke dalam suatu file
    public static void simpan(String output) {
        boolean notValid = false;
        Scanner sc = new Scanner(System.in);
        while (!notValid) {
            Character c;
            System.out.print("Apakah jawabannya mau disimpan?(y/n)\n>> ");
            c = sc.next().charAt(0);
            if (c == 'y') {
                sc = new Scanner(System.in); 
                String fileName;
                System.out.print("Jawaban tersebut mau disimpan dengan nama file apa?\n>> ");
                fileName = sc.nextLine();
                IOFile.writeString(fileName, output);
                System.out.println("Jawabanmu telah disimpan!");
                notValid = true;
            }
            else if (c=='n') {
                System.out.println("Jawabanmu tidak disimpan!");
                notValid = true;
            }
            else {
                System.out.println("Input salah! Ulangi");
            }
        }
    }
}