package com.example.prototype.ApartmentSearch;

import com.example.prototype.DBClasess.Apartment;

import java.util.ArrayList;

public class ApartmentSearch {
    private ArrayList<Apartment> apartments;

    public ApartmentSearch(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
    }

    public ArrayList<Apartment> searchApartment(Apartment apartment) {
        ArrayList<Apartment> relevantApartment = new ArrayList<>();

        for (int i = 0; i < apartments.size() ; i++) {
            if (!apartment.getCityName().isEmpty()) {
                String cityName = apartment.getCityName();
                if (!apartments.get(i).getCityName().equals(cityName)) {
                    continue;//go to next apartment
                }
            }

            if (!apartment.getStreet().isEmpty()) {
                String streetName = apartment.getStreet();
                if (!apartments.get(i).getStreet().equals(streetName)) {
                    continue;//go to next apartment
                }
            }

            if (!apartment.getAirConditioning().equals(apartments.get(i).getAirConditioning())) {
                continue;
            }
            if (!apartment.getParking().equals(apartments.get(i).getParking())) {
                continue;
            }
            if (!apartment.getBalcony().equals(apartments.get(i).getBalcony())) {
                continue;
            }
            if (!apartment.getElevator().equals(apartments.get(i).getElevator())) {
                continue;
            }
            if (!apartment.getBars().equals(apartments.get(i).getBars())) {
                continue;
            }
            if (!apartment.getDisabledAcess().equals(apartments.get(i).getDisabledAcess())) {
                continue;
            }
            if (!apartment.getRenovated().equals(apartments.get(i).getRenovated())) {
                continue;
            }
            if (!apartment.getPets().equals(apartments.get(i).getPets())) {
                continue;
            }
            if (!apartment.getFurnished().equals(apartments.get(i).getFurnished())) {
                continue;
            }
            if (!apartment.getPanicRoom().equals(apartments.get(i).getPanicRoom())) {
                continue;
            }

            relevantApartment.add(apartments.get(i));
        }


        return relevantApartment;
    }
}
