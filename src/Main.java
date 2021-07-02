import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Vector;

/**
 * This is the main class
 */
public class Main {
    /**
     *
     * @param args -- pull or push that coming from Terminal
     * @throws FileNotFoundException -- If we can't find the file we will catch the exception
     */
    public static void main(String[] args) throws FileNotFoundException {
        String url, username, password, newpwd, email;
        ArrayList <String> confData = new ArrayList<String>();
        File confFile = new File(Paths.get("conf.cfg").toAbsolutePath().toString()); //getting data from our cfg file
        Scanner scanner = new Scanner(confFile);
        while (scanner.hasNextLine()) confData.add(scanner.nextLine());
        url = confData.get(0);
        username = confData.get(1);
        password = confData.get(2);

        switch (args[0]) {
            case "pull":
                System.out.println("Start pull...");
                try {
                    Class.forName("org.firebirdsql.jdbc.FBDriver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection(url, username, password)) {
                        Statement statement = conn.createStatement();
                        ResultSet resultSet;
                        resultSet = statement.executeQuery("SELECT PASSWD, EMAIL FROM IMAP_PASS");
                        File passwordsFile = new File("passwords.txt");
                        PrintWriter pw = new PrintWriter(passwordsFile);
                        while (resultSet.next()) {
                            Vector <String> dataDB = new Vector<>();
                            dataDB.add(resultSet.getNString(1));
                            dataDB.add(resultSet.getNString(2));
                            pw.println("Email: " + dataDB.get(1) + "; Password: " + dataDB.get(0));
                        }
                        pw.close();
                        System.out.println("Connection successful!");
                    } catch (Exception ex) {
                        System.out.println("Connection failed...");
                    }
                } catch (Exception ex) {
                    System.out.println("Connection failed...");
                }
                break;
            case "push":
                System.out.println("Start push...");
                try {
                    Class.forName("org.firebirdsql.jdbc.FBDriver").getDeclaredConstructor().newInstance();
                    try (Connection conn = DriverManager.getConnection(url, username, password)) {
                        File passwdFile = new File(Paths.get("passwords.txt").toAbsolutePath().toString());
                        ArrayList<String> passData = new ArrayList<String>();
                        Scanner scanner1 = new Scanner(passwdFile);
                        while (scanner1.hasNextLine()) {
                            passData.add(scanner1.nextLine());
                        }

                        for (int i = 0; i < passData.size(); i++) {
                            String[] substr = passData.get(i).split("; ");
                            email = substr[0].replace("Email: ", "");
                            Statement statement = conn.createStatement();
                            newpwd = substr[1].replace("Password: ", "");
                            statement.executeUpdate("UPDATE IMAP_PASS SET PASSWD = \'" + newpwd + "\' WHERE EMAIL = \'" + email + "\'");
                            statement.close();
                        }

                        System.out.println("Connection successful!");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } catch (Exception ex) {
                    System.out.println("Connection failed...");
                }
                break;
            default:
                System.out.println("Default");
                break;
        }
    }
}
