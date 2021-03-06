package by.matusevichChercasova.movieRating.service.impl;


import by.matusevichChercasova.movieRating.dto.FilmAddDto;
import by.matusevichChercasova.movieRating.dto.FilmBookmarkDto;
import by.matusevichChercasova.movieRating.dto.FilmDto;
import by.matusevichChercasova.movieRating.dto.mapper.FilmAddMapper;
import by.matusevichChercasova.movieRating.dto.mapper.FilmMapper;
import by.matusevichChercasova.movieRating.entity.Bookmark;
import by.matusevichChercasova.movieRating.entity.Film;
import by.matusevichChercasova.movieRating.entity.User;
import by.matusevichChercasova.movieRating.repository.BookmarkRepository;
import by.matusevichChercasova.movieRating.repository.FilmRepository;
import by.matusevichChercasova.movieRating.service.FilmService;
import by.matusevichChercasova.movieRating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmAddMapper filmMapper;
    private final FilmRepository filmRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RatingService ratingService;

    @Autowired
    public FilmServiceImpl(FilmAddMapper filmMapper,
                           FilmRepository filmRepository,
                           BookmarkRepository bookmarkRepository,
                           RatingService ratingService) {
        this.filmMapper = filmMapper;
        this.filmRepository = filmRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.ratingService = ratingService;
    }

    @Override
    public List<Film> allFilms() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        List<Film> films = filmRepository.findAll();
        long idCurrentUser;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            idCurrentUser = user.getId();
        }catch (ClassCastException e){
            return films;
        }
        long idFilm;

        for (int j = 0; j < films.size(); j++) {
            for (int i = 0; i < bookmarks.size(); i++) {

                idFilm = films.get(j).getId();

                if (bookmarks.get(i).getIdUser() == idCurrentUser &&
                        bookmarks.get(i).getIdFilm() == idFilm) {
                    films.get(j).setExistBookmark(true);
                    break;
                } else {
                    films.get(j).setExistBookmark(false);
                }
            }
        }

        return films;
    }

    @Override
    public List<Film> allNewFilms() {

        List<Film> films = filmRepository.findAll();
        List<Film> newFilms = new ArrayList<>();
        for (int i=0;i<films.size();i++){
            if(films.get(i).getReleaseYear().equals("2020")){
                newFilms.add(films.get(i));
            }
        }
        return newFilms;
    }

    @Override
    public boolean saveFilm(FilmAddDto filmAddDto) {

        Film film = filmMapper.toEntity(filmAddDto);

        filmRepository.findByName(film.getName())
                .ifPresent(value -> {
                    throw new RuntimeException("Sorry film is already present");
                });
        filmRepository.save(film);

        return true;
    }

    @Override
    public boolean deleteFilm(Long filmId) {
        if (filmRepository.findById(filmId).isPresent()) {
            filmRepository.deleteById(filmId);
            return true;
        }
        return false;
    }

    @Override
    public Film getFilm(Long id) {

        return filmRepository.getOne(id);

    }

    @Override
    public List<Film> searchFilm(String filmName){
        List<Film>filmSet=filmRepository.findAll();

        List<Film>resultSet=new ArrayList<>();

        for (Film film : filmSet) {
            if (film.getName().toLowerCase().contains(filmName.toLowerCase())) {
                resultSet.add(film);
            }
        }
        return resultSet;
    }

    @Override
    public List<Film> getByGenre(String genre) {
        List<Film> films =filmRepository.findAll();
        List<Film> filterFilms = new ArrayList<>();

        if("все".equals(genre)){
            return films;
        }

        for (Film film : films) {
            if (film.getGenre().contains(genre)) {
                filterFilms.add(film);
            }
        }
        return filterFilms;
    }

    @Override
    public List<Film> getAllFilmSort(String sortType) {
        List<Film> films = allFilms();
        List<Double> ratings = new ArrayList<>();

        for (int i=0;i<films.size();i++){
            ratings.add(ratingService.oneFilmRating(films.get(i).getId()));
        }

        if(sortType.equals("sortRatingDec")){
            sortInc(films,ratings);
        }
        if(sortType.equals("sortRatingInc")){
            sortDec(films,ratings);
        }
        return films;
    }

    @Override
    public int countFilms() {
        return filmRepository.findAll().size();
    }

    @Override
    public void updateFilm(FilmAddDto filmAddDto) {

        Film film = filmMapper.toEntity(filmAddDto);

        filmRepository.save(film);

    }

    private void sortInc(List<Film> films, List<Double> ratings){
        for (int left = 0; left < ratings.size(); left++) {
            int minInd = left;
            for (int i = left; i < ratings.size(); i++) {
                if (ratings.get(i) < ratings.get(minInd)) {
                    minInd = i;
                }
            }

            double tmp = ratings.get(left);
            ratings.set(left,ratings.get(minInd));
            ratings.set(minInd,tmp);

            Film tmpF = films.get(left);
            films.set(left,films.get(minInd));
            films.set(minInd,tmpF);
        }
    }

    private void sortDec(List<Film> films, List<Double> ratings){
        for (int left = 0; left < ratings.size(); left++) {
            int minInd = left;
            for (int i = left; i < ratings.size(); i++) {
                if (ratings.get(i) > ratings.get(minInd)) {
                    minInd = i;
                }
            }

            double tmp = ratings.get(left);
            ratings.set(left,ratings.get(minInd));
            ratings.set(minInd,tmp);

            Film tmpF = films.get(left);
            films.set(left,films.get(minInd));
            films.set(minInd,tmpF);
        }
    }


}
