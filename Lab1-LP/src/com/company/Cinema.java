package com.company;

import java.util.ArrayList;

public class Cinema {
    private String name;
    private int seats;
    private ArrayList<String> currentFilmsList;

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Cinema(String name, int seats, ArrayList<String> currentFilmsList) {
        this.name = name;
        this.seats = seats;
        this.currentFilmsList = currentFilmsList;
    }

    public Cinema(String name, int seats) {
        this.name = name;
        this.seats = seats;
        this.currentFilmsList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCurrentFilmsList() {
        return currentFilmsList;
    }

    public void setCurrentFilmsList(ArrayList<String> currentFilmsList) {
        this.currentFilmsList = currentFilmsList;
    }
}
