/**
 * Created by timothybaba on 1/3/18.
 */
import java.io.Console;
import java.sql.*;

/**
 * linking Java with mySql database
 */

public class mySqlDatabase{
    private static String table = null;
    private static Connection conn;
    private static Statement stmt = null;


    public static void main(String[] args) {
        try {

            String host = args[0];
            String user = args[1];
            String database = args[2];

            Console console = System.console() ;

            char[] password = console.readPassword("Enter password: ");
            StringBuilder pass = new StringBuilder();
            for (char i : password) {
                pass.append(i);
            }
            conn = connect(host, user, pass.toString(), database);
            table = "student";

            //create table
            String query = "CREATE TABLE " + table + "(student_ID INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (student_ID), "
                    + "firstName varchar(255), lastName varchar(255))";
            stmt = conn.createStatement();
            stmt.execute(query);

            // query = "DESC " + table;
            // ResultSet rset = stmt.executeQuery(query);
            // showResults(rset);

            //Alter table - add column
            query = "ALTER TABLE " + table + " ADD COLUMN (age tinyInt UNSIGNED, gpa FLOAT (3, 2) UNSIGNED)";
            stmt.execute(query);

            //Alter table - drop column
            query = "ALTER TABLE "+ table + " DROP COLUMN firstName";
            stmt.execute(query);

            //insert data
            query = "INSERT into " + table + "(lastName, age, gpa) VALUES "
                + "(\"campbell\", 19, 3.79),"
                + "(\"Garcia\", 28, 2.37),"
                + "(\"Fuller\", 19, 3.18),"
                + "(\"Cooper\", 26, 2.13),"
                + "(\"Walker\", 27, 2.14),"
                + "(\"Griego\", 31, 2.10)";
            stmt.executeUpdate(query);

            //udate data
            query = "UPDATE " + table + " SET gpa = 3.41 WHERE lastName = \"Cooper\"";
            stmt.executeUpdate(query);

            //delete data
            query = "DELETE FROM " + table + " WHERE gpa <= 3.0";
            stmt.executeUpdate(query);

            query = "SELECT * FROM " + table;
            ResultSet rset = stmt.executeQuery(query);
            showResults(rset);


            conn.close();
            System.out.println("\nconnection ended");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    //host = localhost, user = root
    private static Connection connect(String host, String user, String pswd, String database) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/"
                    + database + "?user=" + user + "&password=" + pswd);
            if (conn != null) {
                System.out.println("\nconnection established\n");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        return conn;
    }


    private static void showResults(ResultSet rSet) {
        try {
            ResultSetMetaData rsmd = rSet.getMetaData();
            int numColumns = rsmd.getColumnCount();
            String resultString  = null;
            if (numColumns > 0) {
                System.out.println("Table: " + table + "\n======================================================");
                for (int colNum = 1; colNum <= numColumns; colNum++) {
                   // resultString += rsmd.getColumnLabel(colNum) + " ";
                    System.out.format("%-15s", rsmd.getColumnLabel(colNum) + " ");
                }
            }
            System.out.println("\n======================================================");

            while (rSet.next()) {
                resultString = "";
                for (int colNum = 1; colNum <= numColumns; colNum++) {
                    String column = rSet.getString(colNum);
                    if (column != null) {
                        //resultString += column + "\t";
                        System.out.format("%-15s", column);
                    }
                }
                System.out.println(resultString + '\n' +
                       "-------------------------------------------------------");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
