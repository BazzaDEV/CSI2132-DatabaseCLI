package structs;

public class Address {

    private int street_number;
    private String street_name;
    private Integer apartment_number;
    private String city;
    private String state;
    private String zip;

    public Address(int street_number, String street_name, Integer apartment_number, String city, String state, String zip) {
        this.street_number = street_number;
        this.street_name = street_name;
        this.apartment_number = apartment_number;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append(street_number).append(" ").append(street_name).append(", ");

        if (apartment_number!=null) {
            strB.append("APT #").append(apartment_number).append(", ");
        }

        strB.append(city).append(", ").append(state).append(" ").append(zip);

        return strB.toString().trim();
    }
}
