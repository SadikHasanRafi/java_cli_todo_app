import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    public static int choice = 0;

    public static void main(String[] args) throws Exception {
        authenticationView();
        // testDatabase();
    }

    // //! database connection test
    // public static void testDatabase () throws SQLException{
    //     String sql = "SELECT email from customers where firstname = 'John'";
    //     String url = "jdbc:postgresql://localhost:5432/my_test_db_1";
    //     String user = "postgres";
    //     String password = "123456";
    //     Connection con = DriverManager.getConnection(url, user, password); 
    //     Statement st = con.createStatement();
    //     ResultSet rs = st.executeQuery(sql);
    //     rs.next();
    //     String email = rs.getString(1);
    //     System.out.println(email);
    //     st.close();
    // }

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
        } catch (IOException | InterruptedException ex) {
            
        }
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

    public static void registerView(){
        clearDisplay();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== Register Account =====");
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!checkEmail(email)) {
            clearDisplay();
            delay("Enter a valid email...",2);  
            registerView();
          } 
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();   
        
        if (!password.equals(confirmPassword)) {
            clearDisplay();
            delay("Passwords do not match. Please try again...",1);
            System.out.println("Enter 'b' to go back.");
            System.out.println("Enter 't' to try again.");
            String back = scanner.nextLine();

            if ("b".equals(back)) {
                authenticationView();
            }else if(back.equals("t")){
                registerView();
            }else{
                clearDisplay();
                delay("Invalid Input. Please try again...",3);
                authenticationView();
            }
        }else{
            todoMainMenuView();
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

    public static boolean checkEmail(String email){
        if (email == null) { 
            return false; 
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern p = Pattern.compile(emailRegex); 
        Matcher m = p.matcher(email); 
        return m.matches(); 
    }

    public static void loginView(){
        clearDisplay();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Login =====");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!checkEmail(email)) {
            clearDisplay();
          delay("Enter a valid email...",2);  
          loginView();
        } 
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (email.length() > 0 && password.length() > 0) {
            System.out.println("Login successful! Redirecting...");
            delay("Redirecting to your To-Do list...", 1);
            todoMainMenuView();
        } else {
            System.out.println("Invalid username or password.");
            delay("Please try again...", 1);
            System.out.println("Enter 'b' to go back.");
            System.out.println("Enter 't' to try again.");
            String back = scanner.nextLine();
            if ("b".equals(back)) {
                authenticationView();
            }else if(back.equals("t")){
                loginView();
            }else{
                clearDisplay();
                delay("Invalid Input. Please try again...",3);
                loginView(); 
            }
        }
    }

    public static void todoMainMenuView(){
        clearDisplay();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== To-Do List =====");
        System.out.println("1. Create a new task.");
        System.out.println("2. Show all tasks.");
        System.out.println("3. Sign out.");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("create a new task");
        } else if(choice == 2) {
            System.out.println("show all tasks");
        }else{
            authenticationView();
        }
    }

}
