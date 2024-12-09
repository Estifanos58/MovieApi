package com.movieflix.movieApi.service;

import com.movieflix.movieApi.dto.Moviedto;
import com.movieflix.movieApi.entities.Movie;
import com.movieflix.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private  final FileService fileService;

    private  final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService){
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseurl;

    @Override
    public Moviedto addMovie(Moviedto moviedto, MultipartFile file) throws IOException {

        // 1. upload the file
        String uploadedFileName = fileService.uploadFile(path, file);

        // 2. set the value of field 'poster' as filename
        moviedto.setPoster(uploadedFileName);

        // 3. map dto to movie object
        Movie movie = new Movie(
                moviedto.getMovieId(),
                moviedto.getTitle(),
                moviedto.getDirector(),
                moviedto.getStudio(),
                moviedto.getMovieCast(),
                moviedto.getReleaseYear(),
                moviedto.getPoster()
        );

        // 4. save the movie object -> saved movie object
        Movie savedMovie = movieRepository.save(movie);

        // 5. generate the posterUrl
        String posterUrl = baseurl + "/file/" + uploadedFileName;

        // 6. map movie object to DTO object and return it
        Moviedto response = new Moviedto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );

        return response;
    }

    @Override
    public Moviedto getMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<Moviedto> getAllMovies() {
        return List.of();
    }
}
