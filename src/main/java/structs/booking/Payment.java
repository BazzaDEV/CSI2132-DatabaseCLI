package structs.booking;

import java.util.Date;

public class Payment {

    private int rentingID;
    private Integer transactionID;
    private Date dueDate;
    private double amount;
    private Date receivedOn;

    public Payment(int rentingID, Integer transactionID, Date dueDate, double amount, Date receivedOn) {
        this.rentingID = rentingID;
        this.transactionID = transactionID;
        this.dueDate = dueDate;
        this.amount = amount;
        this.receivedOn = receivedOn;
    }

    public int getRentingID() {
        return rentingID;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public Date getReceivedOn() {
        return receivedOn;
    }
}
