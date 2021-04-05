package cli.misc;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import structs.booking.Booking;
import structs.hotel.HotelRoom;
import users.Customer;
import users.Employee;
import utils.Helper;
import utils.Vars;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomSearchMenu extends Menu {

    String startDate = null, endDate = null;
    String city = null, state = null;
    double minPrice = 0, maxPrice = 0;
    String view = null;
    String roomCapacity = null;
    boolean isExtendable = false;
    String amenity = null;

    private ArrayList<HotelRoom> roomResults;

    private SearchOption searchOption;

    public enum SearchOption {
        EMPLOYEE,
        EMPLOYEE_FOR_CUSTOMER,
        CUSTOMER;
    }

    public RoomSearchMenu(SearchOption searchOption) {
        this.searchOption = searchOption;
    }

    @Override
    public void start() {

        switch(searchOption) {
            case CUSTOMER:
            case EMPLOYEE_FOR_CUSTOMER:
                searchAndBook();
                break;
            case EMPLOYEE:
                searchOnly();
                break;
            default:
                break;
        }

    }

    private void searchOnly() {

        boolean F = false;
        while (!F) {

            runSearch();

            boolean F2 = false;
            while (!F2) {

                String res = Helper.getInput("\nWould you like to:" +
                        "\n(1) Run another search" +
                        "\n(2) Return to main menu" +
                        "\n>> ");

                if (Helper.isValid(res, 2)) {
                    F2 = true;

                    if (res.equalsIgnoreCase("1")) { // Run another search


                    } else if (res.equalsIgnoreCase("2")) { // Go back to main menu
                        F = true;
                        cliManager.prevMenu();

                    }

                } else { // Invalid entry
                    Helper.println("\nInvalid entry - try again.");

                }

            }
        }

    }

    private void searchAndBook() {

        boolean F = false;
        while (!F) {

            runSearch();

            if (roomResults.size() >= 1 && !searchOption.equals(SearchOption.EMPLOYEE)) { // Found at least 1 room matching search criteria

                boolean F2 = false;
                while (!F2) {

                    String res = Helper.getInput("\nWould you like to:" +
                            "\n(1) Book a room" +
                            "\n(2) Run another search" +
                            "\n(3) Return to main menu" +
                            "\n>> ");

                    if (Helper.isValid(res, 3)) {
                        F2 = true;

                        if (res.equalsIgnoreCase("1")) { // Book a room
                            F = true;
                            bookRoom();

                        } else if (res.equalsIgnoreCase("2")) { // Run another search


                        } else if (res.equalsIgnoreCase("3")) { // Go back to main menu
                            F = true;
                            cliManager.prevMenu();

                        }

                    } else { // Invalid entry
                        Helper.println("\nInvalid entry - try again.");

                    }

                }

            } else { // Search results empty OR employee searching (do not present book option)
                boolean F3 = false;
                while (!F3) {

                    String res = Helper.getInput("\nWould you like to:" +
                            "\n(1) Run another search" +
                            "\n(2) Return to main menu" +
                            "\n>> ");

                    if (Helper.isValid(res, 2)) {
                        F3 = true;

                        if (res.equalsIgnoreCase("1")) { // Run another search


                        } else if (res.equalsIgnoreCase("2")) { // Go back to main menu
                            F = true;
                            cliManager.prevMenu();

                        }

                    } else { // Invalid entry
                        Helper.println("\nInvalid entry - try again.");

                    }

                }

            }
        }

    }

    private void runSearch() {

        roomResults = new ArrayList<>();

        if (searchOption.equals(SearchOption.CUSTOMER)) {
            Helper.println("\n" + Vars.DIVIDER_EQUALS +
                    "\n" + StringUtils.center("Customer >> Room Search", Vars.DIVIDER_EQUALS.length()) +
                    "\n" + Vars.DIVIDER_EQUALS);

        } else if (searchOption.equals(SearchOption.EMPLOYEE)) {
            Helper.println("\n" + Vars.DIVIDER_EQUALS +
                    "\n" + StringUtils.center("Employee >> View Room Availability", Vars.DIVIDER_EQUALS.length()) +
                    "\n" + Vars.DIVIDER_EQUALS);

        } else if (searchOption.equals(SearchOption.EMPLOYEE_FOR_CUSTOMER)) {
            Helper.println("\n" + Vars.DIVIDER_EQUALS +
                    "\n" + StringUtils.center("Customer Check-In >> Room Search", Vars.DIVIDER_EQUALS.length()) +
                    "\n" + Vars.DIVIDER_EQUALS);

        }

        // Start and end date
        selectDateRange();

        if (searchOption.equals(SearchOption.CUSTOMER)) {
            // City and state
            selectCityState();
        }

        // Price range
        selectPriceRange();

        // View options (sea or mountain)
        selectView();

        // Room capacity (single, double)
        selectRoomCapacity();

        // Extendable room
        selectExtendable();

        // Amenities
        selectAmenities();

        // Print search results
        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Room Search >> Results", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);



        switch(searchOption) {
            case CUSTOMER:
                Customer c = (Customer) cliManager.getUser();
                roomResults = (ArrayList<HotelRoom>) c.getRooms(startDate, endDate, city, state, minPrice, maxPrice, view, roomCapacity, Boolean.toString(isExtendable), amenity);
                break;

            case EMPLOYEE:
            case EMPLOYEE_FOR_CUSTOMER:
                Employee e = (Employee) cliManager.getUser();
                roomResults = (ArrayList<HotelRoom>) e.getRooms(startDate, endDate, minPrice, maxPrice, view, roomCapacity, Boolean.toString(isExtendable), amenity);
                break;

            default:
                break;
        }

    }

    private void bookRoom() {

        Helper.println("\nPlease enter the hotel ID and room number for the room you'd like to book " +
                "\nby consulting the search results above.");

        boolean FLAG = false;
        while (!FLAG) {

            String hID = Helper.getInput("\n>> Hotel ID: ");
            String rNum = Helper.getInput(">> Room Number: ");

            if (Helper.isDigitsOnly(hID) && Helper.isDigitsOnly(rNum)) {

                int hotelID = Integer.parseInt(hID);
                int roomNumber = Integer.parseInt(rNum);

                HotelRoom hotelRoom = roomResults.stream()
                        .filter(room -> room.getHotelID() == hotelID && room.getRoomNumber() == roomNumber)
                        .findFirst()
                        .orElse(null);

                if (hotelRoom != null) { // Hotel ID + room number combination exists
                    FLAG = true;

                    Booking booking = new Booking(
                            null,
                            "scheduled",
                            hotelRoom.getRoomCapacity(),
                            hotelRoom.roomCapacityAsInt(),
                            Helper.stringToDate(startDate),
                            Helper.stringToDate(endDate));

                    Customer customer = null;

                    switch (searchOption) {
                        case CUSTOMER:
                            customer = (Customer) cliManager.getUser();
                            break;

                        case EMPLOYEE_FOR_CUSTOMER:
                            customer = cliManager.getCustomer();
                            break;

                        default:
                            break;
                    }

                    assert customer != null;
                    customer.bookRoom(hotelRoom, booking);

                } else { // Hotel ID and room number combination not in search results
                    Helper.println("\nThis combination of hotel ID and room number aren't in the search results - try again.");

                }

            } else { // Invalid entry
                Helper.println("\nMake sure both inputs are digits-only and try again.");
            }

        }
    }

    private void selectDateRange() {
        boolean FLAG = false;
        while (!FLAG) {

            startDate = Helper.getInput("\n>> Start date (YYYY-MM-DD): ");
            endDate = Helper.getInput(">> End date (YYYY-MM-DD): ");

            if (startDate.matches(Vars.DATE_REGEX) && endDate.matches(Vars.DATE_REGEX)) { // Date format valid

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date sDate = null;
                Date eDate = null;

                try {
                    sDate = dateFormat.parse(startDate);
                    eDate = dateFormat.parse(endDate);

                } catch (ParseException e) { // Issue parsing dates, try again
                    FLAG = false;
                    Helper.println("\nSorry, there was an issue with your dates." +
                            "\nMake sure they're in YYYY-DD-MM format and try again.");
                    continue;

                }

                if (sDate.before(eDate)) {
                    FLAG = true;

                    if (searchOption.equals(SearchOption.EMPLOYEE_FOR_CUSTOMER)) {
                        String tDate = Vars.DATE_FORMAT.format(cliManager.getCurrentDate());

                        if (!startDate.equalsIgnoreCase(tDate)) {
                            FLAG = false;
                            Helper.println("\nThe start date must be today (" + tDate + ") - try again.");

                        }

                    }

                } else { // Start date is AFTER end date, try again
                    FLAG = false;
                    Helper.println("\nThe start date is after the end date." +
                            "\nTry again.");

                }

            } else { // Invalid date format
                Helper.println("\nInvalid date format - try again.");

            }

        }
    }

    private void selectCityState() {
        boolean F4 = false;
        Helper.println("\nPlease enter your city and state you're searching for: ");

        while (!F4) {
            city = Helper.getInput("\n>> City: ");
            state = Helper.getInput(">> State: ");

            if (city.matches("[a-zA-Z]+") && state.matches("[a-zA-Z]+")) {
                F4 = true;

            } else { // Invalid entry
                Helper.println("\nPlease enter a valid city and state & try again.");

            }
        }
    }

    private void selectPriceRange() {
        Helper.println("\nPlease enter the price range.");

        boolean FLAG3 = false;
        while (!FLAG3) {

            String minPriceStr = Helper.getInput("\n>> Minimum price: ");
            String maxPriceStr = Helper.getInput(">> Maximum price: ");

            if (Helper.isDigitsOnly(minPriceStr) && Helper.isDigitsOnly(maxPriceStr)) {
                minPrice = Double.parseDouble(minPriceStr);
                maxPrice = Double.parseDouble(maxPriceStr);

                if (minPrice < maxPrice) {
                    FLAG3 = true;

                } else { // Invalid entry
                    Helper.println("\nThe minimum price must be less than the maximum price in your range.");

                }

            } else { // Invalid entry
                Helper.println("\nInvalid price range - make sure you are only entering digits and try again.");

            }

        }
    }

    private void selectView() {
        boolean FLAG4 = false;
        while (!FLAG4) {

            String res = Helper.getInput("\nWhich view would you like?" +
                    "\n(1) sea view" +
                    "\n(2) mountain view" +
                    "\n>> ");

            if (Helper.isValid(res, 2)) {
                FLAG4 = true;

                int choice = Integer.parseInt(res);
                view = Vars.VIEW_OPTIONS[choice-1];

            } else { // Invalid entry
                Helper.println("\nInvalid entry - try again.");

            }
        }
    }

    private void selectRoomCapacity() {
        boolean FLAG5 = false;
        while (!FLAG5) {

            String res = Helper.getInput("\nWhich room size would you like?" +
                    "\n(1) single" +
                    "\n(2) double" +
                    "\n>> ");

            if (Helper.isValid(res, 2)) {
                FLAG5 = true;

                int choice = Integer.parseInt(res);
                roomCapacity = Vars.ROOM_SIZE_OPTIONS[choice-1];

            } else { // Invalid entry
                Helper.println("\nInvalid entry - try again.");

            }
        }
    }

    private void selectExtendable() {
        boolean FLAG6 = false;
        while (!FLAG6) {

            String res = Helper.getInput("\nWould you like an extendable room?" +
                    "\n(Y)es" +
                    "\n(N)o" +
                    "\n>> ");

            if (Helper.multiCheck(res, new String[]{"Y", "N"})) {
                FLAG6 = true;

                if (res.equalsIgnoreCase("Y")) { // Yes
                    isExtendable = true;

                } else if (res.equalsIgnoreCase("N")) { // No
                    isExtendable = false;
                }

            } else { // Invalid entry
                Helper.println("\nInvalid entry - try again.");

            }
        }
    }

    private void selectAmenities() {
        boolean FLAG7 = false;
        while (!FLAG7) {
            String res = Helper.getInput("\nWhich hotel room amenity would you like?" +
                    "\n(1) Air Conditioning" +
                    "\n(2) TV" +
                    "\n(3) Fridge" +
                    "\n>> ");

            if (Helper.isValid(res, 3)) {
                FLAG7 = true;

                int choice = Integer.parseInt(res);
                amenity = Vars.AMENITY_OPTIONS[choice-1];

            } else { // Invalid entry
                Helper.println("\nInvalid entry - try again.");

            }

        }
    }


}
