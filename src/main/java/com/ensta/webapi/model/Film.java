package com.ensta.webapi.model;

/**
 * Represente un Film.
 */
public class Film {

	private long id;

	private String titre;

	private int duree;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", titre=" + titre + ", duree=" + duree + "]";
	}

	
}
