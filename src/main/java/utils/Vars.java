package utils;

import structs.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Vars {

    public static final String EXIT_APP_KEYWORD = "```";
    public static final String PREV_MENU_KEYWORD = "`";

    public static final String DIVIDER_EQUALS = "=========================================";

    public static final String DIVIDER_DASH = "---------------------------------";
    public static final String DIVIDER_DASH_LONG = "---------------------------------------------";

    public static final String DIVIDER_ASTERICK = "*******************************";
    public static final String DIVIDER_ASTERICK_LONG = "********************************************";

    public static final String DIVIDER_RED_SQUARE_LONG = Helper.repeat(Emoji.RED_SQUARE, 30);

    public static final String[] YES_NO_OPTIONS = new String[] {"Y", "N"};

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public static final String[] VIEW_OPTIONS = new String[]{"sea", "mountain"};
    public static final String[] ROOM_SIZE_OPTIONS = new String[]{"single", "double"};
    public static final String[] AMENITY_OPTIONS = new String[] {"air conditioning", "TV", "fridge"};

    public static final List<Pair<String, String>> RELATIONAL_SCHEMA = new ArrayList<>();

    public static final String[]TABLES = new String[]{"hotelbrand", "hotelbrandphonenum", "hotelbrandemail",
            "brandchain", "hotel", "hotelphonenum", "worksfor", "employee", "supervises",
            "hotelroom", "hotelroomamenities", "booksfor", "customer",
            "cancreate", "booking", "transformsinto", "payment", "renting"};

    public static final String[]cols = new String[] {
            "brand_ID, street_number, 'street_name', apt_number, 'city', 'state', zip, num_hotels", //hotelbrand
            "brand_ID, phone_number(10)", //hotelbrandphonenum
            "brand_ID, 'email_address'", //hotelbrandemail
            "hotel_ID, brand_ID", //brandchain
            "hotel_ID, star_category, number_of_rooms, 'street_number', 'street_name', 'city', 'state_name', 'zip(6)','email_address'", //hotel
            "hotel_ID, phone_number", //hotelphonenum
            "sin_number, hotel_ID", //worksfor
            "sin_number, 'first_name', 'middle_name', 'last_name', street_number, 'street_name',apt_number, 'city', 'state', 'zip', salary, 'role'", //employee
            "manager_ID, supervisee_ID", //supervises
            "hotel_ID, room_number, price, 'room_capacity', 'view', 'is_extendable', 'room_status'", //hotelroom
            "hotel_ID, room_number, 'amenity'", //hotelroomamenities
            "booking_ID, room_number, hotel_ID", //booksfor
            "sin_number, 'first_name', 'middle_name', 'last_name', 'street_number', 'street_name',apt_number, 'city', 'state', 'zip', 'date_of_registration YYYY-MM-DD'", //customer
            "booking_ID, sin_number", //cancreate
            "default (booking_ID), 'status', 'room_type', num_occupants, 'start_date YYYY-MM-DD', 'end_date YYYY-MM-DD'", //booking
            "booking_ID, renting_ID", //transformsinto
            "renting_ID, default (transaction_ID) , 'due_date YYYY-MM-DD', amount, 'received_on YYYY-MM-DD'", //payment
            "default (renting_ID), 'status', balance" //renting
    };

    public static List<Pair<String, String>> getSchema() {

        if (RELATIONAL_SCHEMA.size()==0) {
            RELATIONAL_SCHEMA.add(new Pair<>("HotelBrand", "brand_ID, street_number, 'street_name', apt_number, 'city', 'state', zip, num_hotels"));
            RELATIONAL_SCHEMA.add(new Pair<>("HotelBrandPhoneNum", "brand_ID, phone_number(10)"));
            RELATIONAL_SCHEMA.add(new Pair<>("HotelBrandEmail", "brand_ID, 'email_address'"));
            RELATIONAL_SCHEMA.add(new Pair<>("BrandChain", "hotel_ID, brand_ID"));
            RELATIONAL_SCHEMA.add(new Pair<>("Hotel", "hotel_ID, star_category, number_of_rooms, 'street_number', 'street_name', 'city', 'state_name', 'zip(6)','email_address'"));
            RELATIONAL_SCHEMA.add(new Pair<>("HotelPhoneNum", "hotel_ID, phone_number"));
            RELATIONAL_SCHEMA.add(new Pair<>("WorksFor", "sin_number, hotel_ID"));
            RELATIONAL_SCHEMA.add(new Pair<>("Employee", "sin_number, 'first_name', 'middle_name', 'last_name', street_number, 'street_name', apt_number, 'city', 'state_name', 'zip', salary, 'role'"));
            RELATIONAL_SCHEMA.add(new Pair<>("Supervises", "manager_ID, supervisee_ID"));
            RELATIONAL_SCHEMA.add(new Pair<>("HotelRoom", "hotel_ID, room_number, price, 'room_capacity', 'view', 'is_extendable', 'room_status'"));
            RELATIONAL_SCHEMA.add(new Pair<>("HotelRoomAmenities", "hotel_ID, room_number, 'amenity'"));
            RELATIONAL_SCHEMA.add(new Pair<>("BooksFor", "booking_ID, room_number, hotel_ID"));
            RELATIONAL_SCHEMA.add(new Pair<>("Customer", "sin_number, 'first_name', 'middle_name', 'last_name', 'street_number', 'street_name', apt_number, 'city', 'state_name', 'zip', 'date_of_registration (YYYY-MM-DD)'"));
            RELATIONAL_SCHEMA.add(new Pair<>("CanCreate", "booking_ID, sin_number"));
            RELATIONAL_SCHEMA.add(new Pair<>("Booking", "DEFAULT (booking_ID), 'status', 'room_type', num_occupants, 'start_date (YYYY-MM-DD)', 'end_date (YYYY-MM-DD)'"));
            RELATIONAL_SCHEMA.add(new Pair<>("TransformsInto", "booking_ID, renting_ID"));
            RELATIONAL_SCHEMA.add(new Pair<>("Payment", "renting_ID, DEFAULT (transaction_ID), 'due_date (YYYY-MM-DD)', amount, 'received_on (YYYY-MM-DD)'"));
            RELATIONAL_SCHEMA.add(new Pair<>("Renting", "DEFAULT (renting_ID), 'status', balance"));

        }

        return RELATIONAL_SCHEMA;
    }
}
