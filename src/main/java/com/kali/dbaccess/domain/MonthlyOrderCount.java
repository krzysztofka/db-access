package com.kali.dbaccess.domain;

public class MonthlyOrderCount {

    private int month;

    private int year;

    private long count;

    public MonthlyOrderCount(int month, int year, long count) {
        this.month = month;
        this.year = year;
        this.count = count;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public long getCount() {
        return count;
    }
}
