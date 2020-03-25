package ro.jademy.contactlistfx.model;

import java.util.Objects;

public class Address {

    private String city;
    private String streetName;
    private String number;

    public Address() {
        this("", "", "");
    }

    public Address(String city) {
        this(city, "", "");
    }

    public Address(String city, String streetName, String number) {
        this.city = city;
        this.streetName = streetName;
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) &&
                Objects.equals(streetName, address.streetName) &&
                Objects.equals(number, address.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, streetName, number);
    }

    @Override
    public String toString() {
        return number + " " + streetName + ", " + city;
    }
}
