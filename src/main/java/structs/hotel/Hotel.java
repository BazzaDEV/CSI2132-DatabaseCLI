package structs.hotel;

import structs.Address;

import java.util.List;

public class Hotel {

    private int hotelID;
    private int starCategory;
    private int numberOfRooms;
    private Address address;
    private String emailAddress;
    private List<Integer> phoneNumbers;

    public Hotel(int hotelID, int starCategory, int numberOfRooms, Address address, String emailAddress, List<Integer> phoneNumbers) {
        this.hotelID = hotelID;
        this.starCategory = starCategory;
        this.numberOfRooms = numberOfRooms;
        this.address = address;
        this.emailAddress = emailAddress;
        this.phoneNumbers = phoneNumbers;
    }

    public int getHotelID() {
        return hotelID;
    }

    public int getStarCategory() {
        return starCategory;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<Integer> getPhoneNumbers() {
        return phoneNumbers;
    }
}
