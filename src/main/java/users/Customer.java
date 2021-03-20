package users;

import structs.Address;
import structs.Name;

import java.util.Date;

public class Customer extends Person {

    private Date registrationDate;

    public Customer(int sinNumber, Name name, Address address) {
        super(sinNumber, name, address);
    }
}
