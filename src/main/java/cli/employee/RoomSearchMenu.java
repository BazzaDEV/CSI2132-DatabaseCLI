package cli.employee;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import structs.Pair;
import users.Employee;
import utils.Helper;
import utils.Vars;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomSearchMenu extends Menu {

    private String startDate = null, endDate = null;
    private String city = null, state = null;
    private Double minPrice = 0.0, maxPrice = 0.0;
    private String view = null;
    private String roomCapacity = null;
    private Boolean isExtendable = false;
    private String amenity = null;

    private ArrayList<Pair<Integer, Integer>> roomResults;
    private String toggle;

    public RoomSearchMenu(String toggle) {
        this.toggle = toggle;
    }

    @Override
    public void start() {

        roomResults = new ArrayList<>();

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Employee >> Room Search", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        if (toggle.equalsIgnoreCase("E")) {
            search();
        } else if (toggle.equalsIgnoreCase("C")) {
            searchAndRent();
        }

    }

    private void search() {
        // Start and end date
        selectStartEndDate();

        // City and state
        selectCityState();

        // Min and max price
        selectPriceRange();

        // View options (sea or mountain)
        selectView();

        // Room capacity (single, double)
        selectRoomCapacity();

        // Extendable room
        selectIsExtendable();

        // Amenities
        selectAmenities();

        // Print search results
        printSearchResults();

        boolean FLAG8 = false;
        while (!FLAG8) {

            String res = Helper.getInput("\nWould you like to:" +
                    "\n(1) Run another search" +
                    "\n(2) Return to main menu" +
                    "\n>> ");

            if (Helper.isValid(res, 2)) {
                FLAG8 = true;

                if (res.equalsIgnoreCase("1")) { // Run another search
                    cliManager.popAndLoadMenu(new RoomSearchMenu("E"));

                } else if (res.equalsIgnoreCase("2")) { // Go back to main menu
                    cliManager.prevMenu();
                }

            } else { // Invalid entry
                Helper.println("\nInvalid entry - try again.");

            }

        }
    }

    private void searchAndRent() {
        boolean FLAG10 = false;
        while (!FLAG10) {
            // Start and end date
            Date todaysDate = cliManager.getCurrentDate();
            boolean F = false;
            while (!F) {
                selectStartEndDate();
                String tDate = Vars.DATE_FORMAT.format(todaysDate);
                if (!startDate.equalsIgnoreCase(tDate)) {
                    Helper.println("\nSelect today's date (" + tDate + ") as the start date.");
                } else {
                    F = true;
                }
            }

            // Min and max price
            selectPriceRange();

            // View options (sea or mountain)
            selectView();

            // Room capacity (single, double)
            selectRoomCapacity();

            // Extendable room
            selectIsExtendable();

            // Amenities
            selectAmenities();

            // Print search results
            printSearchResults();

            boolean FLAG8 = false;
            while (!FLAG8) {

                String res = Helper.getInput("\nWould you like to:" +
                        "\n(1) Book a room" +
                        "\n(2) Search again" +
                        "\n>> ");

                if (Helper.isValid(res, 2)) {
                    FLAG8 = true;

                    if (res.equalsIgnoreCase("1")) { // Book a room
                        FLAG10 = true;
                        cliManager.popMenu();

                    } else if (res.equalsIgnoreCase("2")) { // Search again

                    }

                } else { // Invalid entry
                    Helper.println("\nInvalid entry - try again.");

                }

            }
        }

    }

    /**************************************
                SELECT METHODS
    **************************************/

    private void selectStartEndDate() {
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
        boolean FLAG2 = false;
        while(!FLAG2) {

            city = Helper.getInput("\n>> City: ");
            state = Helper.getInput(">> State/Province: ");

            if (city.matches("[a-zA-Z]+") && state.matches("[a-zA-Z]+")) {
                FLAG2 = true;

            } else { // Invalid entry
                Helper.println("\nInvalid city and/or state - try again.");

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

    private void selectIsExtendable() {
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

    private void printSearchResults() {
        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Room Search >> Results", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        Employee e = (Employee) cliManager.getUser();

        boolean result = e.getRooms(roomResults, startDate, endDate, minPrice, maxPrice, view, roomCapacity, Boolean.toString(isExtendable), amenity);
    }

    public ArrayList<Pair<Integer, Integer>> getRoomResults() {
        return roomResults;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
