package com.example.gongmobile.api.model;

public class Address {
    private final String street;
    private final String city;
    private final int buildingNumber;
    private final int distanceFromUniversity;

    public Address(String street, String city, int buildingNumber, int distanceFromUniversity) {
        this.street = street;
        this.city = city;
        this.buildingNumber = buildingNumber;
        this.distanceFromUniversity = distanceFromUniversity;
    }

    public String getStreet() {
        return street;
    }


    public String getCity() {
        return city;
    }


    public int getBuildingNumber() {
        return buildingNumber;
    }


    public int getDistanceFromUniversity() {
        return distanceFromUniversity;
    }
}

