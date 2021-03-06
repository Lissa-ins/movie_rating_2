package by.matusevichChercasova.movieRating.dto.mapper;

import by.matusevichChercasova.movieRating.dto.FilmAddDto;
import by.matusevichChercasova.movieRating.entity.Actor;
import by.matusevichChercasova.movieRating.entity.Film;
import by.matusevichChercasova.movieRating.entity.Producer;
import by.matusevichChercasova.movieRating.repository.ActorRepository;
import by.matusevichChercasova.movieRating.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FilmAddMapper implements Mapper<FilmAddDto, Film>  {
    @Autowired
    ProducerRepository producerRepository;
    @Autowired
    ActorRepository actorRepository;

    @Override
    public Film toEntity(FilmAddDto filmAddDto) {

        Film film = new Film();

        film.setId(filmAddDto.getId());
        film.setName(filmAddDto.getName());
        film.setProducer(producerRepository.findById(filmAddDto.getProducerId()).get());
        film.setPhotoPath(filmAddDto.getPhotoPath());
        film.setAnnotation(filmAddDto.getAnnotation());
        film.setGenre(filmAddDto.getGenre());
        film.setReleaseYear(filmAddDto.getReleaseYear());
        film.setDuration(filmAddDto.getDuration());

        Set<Actor> actors = new HashSet<>();
        Actor actor;
        for (int i = 0; i < filmAddDto.getActorsId().size(); i++) {
            actor = actorRepository.findById(filmAddDto.getActorsId().get(i)).get();
            actors.add(actor);
        }

        film.setActors(actors);

        return film;
    }

    @Override
    public FilmAddDto toDto(Film film) {

        FilmAddDto filmAddDto = new FilmAddDto();

        filmAddDto.setId(film.getId());
        filmAddDto.setName(film.getName());
        filmAddDto.setProducerId(film.getProducer().getId());
        filmAddDto.setPhotoPath(film.getPhotoPath());
        filmAddDto.setAnnotation(film.getAnnotation());
        filmAddDto.setGenre(film.getGenre());
        filmAddDto.setReleaseYear(film.getReleaseYear());
        filmAddDto.setDuration(film.getDuration());

        ArrayList<Long> actorsId = new ArrayList<>();

        Stream stream = film.getActors().stream();

        stream.forEach((element) -> {
            actorsId.add(((Actor) element).getId());
        });

        filmAddDto.setActorsId(actorsId);

        return filmAddDto;
    }

    @Override
    public List<FilmAddDto> toDtoList(List<Film> list) {
        return
                list.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());
    }
}
