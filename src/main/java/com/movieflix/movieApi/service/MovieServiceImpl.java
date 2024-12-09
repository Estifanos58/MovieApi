package com.movieflix.movieApi.service;

import com.movieflix.movieApi.dto.Moviedto;
import com.movieflix.movieApi.entities.Movie;
import com.movieflix.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final FileService fileService;
    private final MovieRepository movieRepository;

    // Property injection for folder path and base URL
    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseurl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public Moviedto addMovie(Moviedto moviedto, MultipartFile file) throws IOException {
        // 1. Upload the file
        String uploadedFileName = fileService.uploadFile(path, file);

        // 2. Set the value of field 'poster' as filename
        moviedto.setPoster(uploadedFileName);

        // 3. Map DTO to Movie entity
        Movie movie = new Movie(
                moviedto.getMovieId(),
                moviedto.getTitle(),
                moviedto.getDirector(),
                moviedto.getStudio(),
                moviedto.getMovieCast(),
                moviedto.getReleaseYear(),
                moviedto.getPoster()
        );

        // 4. Save the movie object
        Movie savedMovie = movieRepository.save(movie);

        // 5. Generate the poster URL
        String posterUrl = baseurl + "/file/" + uploadedFileName;

        // 6. Map saved Movie object to DTO and return
        Moviedto response = new Moviedto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),  // which should be the uploaded file name
                posterUrl
        );

        return response;
    }

    @Override
    public Moviedto getMovie(Integer movieId) {
        return movieRepository.findById(movieId)
                .map(savedMovie -> new Moviedto(
                        savedMovie.getMovieId(),
                        savedMovie.getTitle(),
                        savedMovie.getDirector(),
                        savedMovie.getStudio(),
                        savedMovie.getMovieCast(),
                        savedMovie.getReleaseYear(),
                        savedMovie.getPoster(),  // using saved poster filename
                        baseurl + "/file/" + savedMovie.getPoster()
                ))
                .orElse(null); // Handle not found appropriately
    }

    @Override
    public List<Moviedto> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(savedMovie -> new Moviedto(
                        savedMovie.getMovieId(),
                        savedMovie.getTitle(),
                        savedMovie.getDirector(),
                        savedMovie.getStudio(),
                        savedMovie.getMovieCast(),
                        savedMovie.getReleaseYear(),
                        savedMovie.getPoster(),
                        baseurl + "/file/" + savedMovie.getPoster()
                ))
                .collect(Collectors.toList());
    }
}