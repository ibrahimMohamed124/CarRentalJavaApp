package models;

public class RentsModel {
    private int id;
    private String carRegistration;
    private String customerName;
    private String rentDate;
    private String returnDate;
    private double totalPaid;

    public RentsModel(int id, String carRegistration, String customerName, String rentDate, String returnDate, double totalPaid) {
        this.id = id;
        this.carRegistration = carRegistration;
        this.customerName = customerName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.totalPaid = totalPaid;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getCarRegistration() { return carRegistration; }
    public String getCustomerName() { return customerName; }
    public String getRentDate() { return rentDate; }
    public String getReturnDate() { return returnDate; }
    public double getTotalPaid() { return totalPaid; }

    public void setCarRegistration(String carRegistration) { this.carRegistration = carRegistration; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setRentDate(String rentDate) { this.rentDate = rentDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public void setTotalPaid(double totalPaid) { this.totalPaid = totalPaid; }
}
