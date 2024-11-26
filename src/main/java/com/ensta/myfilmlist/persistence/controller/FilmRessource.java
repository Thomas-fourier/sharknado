package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FilmRessource {
    ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException;
}
