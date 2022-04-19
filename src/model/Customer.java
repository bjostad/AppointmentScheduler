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


    /**
     * Customer constructor
     * @param ID
     * @param name
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionID
     * @param division
     * @param countryID
     * @param country
     */
    public Customer(int ID, String name, String address, String postalCode, String phoneNumber,
                    int divisionID, String division, int countryID, String country) {
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

    /**
     * Override string output to display Customer name
     * @return String
     */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * get Customer ID
     * @return int
     */
    public int getID() {
        return ID;
    }

    /**
     * get Customer name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * get Customer address
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * get Customer postal code
     * @return String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * get Customer phone number
     * @return String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * get Customer division ID
     * @return int
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * get Customer division
     * @return String
     */
    public String getDivision() {
        return division;
    }

    /**
     * get Customer country id
     * @return int
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * get Customer country
     * @return String
     */
    public String getCountry() {
        return country;
    }
}
