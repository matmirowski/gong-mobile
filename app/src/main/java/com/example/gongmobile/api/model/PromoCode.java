package com.example.gongmobile.api.model;

public class PromoCode {
    private final String code;
    private final String expiresAt;

    public PromoCode(String code, String expiresAt) {
        this.code = code;
        this.expiresAt = expiresAt;
    }

    public String getCode() {
        return code;
    }

    public String getExpiresAt() {
        return expiresAt;
    }
}
