package com.roy.grpc.movie.service;

import com.roy.grpc.movie.MovieDto;
import com.roy.grpc.movie.MovieSearchRequest;
import com.roy.grpc.movie.MovieSearchResponse;
import com.roy.grpc.movie.MovieServiceGrpc;
import com.roy.grpc.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    private final MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movieDtoList = movieRepository.getMovieByGenreOrderByYearDesc(request.getGenre().toString())
                .stream()
                .map(movie ->
                        MovieDto.newBuilder()
                                .setTitle(movie.getTitle())
                                .setRating(movie.getRating())
                                .setYear(movie.getYear())
                                .build()
                ).collect(Collectors.toList());

        responseObserver.onNext(MovieSearchResponse.newBuilder().addAllMovies(movieDtoList).build());
        responseObserver.onCompleted();
    }
}
