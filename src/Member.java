/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *Author: Matthew Vogler
 *Represents a member in the membership registration system.
 *Each member has a name, address, and phone number.
 *Instances of this class can be serialized to be stored or transferred
 */
import java.io.Serializable;

// Serializable interface indicates that instances of this class can be converted into byte streams
public class Member implements Serializable {
    // Member fields
    private String name;
    private String address;
    private String phoneNumber;

    // Constructor to initialize member fields
    public Member(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters for member fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Override toString method to provide custom string representation of the object
    @Override
    public String toString() {
        return "Name: " + name + ", Address: " + address + ", Phone Number: " + phoneNumber;
    }
}
