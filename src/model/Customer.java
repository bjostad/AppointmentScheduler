package model;

/**
 * @author BJ Bjostad
 */
public class Customer {

    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private String division;
    private int countryID;
    private String country;



    public Customer(int ID, String name, String address, String postalCode, String phoneNumber, int divisionID, String division, int countryID, String country) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
        this.country = country;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getCountry() {
        return country;
    }
}
