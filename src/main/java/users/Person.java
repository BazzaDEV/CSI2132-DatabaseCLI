package users;

import structs.Address;
import structs.Name;

public abstract class Person {

    private int sinNumber;
    private Name name;
    private Address address;

    public Person(int sinNumber, Name name, Address address) {
        this.sinNumber = sinNumber;
        this.name = name;
        this.address = address;
    }
}
