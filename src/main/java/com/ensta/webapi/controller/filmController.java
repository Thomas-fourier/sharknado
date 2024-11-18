package com.ensta.webapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ensta.webapi.dto.FilmDTO;
import com.ensta.webapi.exception.NotFoundException;
import com.ensta.webapi.exception.ServiceException;
import com.ensta.webapi.model.Film;
import com.ensta.webapi.service.FilmService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class filmController
 */
@WebServlet("/film")
public class filmController extends HttpServlet {

	private final String PARAM_ID = "id";
	private final String PARAM_TITRE = "titre";
	private final String PARAM_DUREE = "duree";

	private final String DONE = "done";

	private FilmService filmService = new FilmService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Optional<Long> id = getId(request);

		try {
			if (id.isPresent()) {
				Film film = filmService.getById(id.get());
				response.getWriter().write(film.toString());
			} else {
				List<Film> films = filmService.getAll();
				response.getWriter().write(films.toString());
			}
		} catch (ServiceException e) {
			send500(response, e);
		} catch (NotFoundException e) {
			send400(response, e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			filmService.create(mapFilm(request));
			response.getWriter().write(DONE);
		} catch (ServiceException e) {
			send500(response, e);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			filmService.update(mapFilm(req));
			resp.getWriter().write(DONE);
		} catch (ServiceException e) {
			send500(resp, e);
		}
	}

	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Optional<Long> id = getId(req);

		try {
			if (id.isPresent()) {
				filmService.delete(id.get());
			} else {
				sendError(resp, "Paramètre id manquant", 400);
			}
		} catch (ServiceException e) {
			send500(resp, e);
		}
	}

	private FilmDTO mapFilm(HttpServletRequest req) {
		FilmDTO film = new FilmDTO();

		film.setId(getId(req).orElse(0L));
		film.setDuree(getDuree(req).orElse(0));
		film.setTitre(req.getParameter(PARAM_TITRE));

		return film;
	}

	private Optional<Long> getId(HttpServletRequest req) {
		Optional<String> id = Optional.ofNullable(req.getParameter(PARAM_ID));

		if (id.isPresent()) {
			return Optional.of(Long.valueOf(id.get()));
		}
		return Optional.empty();
	}

	private Optional<Integer> getDuree(HttpServletRequest req) {
		Optional<String> duree = Optional.ofNullable(req.getParameter(PARAM_DUREE));

		if (duree.isPresent()) {
			return Optional.of(Integer.valueOf(duree.get()));
		}
		return Optional.empty();
	}

	private void send400(HttpServletResponse res, Exception cause) throws IOException {
		sendError(res, cause.getMessage(), 400);
	}

	private void send500(HttpServletResponse res, Exception cause) throws IOException {
		sendError(res, cause.getMessage(), 500);
	}

	private void sendError(HttpServletResponse res, String msg, int statusCode) throws IOException {
		res.setStatus(statusCode);
		res.getWriter().write(msg);
	}
}
