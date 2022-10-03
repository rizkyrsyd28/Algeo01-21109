import src.libTubes.*;

public class App{
    public static void main(String[] args){
   
        boolean run = true; int mode;
        System.out.println("""

▄▄▄▄      █    ██  ██ ▄█▀▄▄▄       ███▄    █     ▄▄▄▄    ▄▄▄██▀▀▀▒█████   ██▀███   ██ ▄█▀▄▄▄      
▓█████▄  ███  ▓██▒ ██▄█▒▒████▄     ██ ▀█   █    ▓█████▄    ▒██  ▒██▒  ██▒▓██ ▒ ██▒ ██▄█▒▒████▄    
▒██▒ ▄██ ▓██  ▒██░▓███▄░▒██  ▀█▄  ▓██  ▀█ ██▒   ▒██▒ ▄██   ░██  ▒██░  ██▒▓██ ░▄█ ▒▓███▄░▒██  ▀█▄  
▒██░█▀   ▓▓█  ░██░▓██ █▄░██▄▄▄▄██ ▓██▒  ▐▌██▒   ▒██░█▀   ██▄██▓ ▒██   ██░▒██▀▀█▄  ▓██ █▄░██▄▄▄▄██ 
░▓█  ▀█▓ ▒▒█████▓ ▒██▒ █▄▓█   ▓██▒▒██░   ▓██░   ░▓█  ▀█▓ ▓███▒  ░ ████▓▒░░██▓ ▒██▒▒██▒ █▄▓█   ▓██▒
░▒▓███▀▒ ░▒▓▒ ▒ ▒ ▒ ▒▒ ▓▒▒▒   ▓▒█░░ ▒░   ▒ ▒    ░▒▓███▀▒ ▒▓▒▒░  ░ ▒░▒░▒░ ░ ▒▓ ░▒▓░▒ ▒▒ ▓▒▒▒   ▓▒█░
▒░▒   ░  ░░▒░ ░ ░ ░ ░▒ ▒░ ▒   ▒▒ ░░ ░░   ░ ▒░   ▒░▒   ░  ▒ ░▒░    ░ ▒ ▒░   ░▒ ░ ▒░░ ░▒ ▒░ ▒   ▒▒ ░
 ░    ░   ░░░ ░ ░ ░ ░░ ░  ░   ▒      ░   ░ ░     ░    ░  ░ ░ ░  ░ ░ ░ ▒    ░░   ░ ░ ░░ ░  ░   ▒   
 ░          ░     ░  ░        ░  ░         ░     ░       ░   ░      ░ ░     ░     ░  ░        ░  ░
        """);
        while (run){
            UI.printMainMenu();
            mode = UI.Pilih(8);
            switch (mode){
                case 1:
                        SPL.driverSPL();
                        break;
                case 2:
                        Matrix.driverDeterminan();
                        break;
                case 3: 
                        Matrix.driverInverse();
                        break;
                case 4:
                        SPL.driverInterpolPolinom();
                        break;
                case 5:
                        Bicubic.driverBicubic();
                        break;
                case 6: 
                        Regresi.MultiRegresi();
                        break;
                case 7:
                        ImageResize.driverImageResize();
                        break;
                case 8:
                        run = false;
            }
            System.out.println("\n====================================================================================\n");
        }
        System.out.println("""
        ▄▄▄      ▓█████▄  ██▓ ▒█████    ██████ 
        ▒████▄    ▒██▀ ██▌▓██▒▒██▒  ██▒▒██    ▒ 
        ▒██  ▀█▄  ░██   █▌▒██▒▒██░  ██▒░ ▓██▄   
        ░██▄▄▄▄██ ░▓█▄   ▌░██░▒██   ██░  ▒   ██▒
         ▓█   ▓██▒░▒████▓ ░██░░ ████▓▒░▒██████▒▒
         ▒▒   ▓▒█░ ▒▒▓  ▒ ░▓  ░ ▒░▒░▒░ ▒ ▒▓▒ ▒ ░
          ▒   ▒▒ ░ ░ ▒  ▒  ▒ ░  ░ ▒ ▒░ ░ ░▒  ░ ░
          ░   ▒    ░ ░  ░  ▒ ░░ ░ ░ ▒  ░  ░  ░  
              ░  ░   ░     ░      ░ ░        ░  
                   ░                            """);
    }
}