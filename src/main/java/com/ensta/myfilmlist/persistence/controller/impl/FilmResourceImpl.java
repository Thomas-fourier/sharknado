package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import com.ensta.myfilmlist.persistence.controller.FilmRessource;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilmResourceImpl implements FilmRessource {

    @Autowired
    MyFilmsService myFilmsService;

    @Override
    @RequestMapping("/film")
    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException {
        try {
            return new ResponseEntity<>(myFilmsService.findAllFilms(), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new ControllerException();
        }
    }
}
