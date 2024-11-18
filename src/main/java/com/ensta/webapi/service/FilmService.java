package com.ensta.webapi.service;

import java.util.List;

import com.ensta.webapi.dao.FilmDAO;
import com.ensta.webapi.dto.FilmDTO;
import com.ensta.webapi.exception.DAOException;
import com.ensta.webapi.exception.NotFoundException;
import com.ensta.webapi.exception.ServiceException;
import com.ensta.webapi.mapper.FilmMapper;
import com.ensta.webapi.model.Film;

public class FilmService {

	private FilmDAO filmDAO = new FilmDAO();

	public List<Film> getAll() throws ServiceException {
		try {
			return filmDAO.getAll();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Film getById(long id) throws ServiceException, NotFoundException {
		try {
			return filmDAO.getById(id).orElseThrow(() -> new NotFoundException("No film with id " + id));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void create(FilmDTO film) throws ServiceException {
		try {
			Film model = new FilmMapper().toModel(film);
			filmDAO.create(model);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void update(FilmDTO film) throws ServiceException {
		try {
			Film model = new FilmMapper().toModel(film);
			filmDAO.update(model);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public void delete(long id) throws ServiceException {
		try {
			filmDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
