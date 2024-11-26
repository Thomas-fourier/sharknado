package com.ensta.myfilmlist.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Realisateur {
    private final int filmRealisesCelebrite = 3;

    private long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private List<Film> filmRealises = new ArrayList<>();
    private boolean celebre;

    public long getId() {
        return id;
    }

    public void setId(long _id) {
        id = _id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String _nom) {
        nom = _nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String _prenom) {
        prenom = _prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate _dateNaissance) {
        dateNaissance = _dateNaissance;
    }

    public List<Film> getFilmRealises() {
        return filmRealises;
    }

    public void addFilmRealises(Film film) {
        filmRealises.add(film);
        if (celebre || filmRealises.size() >= filmRealisesCelebrite) {
            celebre = true;
        }
    }

    public void removeFilmRealises(Film film) {
        filmRealises.remove(film);
        if (!celebre || filmRealises.size() < filmRealisesCelebrite) {
            celebre = false;
        }
    }

    public void setFilmRealises(List<Film> _filmRealises) {
        filmRealises = _filmRealises;
        celebre = (filmRealises.size() >= filmRealisesCelebrite);
    }

    public boolean getCelebre() {
        return celebre;
    }

    public void setCelebre(boolean _celebre) {
        celebre = _celebre;
    }
}
