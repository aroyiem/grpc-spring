package com.roy.grpc.aggregator.controller;

import com.roy.grpc.aggregator.dto.RecommendedMovie;
import com.roy.grpc.aggregator.dto.UserGenre;
import com.roy.grpc.aggregator.service.UserMovieService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
public class AggregatorController {

    private final UserMovieService userMovieService;

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
        return userMovieService.getUserMovieSuggestions(loginId);
    }

    @PutMapping("/user")
    public void setUserGenre(@RequestBody UserGenre userGenre) {
        userMovieService.setUserGenre(userGenre);
    }
}
