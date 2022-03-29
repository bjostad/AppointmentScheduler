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

    private Customer(int ID, String name, String address, String postalCode, String phoneNumber, int divisionID, String division, int countryID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    private int getID() {
        return ID;
    }

    private String getName() {
        return name;
    }

    private String getAddress() {
        return address;
    }

    private String getPostalCode() {
        return postalCode;
    }

    private String getPhoneNumber() {
        return phoneNumber;
    }

    private int getDivisionID() {
        return divisionID;
    }

    private String getDivision() {
        return division;
    }

    private int getCountryID() {
        return countryID;
    }
}
