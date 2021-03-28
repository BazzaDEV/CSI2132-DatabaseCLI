package structs;

public class Name {

    private String firstName;
    private String middleName;
    private String lastName;

    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append(firstName).append(" ");

        if (middleName!=null) {
            strB.append(middleName).append(" ");
        }

        strB.append(lastName);

        return strB.toString().trim();
    }
}
