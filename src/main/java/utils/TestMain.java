package utils;

import structs.Pair;
import structs.hotel.HotelRoom;
import users.Employee;

import java.util.List;

public class TestMain {

    public static void main(String[] args) {

       Employee e = Helper.getEmployeeFromSIN("11111116");
        assert e != null;
        List<HotelRoom> roomResults;
        roomResults = e.getRooms("2030-07-06", "2030-07-08", 1, 1000, "mountain", "single", "true", "air conditioning");

        for (HotelRoom room : roomResults) {
             Helper.println(room.toString());
        }




    }

}
