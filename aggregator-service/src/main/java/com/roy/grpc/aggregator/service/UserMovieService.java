package com.roy.grpc.aggregator.service;

import com.roy.common.Genre;
import com.roy.grpc.aggregator.dto.RecommendedMovie;
import com.roy.grpc.aggregator.dto.UserGenre;
import com.roy.grpc.movie.MovieSearchRequest;
import com.roy.grpc.movie.MovieSearchResponse;
import com.roy.grpc.movie.MovieServiceGrpc;
import com.roy.grpc.user.UserGenreUpdateRequest;
import com.roy.grpc.user.UserResponse;
import com.roy.grpc.user.UserSearchRequest;
import com.roy.grpc.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder()
                .setLoginId(loginId)
                .build();
        UserResponse userResponse = this.userStub.getUserGenre(userSearchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder()
                .setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMoviesList()
                .stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre) {
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build();
        UserResponse userResponse = this.userStub.updateUserGenre(userGenreUpdateRequest);
    }
}
