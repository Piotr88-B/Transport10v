package com.example.transport10v.model;

public class ModelArt {
    private final int ID;
    private final int Nr_art;
    private final String Nazwa_art;
    private final int Waga_Art;

    public ModelArt(int ID, int nr_art, String nazwa_art, int waga_Art) {
        this.ID = ID;
        Nr_art = nr_art;
        Nazwa_art = nazwa_art;
        Waga_Art = waga_Art;
    }

    public int getID() {
        return ID;
    }

    public int getNr_art() {
        return Nr_art;
    }

    public String getNazwa_art() {
        return Nazwa_art;
    }

    public int getWaga_Art() {
        return Waga_Art;
    }
}
