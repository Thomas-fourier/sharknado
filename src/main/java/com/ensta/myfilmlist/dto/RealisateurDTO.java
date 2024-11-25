package com.ensta.myfilmlist.dto;

import java.time.LocalDate;
import java.util.List;

public class RealisateurDTO {
    private long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private List<FilmDTO> filmRealises;
    private boolean celebre;

    public long getId() {
        return id;
    }

    public void setId(long _id) {
        id = _id;
        return;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String _nom) {
        nom = _nom;
        return;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String _prenom) {
        prenom = _prenom;
        return;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate _dateNaissance) {
        dateNaissance = _dateNaissance;
        return;
    }

    public List<FilmDTO> getFilmRealises() {
        return filmRealises;
    }

    public void setFilmRealises(List<FilmDTO> _filmRealises) {
        filmRealises = _filmRealises;
        return;
    }

    public boolean getCelebre() {
        return celebre;
    }

    public void setCelebre(boolean _celebre) {
        celebre = _celebre;
        return;
    }

    public String toString() {
        return "RealisateurDTO [id=" + id + ", nom=" + nom + ", prenom=" + prenom +
                ", dateNaissance=" + dateNaissance + ", celebre=" + celebre + "]";
    }
}
