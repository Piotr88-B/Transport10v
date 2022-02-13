package com.example.transport10v.model;

public class ModelCustomers {

    String Imie;
    String Nazwisko;
    String Miejscowosc;
    String Kodpocztowy;
    String Ulica;
    String NumerDomu;
    String Telefon;
    String Nip;

    public ModelCustomers(String imie, String nazwisko, String miejscowosc,
                          String kodpocztowy, String ulica, String numerDomu,
                          String telefon, String nip) {
        Imie = imie;
        Nazwisko = nazwisko;
        Miejscowosc = miejscowosc;
        Kodpocztowy = kodpocztowy;
        Ulica = ulica;
        NumerDomu = numerDomu;
        Telefon = telefon;
        Nip = nip;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public String getMiejscowosc() {
        return Miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        Miejscowosc = miejscowosc;
    }

    public String getKodpocztowy() {
        return Kodpocztowy;
    }

    public void setKodpocztowy(String kodpocztowy) {
        Kodpocztowy = kodpocztowy;
    }

    public String getUlica() {
        return Ulica;
    }

    public void setUlica(String ulica) {
        Ulica = ulica;
    }

    public String getNumerDomu() {
        return NumerDomu;
    }

    public void setNumerDomu(String numerDomu) {
        NumerDomu = numerDomu;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelfon(String telefon) {
        Telefon = telefon;
    }

    public String getNip() {
        return Nip;
    }

    public void setNip(String nip) {
        Nip = nip;
    }
}

