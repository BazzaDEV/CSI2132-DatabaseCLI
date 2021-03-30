package users;

import structs.Address;
import structs.Name;

public class Admin extends Employee {

    public static final String ROLE_NAME = "Database Administrator";

    public Admin(int sinNumber, Name name, Address address, int salary) {
        super(sinNumber, name, address, salary, ROLE_NAME);
    }

}
