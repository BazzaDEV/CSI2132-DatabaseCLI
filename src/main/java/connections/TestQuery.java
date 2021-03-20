package connections;

import sun.nio.ch.sctp.SctpNet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TestQuery {

    public static void main(String[] args) {

        SQLDatabaseConnection db = new SQLDatabaseConnection();
        db.load();

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome!" +
                "\nAre you a (C)ustomer, (E)mployee, or (D)atabase Administrator?");

        String userType = sc.next();

        if (userType.equalsIgnoreCase("C")) {
            System.out.println("Hey there Customer! What would you like to do?");
            System.out.println("(1) ");
        } else if (userType.equalsIgnoreCase("E")) {
            System.out.println("Hey there Employee!");
            System.out.println();
        } else if (userType.equalsIgnoreCase("D")) {
            System.out.println("What's up, DB Admin?");
        }



    }
}
