package com.ensta.webapi.mapper;

import com.ensta.webapi.dto.FilmDTO;
import com.ensta.webapi.model.Film;

public class FilmMapper {

	public Film toModel(FilmDTO dto) {
		Film film = new Film();
		
		film.setId(dto.getId());
		film.setDuree(dto.getDuree());
		film.setTitre(dto.getTitre());
		
		return film;
	}

}
