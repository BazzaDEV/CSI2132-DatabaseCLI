package structs.hotel;

import structs.Address;

import java.util.List;

public class HotelBrand {

    private int brandID;
    private Address address;
    private int numHotels;
    private List<String> emailAddresses;
    private List<Integer> phoneNumbers;

    public HotelBrand(int brandID, Address address, int numHotels, List<String> emailAddresses, List<Integer> phoneNumbers) {
        this.brandID = brandID;
        this.address = address;
        this.numHotels = numHotels;
        this.emailAddresses = emailAddresses;
        this.phoneNumbers = phoneNumbers;
    }

    public int getBrandID() {
        return brandID;
    }

    public Address getAddress() {
        return address;
    }

    public int getNumHotels() {
        return numHotels;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public List<Integer> getPhoneNumbers() {
        return phoneNumbers;
    }
}
