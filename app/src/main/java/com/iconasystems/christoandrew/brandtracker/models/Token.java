package com.iconasystems.christoandrew.brandtracker.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tokens")
public class Token {
    @Expose(serialize = false)
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("token")
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


}
