package com.example.gongmobile.api.model;

public class BranchDetails {
    private final int id;
    private final String name;
    private final String slogan;
    private final String phoneNumber;
    private final String description;
    private final String image;
    private final int priceLow;
    private final int priceHigh;
    private final String openingTime;
    private final String closingTime;
    private final String status;
    private final Address address;

    public BranchDetails(int id, String name, String slogan, String phoneNumber, String description, String image,
               int priceLow, int priceHigh, String openingTime, String closingTime, String status, Address address) {
        this.id = id;
        this.name = name;
        this.slogan = slogan;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.image = image;
        this.priceLow = priceLow;
        this.priceHigh = priceHigh;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.status = status;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getPriceLow() {
        return priceLow;
    }

    public int getPriceHigh() {
        return priceHigh;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public String getStatus() {
        return status;
    }

    public Address getAddress() {
        return address;
    }
}
