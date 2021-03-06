package by.matusevichChercasova.movieRating.service;

import by.matusevichChercasova.movieRating.dto.FilmAddDto;
import by.matusevichChercasova.movieRating.dto.FilmDto;
import by.matusevichChercasova.movieRating.entity.Film;
import java.util.List;

public interface FilmService {

    List<Film> allFilms();

    List<Film> allNewFilms();

    Film getFilm(Long id);

    void updateFilm (FilmAddDto filmDto);

    boolean saveFilm(FilmAddDto filmDto);

    boolean deleteFilm(Long filmId);

    List<Film> searchFilm(String filmName);

    List<Film> getByGenre(String genre);

    List<Film> getAllFilmSort(String sortType);

    int countFilms();
}
