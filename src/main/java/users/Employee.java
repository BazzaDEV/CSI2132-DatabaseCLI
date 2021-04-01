package users;

import structs.Address;
import structs.Name;

import java.text.NumberFormat;

public class Employee extends User {

    private int salary;
    private String role;

    public Employee(int sinNumber, Name name, Address address, int salary, String role) {
        super(sinNumber, name, address);
        this.salary = salary;
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append(super.toString()).append("\n")
                .append("Salary: ").append(NumberFormat.getCurrencyInstance().format(salary)).append("\n")
                .append("Role: ").append(role);

        return strB.toString().trim();
    }

    public int getSalary() {
        return salary;
    }

    public String getRole() {
        return role;
    }
}
