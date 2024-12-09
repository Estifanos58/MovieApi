package com.movieflix.movieApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.movieApi.dto.Moviedto;
import com.movieflix.movieApi.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add-movie")
    // Whenever you take json object and file at the same time you should not take the json as an object instade
    // Take the json as a string and convert it to Object
    public ResponseEntity<Moviedto> addMovieHandler(@RequestPart MultipartFile file, @RequestPart String moviedto) throws IOException {
        Moviedto dto = convertToMovieDto(moviedto);
        return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);
    }

    private Moviedto convertToMovieDto(String movieDtoObj) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        Moviedto moviedto = objectMapper.readValue(movieDtoObj, Moviedto.class);
        return moviedto;
    }
}
