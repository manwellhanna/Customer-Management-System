import java.util.Date;

public class Customer {
    private  int customerID;
    private String customerFirstName;
    private String customerLastName;
    private String customerMiddleName;
    private String customerEmail;
    private Date DOB;
    private String customerAddress;
    private String phoneNumber;
    private String gender;
    private String country;


    public Customer (int customerID,String customerFirstName, String customerLastName,String customerMiddleName
            , String customerEmail,Date dob, String customerAddress
            , String phoneNumber, String gender, String country ){

        this.customerID = customerID;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerMiddleName = customerMiddleName;
        this.customerEmail = customerEmail;
        this.DOB = dob;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.country = country;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerMiddleName(String customerMiddleName) {
        this.customerMiddleName = customerMiddleName;
    }

    public String getCustomerMiddleName() {
        return customerMiddleName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
