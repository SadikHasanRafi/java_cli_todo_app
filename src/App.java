import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

public class App {

    public static int choice = 0;
    public static Connection connection = null;

    public static void main(String[] args) throws Exception {
        authenticationView();
        // testDatabase();
    }

    public static Connection getConnection() throws SQLException {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[] { "localhost" });
        dataSource.setPortNumbers(new int[] { 5432 });
        dataSource.setDatabaseName("todo_cli_java");
        dataSource.setUser("postgres"); 
        dataSource.setPassword("123456"); 
        Connection connection = dataSource.getConnection();
        return connection;
    }

    public static void clearDisplay() {
        // System.out.print("\033\143"); // only for linux system
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Runtime.getRuntime().exec("clear");
                // System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {

        }
    }

    public static void authenticationView() throws SQLException {
        clearDisplay();
        System.out.println("\n===== ToDo CLI App =====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int loginChoice = scanner.nextInt();
        System.out.println(loginChoice);
        if (loginChoice == 1) {
            registerView();
        } else if (loginChoice == 2) {
            loginView();
        } else if (loginChoice == 3) {
            clearDisplay();
        } else {
            clearDisplay();
            delay("Invalid Input. Please try again...", 3);
            authenticationView();
        }
    }

    public static void registerView() throws SQLException {
        clearDisplay();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== Register Account =====");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!checkEmail(email)) {
            clearDisplay();
            delay("Enter a valid email...", 2);
            registerView();
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            clearDisplay();
            delay("Passwords do not match. Please try again...", 1);
            System.out.println("Enter 'b' to go back.");
            System.out.println("Enter 't' to try again.");
            String back = scanner.nextLine();

            if ("b".equals(back)) {
                authenticationView();
            } else if (back.equals("t")) {
                registerView();
            } else {
                clearDisplay();
                delay("Invalid Input. Please try again...", 3);
                authenticationView();
            }
        } else {
            createNewUser(email, password);
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

    public static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void loginView() throws SQLException {
        clearDisplay();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Login =====");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!checkEmail(email)) {
            clearDisplay();
            delay("Enter a valid email...", 2);
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
            } else if (back.equals("t")) {
                loginView();
            } else {
                clearDisplay();
                delay("Invalid Input. Please try again...", 3);
                loginView();
            }
        }
    }

    public static void todoMainMenuView() throws SQLException {
        clearDisplay();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== To-Do List =====");
        System.out.println("1. Create a new task.");
        System.out.println("2. Show all tasks.");
        System.out.println("3. Sign out.");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("create a new task");
        } else if (choice == 2) {
            System.out.println("show all tasks");
        } else {
            authenticationView();
        }
    }

    public static void createNewUser(String email, String password) throws SQLException {
        System.out.println(email + " " + password);

        try {
            // todo start from here
            String sql = "INSERT INTO authenticate( email,password) VALUES (?, ?)";
            PreparedStatement st = getConnection().prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            int insertedRow = st.executeUpdate();
            System.out.println(insertedRow);
            getConnection().close();
        } catch (Exception e) {
            System.out.println("Exception occurred is" + e.getMessage());
        } finally {
            todoMainMenuView();
        }
        // System.out.println(insertedRow);
        // todoMainMenuView();
    }

}
