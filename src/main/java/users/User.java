package users;

import structs.Address;
import structs.Name;

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
}
