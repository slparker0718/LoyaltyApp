package edu.psu.slparker.loyaltyapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;
    private int punchCardCount;
    private int loyaltyPoints;
    private List<String> loyaltyFeed;

    public User() {

    }

    public User(String firstName, String lastName, String username, String emailAddress, int punchCardCount, int loyaltyPoints, List<String> loyaltyFeed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.punchCardCount = punchCardCount;
        this.loyaltyPoints = loyaltyPoints;
        this.loyaltyFeed = loyaltyFeed;
    }

    public User(String firstName, String lastName, String username, String emailAddress, int punchCardCount, int loyaltyPoints) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.punchCardCount = punchCardCount;
        this.loyaltyPoints = loyaltyPoints;
        this.loyaltyFeed = new ArrayList<>();
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

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getPunchCardCount() {
        return punchCardCount;
    }

    public void setPunchCardCount(int punchCardCount) {
        this.punchCardCount = punchCardCount;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public List<String> getLoyaltyFeed() {
        return loyaltyFeed;
    }

    public void setloyaltyFeed(List<String> loyaltyFeed) {
        this.loyaltyFeed = loyaltyFeed;
    }
}
