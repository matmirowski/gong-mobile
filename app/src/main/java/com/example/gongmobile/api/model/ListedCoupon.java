package com.example.gongmobile.api.model;

public class ListedPromoCode {
    private final int id;
    private final String title;
    private final String description;

    private final int lifespanInMinutes;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ListedPromoCode(int id, String title, String description, int lifespanInMinutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lifespanInMinutes = lifespanInMinutes;
    }

    public int getLifespanInMinutes() {
        return lifespanInMinutes;
    }
}
