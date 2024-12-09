package com.movieflix.movieApi.service;

import com.movieflix.movieApi.dto.Moviedto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    Moviedto addMovie(Moviedto moviedto, MultipartFile file) throws IOException;

    Moviedto getMovie(Integer movieId);

    List<Moviedto> getAllMovies();
}
