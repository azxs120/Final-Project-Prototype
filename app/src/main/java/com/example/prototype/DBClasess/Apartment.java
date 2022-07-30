package com.example.prototype.DBClasess;

import java.io.Serializable;
import java.util.ArrayList;

public class Apartment implements Serializable {
    private String cityName, street, airConditioning, parking, balcony, elevator;
    private String bars, disabledAccess, renovated, furnished, panicRoom, pets;
    private String HomeOwnerMobilNumber;


    private ArrayList<Boolean> q = new ArrayList<>();

    public Apartment(String cityName, String street, String airConditioning, String parking, String balcony,
                     String elevator, String bars, String disabledAccess, String renovated, String furnished,
                     String panicRoom, String pets, String homeOwnerMobilNumber) {
        this.cityName = cityName;
        this.street = street;
        this.airConditioning = airConditioning;
        this.parking = parking;
        this.balcony = balcony;
        this.elevator = elevator;
        this.bars = bars;
        this.disabledAccess = disabledAccess;
        this.renovated = renovated;
        this.furnished = furnished;
        this.panicRoom = panicRoom;
        this.pets = pets;

        HomeOwnerMobilNumber = homeOwnerMobilNumber;

        if (airConditioning.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (balcony.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (bars.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (disabledAccess.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (elevator.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (furnished.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (panicRoom.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (parking.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (pets.equals("true"))
            q.add(true);
        else
            q.add(false);
        if (renovated.equals("true"))
            q.add(true);
        else
            q.add(false);

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(String airConditioning) {
        this.airConditioning = airConditioning;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getBars() {
        return bars;
    }

    public void setBars(String bars) {
        this.bars = bars;
    }

    public String getDisabledAcess() {
        return disabledAccess;
    }

    public void setDisabledAcess(String disabledAcess) {
        this.disabledAccess = disabledAcess;
    }

    public String getRenovated() {
        return renovated;
    }

    public void setRenovated(String renovated) {
        this.renovated = renovated;
    }

    public String getFurnished() {
        return furnished;
    }

    public void setFurnished(String furnished) {
        this.furnished = furnished;
    }

    public String getPanicRoom() {
        return panicRoom;
    }

    public void setPanicRoom(String mamad) {
        this.panicRoom = mamad;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getHomeOwnerMobilNumber() {
        return HomeOwnerMobilNumber;
    }

    public void setHomeOwnerMobilNumber(String homeOwnerMobilNumber) {
        HomeOwnerMobilNumber = homeOwnerMobilNumber;
    }

    public ArrayList<Boolean> getQ() {
        return q;
    }
}
