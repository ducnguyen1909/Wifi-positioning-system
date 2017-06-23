package com.example.nguyenvan.getwifiinfo;

/**
 * Created by Nguyenvan on 5/3/2017.
 */

public class Building {

    private String nameBuilding;
    private Double distance1;
    private Double distance2;
    private Double distance3;
    private Double distance4;

    public Building(String nameBuilding, Double distance1, Double distance2, Double distance3, Double distance4) {
        this.nameBuilding = nameBuilding;
        this.distance1 = distance1;
        this.distance2 = distance2;
        this.distance3 = distance3;
        this.distance4 = distance4;
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public Double getDistance1() {
        return distance1;
    }

    public void setDistance1(Double distance1) {
        this.distance1 = distance1;
    }

    public Double getDistance2() {
        return distance2;
    }

    public void setDistance2(Double distance2) {
        this.distance2 = distance2;
    }

    public Double getDistance3() {
        return distance3;
    }

    public void setDistance3(Double distance3) {
        this.distance3 = distance3;
    }

    public Double getDistance4() {
        return distance4;
    }

    public void setDistance4(Double distance4) {
        this.distance4 = distance4;
    }
}
