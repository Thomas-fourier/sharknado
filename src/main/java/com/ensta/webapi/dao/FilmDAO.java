package com.ensta.webapi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.webapi.exception.DAOException;
import com.ensta.webapi.model.Film;

public class FilmDAO {
	List<Film> films = new ArrayList<Film>();

	public FilmDAO() {

		Film film1 = new Film();
		films.add(film1);

		film1.setDuree(115);
		film1.setId(0);
		film1.setTitre("Les Aventuriers de l'arche perdue");

		Film film2 = new Film();
		films.add(film2);

		film2.setDuree(121);
		film2.setId(1);
		film2.setTitre("Star Wars, épisode IV : Un nouvel espoir");

		Film film3 = new Film();
		films.add(film3);

		film3.setDuree(154);
		film3.setId(2);
		film3.setTitre("Pulp Fiction");
	}

	public List<Film> getAll() throws DAOException {
		return new ArrayList<Film>(films);
	}

	public Optional<Film> getById(long id) throws DAOException {
		return films.stream().filter(film -> film.getId() == id).findFirst();
	}

	public void create(Film film) throws DAOException {
		long maxId = films.stream().mapToLong(Film::getId).max().orElse(0L);

		film.setId(maxId + 1);
		films.add(film);
	}

	public void update(Film film) throws DAOException {
		getById(film.getId()).ifPresent(filmToUpdate -> updateFilm(film, filmToUpdate));
	}

	private void updateFilm(Film film, Film filmToUpdate) {
		filmToUpdate.setDuree(film.getDuree());
		filmToUpdate.setTitre(film.getTitre());
	}

	public void delete(long id) throws DAOException {
		getById(id).ifPresent(films::remove);
	}
}
