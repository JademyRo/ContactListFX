package ro.jademy.contactlistfx.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Contact {

    private String firstName;
    private String lastName;
    private Map<PhoneNumberType, String> phoneNumbers = new HashMap<>();
    private Address address;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = new Address();
    }

    public Contact(String firstName, String lastName, String phoneNumber) {
        this(firstName, lastName, phoneNumber, new Address());
    }

    public Contact(String firstName, String lastName, String phoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumbers.put(PhoneNumberType.HOME, phoneNumber);
        this.address = address;
    }

    public Contact(String firstName, String lastName, Map<PhoneNumberType, String> phoneNumbers, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
        this.address = address;
    }

    public Contact(String firstName, String lastName, Map<PhoneNumberType, String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Map<PhoneNumberType, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<PhoneNumberType, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(PhoneNumberType type, String phoneNumber) {
        this.phoneNumbers.put(type, phoneNumber);
    }

    public void addPhoneNumber(String phoneNumber) {
        this.phoneNumbers.put(PhoneNumberType.HOME, phoneNumber);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) &&
                Objects.equals(lastName, contact.lastName) &&
                Objects.equals(phoneNumbers, contact.phoneNumbers) &&
                Objects.equals(address, contact.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumbers, address);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", address=" + address +
                '}';
    }
}
