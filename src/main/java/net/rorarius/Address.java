package net.rorarius;

public class Address {

    private String street;
    private String streetNumber;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Override
    public String toString() {
        return "{" +
                "\"" + (street != null ? street : "") + "\"" +
                ", \"" + (streetNumber != null ? streetNumber : "") + "\"" +
                "}";
    }
}
