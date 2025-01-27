package com.ensta.myfilmlist.controller;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.FilmForm;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

import javax.validation.Valid;

@Api(tags = "Film")
@Tag(name = "Film", description = "Opération sur les films")
public interface FilmRessource {
    ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException;

    ResponseEntity<FilmDTO> getFilmById(long id) throws ControllerException;

    ResponseEntity<FilmDTO> createFilm(@Valid FilmForm filmForm) throws ControllerException;

    ResponseEntity<Boolean> deleteFilm(long filmId) throws ControllerException;

    ResponseEntity<List<RealisateurDTO>> getAllRealisateurs() throws ControllerException;

}
