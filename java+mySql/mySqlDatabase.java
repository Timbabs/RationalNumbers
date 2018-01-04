/**
 * Created by timothybaba on 1/3/18.
 */
import java.io.Console;
import java.sql.*;

/**
 * linking Java with mySql database
 */

public class mySqlDatabase{
    private static String table= null;


    public static void main(String[] args) {
        String host = args[0];
        String user = args[1];
        String database = args[2];
        mySqlDatabase md = new mySqlDatabase();

        Console console = System.console() ;

        char[] password = console.readPassword("Enter password: ");
        StringBuilder pass = new StringBuilder();
        for (char i : password) {
            pass.append(i);
        }
        Connection conn = md.connect(host, user, pass.toString(), database);
        table = "student";
        md.create(conn, "CREATE TABLE " + table + "(student_ID INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (student_ID), "
                + "firstName varchar(255), lastName varchar(255))");

        AlterTable alt = new AlterTable();
        alt.addColumn(conn,  "ALTER TABLE " + table + " ADD COLUMN (age tinyInt UNSIGNED, gpa FLOAT (3, 2) UNSIGNED)");
        alt.dropColumn(conn, "ALTER TABLE "+ table + " DROP COLUMN firstName");
        //md.showColumns(conn);
        md.insert(conn, "INSERT STUDENT (lastName, age, gpa) VALUES(\"campbell\", 19, 3.79)");


        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }


    }


    //host = localhost, user = root
    private Connection connect(String host, String user, String pswd, String database) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/"
                    + database + "?user=" + user + "&password=" + pswd);
            if (conn != null) {
                System.out.println("connection established");
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

    //create table and show table structure
    private void create(Connection conn, String sqlCommand) {
        try {
            Statement stmt = conn.createStatement();
            boolean result  = stmt.execute(sqlCommand);
            System.out.print("\tTable creation result: " + result + "\t");
            System.out.println("(False is the expected result)\n");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //AlterTable
    static private class AlterTable {

        private void addColumn(Connection conn, String sqlCommand) {
            try {
                Statement stmt = conn.createStatement();
                boolean result = stmt.execute(sqlCommand);
                System.out.print("\tTable modification result: " + result + "\t");
                System.out.println("(False is the expected result)\n");
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                ex.printStackTrace();
            }
        }


        private void dropColumn(Connection conn, String sqlCommand) {
            try {

                Statement stmt = conn.createStatement();
                boolean result = stmt.execute(sqlCommand);
                System.out.print("\tTable modification result: " + result + "\t");
                System.out.println("(False is the expected result)\n");
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void showColumns(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SHOW COLUMNS FROM " + table);
            showResults(rset);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showResults(ResultSet rSet) {
        try {
            ResultSetMetaData rsmd = rSet.getMetaData();
            int numColumns = rsmd.getColumnCount();
            String resultString  = null;
            if (numColumns > 0) {
                resultString = "Table: " + table + "\n ======================================================\n";
                for (int colNum = 1; colNum <= numColumns; colNum++) {
                    resultString += rsmd.getColumnLabel(colNum) + " ";
                }
                System.out.println(resultString);
                System.out.println("=====================================================================");
            }

            while (rSet.next()) {
                resultString = "";
                for (int colNum = 1; colNum < numColumns; colNum++) {
                    String column = rSet.getString(colNum);
                    if (column != null) {
                        resultString += column + "\t";
                    }
                }
                System.out.println(resultString + '\n' +
                "--------------------------------------------------------------------------------------");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void insert(Connection conn, String sqlCommand) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            int rowCount = stmt.executeUpdate(sqlCommand);
            showValues(conn);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    private void showValues(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM Student");
            showResults(rset);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
