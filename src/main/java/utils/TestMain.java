package utils;

import structs.Pair;
import users.Employee;

import java.util.ArrayList;

public class TestMain {

    public static void main(String[] args) {

       Employee e = Helper.getEmployeeFromSIN("11111116");
        assert e != null;
        ArrayList<Pair<Integer, Integer>> roomResults = new ArrayList<>();
        e.getRooms(roomResults, "2021-06-03", "2021-06-04", 100, 300, "sea", "single", "false", "TV");

        for (Pair<Integer, Integer> room : roomResults) {
           //  Helper.println(room.toString());
        }




    }

}
