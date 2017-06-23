package com.example.nguyenvan.getwifiinfo;

/**
 * Created by Nguyenvan on 5/8/2017.
 */

public class Wifi {

    private String name;
    private double distance;

    public Wifi(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
