package users;

import structs.Address;
import structs.Name;

import java.text.SimpleDateFormat;

public abstract class User {

    protected int sinNumber;
    protected Name name;
    protected Address address;

    public User(int sinNumber, Name name, Address address) {
        this.sinNumber = sinNumber;
        this.name = name;
        this.address = address;
    }

    public int getSinNumber() {
        return sinNumber;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append("SIN: ").append(sinNumber).append("\n")
                .append("Name: ").append(name).append("\n")
                .append("Address: ").append(address).append("\n");

        return strB.toString().trim();
    }
}
