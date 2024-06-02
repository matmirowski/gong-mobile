package com.example.gongmobile.api.model;

public class ListedBranch {
    private final int id;
    private final String name;
    private final String image;
    private final String slogan;
    private final Address address;

    public ListedBranch(int id, String name, String image, String slogan, Address address) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.slogan = slogan;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getImage() {
        return image;
    }


    public String getSlogan() {
        return slogan;
    }

    public Address getAddress() {
        return address;
    }
}

