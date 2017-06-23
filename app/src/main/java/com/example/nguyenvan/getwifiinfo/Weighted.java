package com.example.nguyenvan.getwifiinfo;

/**
 * Created by Nguyenvan on 5/17/2017.
 */

public class Weighted {

    private String nameBuilding;
    private double weight;

    public Weighted(String nameBuilding, double weight) {
        this.nameBuilding = nameBuilding;
        this.weight = weight;
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
