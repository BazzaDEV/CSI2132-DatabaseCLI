package users;

import structs.Address;
import structs.Name;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer extends User {

    private Date registrationDate;

    public Customer(int sinNumber, Name name, Address address, Date registrationDate) {
        super(sinNumber, name, address);
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append(super.toString()).append("\n")
                .append("Registration Date: ").append(new SimpleDateFormat("MMMMMMMM dd yyyy").format(registrationDate));

        return strB.toString().trim();
    }
}
