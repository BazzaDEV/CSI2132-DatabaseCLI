package users;

import structs.Address;
import structs.Name;

public class Admin extends Employee {

    public static final String ROLE_NAME = "DB Administrator";

    public Admin(int sinNumber, Name name, Address address, int salary) {
        super(sinNumber, name, address, salary, ROLE_NAME);
    }

    public Admin(Employee e) {
        super(e.sinNumber, e.name, e.address, e.getSalary(), ROLE_NAME);
    }

}
