import java.util.*;

class UI{
    static Scanner sc = new Scanner(System.in);

    public static void printMainMenu(){
        System.out.println("MAIN MENU");
        System.out.println("    1. Sistem Persamaan Linier");
        System.out.println("    2. Determinan");
        System.out.println("    3. Matriks Balikan");
        System.out.println("    4. Interpolasi Polinom");
        System.out.println("    5. Interpolasi Bicubic");
        System.out.println("    6. Regresi Linier Berganda");
        System.out.println("    7. Keluar");
    }


    public static int Pilih(int n){
        int input = 0; boolean valid = false;
        System.out.println("Pilih fitur diatas: ");
        
        while(!valid){
            System.out.print("> ");
            input = sc.nextInt();
            if (input > 0 && input <= n){
                valid = true;
            } else {
                System.out.println("Input pilihan invalid, Silakan input ulang ! ");
            }
        }

        return input;
    }

    public static boolean save2File(){
        int input;

        System.out.println("Simpan Hasil ke dalam File ?");
        System.out.print("1. Ya\n2. Tidak\n");
        input = Pilih(2);

        if (input == 1){
            return true;
        }
        else {
            return false;
        }
    }
}