package com.example.prototype.DBClasess;

public class Apartment {
    private String cityName, street,airConditioning, parking, balcony, elevator;
    private String  bars, disabledAcess, renovated , furnished,panicRoom,pets;
    private String HomeOwnerMobilNumber;

    public Apartment(String cityName, String street, String airConditioning, String parking, String balcony,
                     String elevator, String bars, String disabledAcess, String renovated, String furnished,
                     String panicRoom, String pets, String homeOwnerMobilNumber) {
        this.cityName = cityName;
        this.street = street;
        this.airConditioning = airConditioning;
        this.parking = parking;
        this.balcony = balcony;
        this.elevator = elevator;
        this.bars = bars;
        this.disabledAcess = disabledAcess;
        this.renovated = renovated;
        this.furnished = furnished;
        this.panicRoom = panicRoom;
        this.pets = pets;
        HomeOwnerMobilNumber = homeOwnerMobilNumber;
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
        return disabledAcess;
    }

    public void setDisabledAcess(String disabledAcess) {
        this.disabledAcess = disabledAcess;
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
}
