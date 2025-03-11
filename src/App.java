import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App {

    public static int choice = 0;

    public static void main(String[] args) throws Exception {
        authenticationView();
    }

    public static void clearDisplay() {
        // System.out.print("\033\143"); // only for linux system
          try {
        if (System.getProperty("os.name").contains("Windows")){
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        else{
            Runtime.getRuntime().exec("clear");
            System.out.print("\033\143");
          }
    } catch (IOException | InterruptedException ex) {}
    }

    public static void authenticationView(){
        clearDisplay();
        System.out.println("\n===== ToDo CLI App =====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int loginChoice = scanner.nextInt();
        if (loginChoice == 1) {
            registerView();
        } else if (loginChoice == 2) {
            loginView();
        } else if(loginChoice == 3) {
            clearDisplay();            
        }else{
            clearDisplay();
            delay("Invalid Input. Please try again...",3);
            authenticationView();
        }
    }





    public static void delay(String message, int seconds) {
        try {
            System.out.println(message);
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    public static void registerView(){
        System.out.println("Registering");
    }

    public static void loginView(){
        System.out.println("Login");
    }



}
