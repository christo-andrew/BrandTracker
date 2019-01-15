package com.iconasystems.christoandrew.brandtracker.api.response;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("responseCode")
    private int responseCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public int getCode() {
        return responseCode;
    }

    public void setCode(int code) {
        this.responseCode = code;
    }
}
