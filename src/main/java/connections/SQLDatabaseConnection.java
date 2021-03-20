package connections;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class SQLDatabaseConnection {

    Connection db;

    public void load() {

        try {

            File myObj = new File("dbCLILogin.txt");
            Scanner sc = new Scanner(myObj);

            String username = sc.nextLine();
            String password = sc.nextLine();

            Class.forName("org.postgresql.Driver");

            db = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b07_g38",
                    username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }


}
