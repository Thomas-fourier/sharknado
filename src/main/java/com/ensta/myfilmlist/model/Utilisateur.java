package com.ensta.myfilmlist.model;



public class Utilisateur {

    private long id;
    private String nom;
    private String prenom;

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
    }}
