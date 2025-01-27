package com.ensta.myfilmlist.controller.impl;

import com.ensta.myfilmlist.controller.FilmRessource;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.service.MyFilmsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class FilmResourceImpl implements FilmRessource {

    @Autowired
    MyFilmsService myFilmsService;

    @Override
    @GetMapping("/film")
    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Lister les films", notes = "Permet de renvoyer la liste de tous les films.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des films a été renvoyée correctement")
    })
    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException {
        try {
            return new ResponseEntity<>(myFilmsService.findAllFilms(), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @Override
    @GetMapping("/film/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Obtenir des infos sur un film", notes = "Renvoit les détails des informations d'un film donné.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le film a été trouvé et les informations sont transimises."),
            @ApiResponse(code = 404, message = "Le film n'a pas été trouvé.")
    })
    public ResponseEntity<FilmDTO> getFilmById(
            @PathVariable("id") @Parameter(name = "id", description = "id du film à trouver.", example = "1") long id)
            throws ControllerException {
        try {
            return new ResponseEntity<>(myFilmsService.findFilmById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @Override
    @PostMapping("/createFilm")
    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Ajouter un film à la librairie", notes = "Ajoute les détails d'un film et renvoit le filmDTO créé", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilmDTO> createFilm(
            @RequestBody FilmForm filmForm) throws ControllerException {
        try {
            FilmDTO filmDTO = myFilmsService.createFilm(filmForm);
            return new ResponseEntity<>(filmDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/deleteFilm/{filmId}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Supprimer un film de la librairie", notes = "Supprime le film par son identifiant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteFilm(
            @PathVariable("filmId") @Parameter(name = "filmId", description = "Identifiant du film à supprimer.") long filmId)
            throws ControllerException {
        try {
            myFilmsService.deleteFilm(filmId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
    }

    @Override
    @GetMapping("/realisateur")
    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Lister les realisateurs", notes = "Permet de renvoyer la liste de tous les réalisateurs.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des réalisateur a été renvoyée correctement")
    })
    public ResponseEntity<List<RealisateurDTO>> getAllRealisateurs() throws ControllerException {
        try {
            return new ResponseEntity<>(myFilmsService.findAllRealisateurs(), HttpStatus.OK);
        } catch (ServiceException e) {
            System.err.println(e);
            throw new ControllerException(e.getMessage());
        }
    }

}
