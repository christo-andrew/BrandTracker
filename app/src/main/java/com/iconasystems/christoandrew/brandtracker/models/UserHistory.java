package com.iconasystems.christoandrew.brandtracker.models;

import java.util.Date;

public class UserHistory {
    int id;
    Date checkInTime;
    Date checkOutTime;
    Date dateAdded;

    public UserHistory(int id, Date checkInTime, Date checkOutTime, Date dateAdded) {
        this.id = id;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
