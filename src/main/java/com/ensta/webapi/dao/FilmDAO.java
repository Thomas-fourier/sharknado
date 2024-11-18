package com.ensta.webapi.dao;

import java.util.ArrayList;
import java.util.List;

import com.ensta.webapi.model.Film;

public class FilmDAO {
	Film film1;
	Film film2;
	Film film3;

	List<Film> films = new ArrayList<Film>();

	public FilmDAO() {

		film1 = new Film();
		films.add(film1);
		
		film1.setDuree(115);
		film1.setId(0);
		film1.setTitre("Les Aventuriers de l'arche perdue");
		
		film2 = new Film();
		films.add(film2);
		
		film2.setDuree(121);
		film2.setId(1);
		film2.setTitre("Star Wars, épisode IV : Un nouvel espoir");
		
		film3 = new Film();
		films.add(film3);
		
		film3.setDuree(154);
		film3.setId(0);
		film3.setTitre("Pulp Fiction");
	}
}
