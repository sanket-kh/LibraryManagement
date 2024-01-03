package com.librarymanagement.LibraryApplication.services.serviceImpls;

import com.librarymanagement.LibraryApplication.models.responses.DefaultResponse;
import com.librarymanagement.LibraryApplication.services.ReportingStatisticService;
import com.librarymanagement.LibraryApplication.utils.ResponseConstants;
import com.librarymanagement.LibraryApplication.utils.ResponseUtility;
import com.librarymanagement.LibraryApplication.utils.UriConstants;
import com.librarymanagement.LibraryApplication.utils.WebClientRequestBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReportingStatisticServiceImpl implements ReportingStatisticService {
    private final WebClientRequestBuilder webClientRequestBuilder;

    @Override
    public ResponseEntity<Object> getListOfAllBooks() {
        WebClient webClient =webClientRequestBuilder.buildGetWebClientRequest(null);


        Mono<DefaultResponse> response = webClient.get()
                .uri(UriConstants.GET_ALL_BOOKS).header("Authorization")
                .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                                return clientResponse.bodyToMono(DefaultResponse.class);
                            } else if (clientResponse.statusCode().is4xxClientError()) {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error: 4xx or 5xx"));
                            } else {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error"));
                            }
                        }
                );

        DefaultResponse defaultResponse = response.block();
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getListOfAllAvailableBooks() {
        WebClient webClient =
                webClientRequestBuilder.buildGetWebClientRequest(null);


        Mono<DefaultResponse> response = webClient.get()
                .uri(UriConstants.GET_ALL_AVAILABLE_BOOKS)
                .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                                return clientResponse.bodyToMono(DefaultResponse.class);
                            } else if (clientResponse.statusCode().is4xxClientError() ||
                                    clientResponse.statusCode().is5xxServerError()) {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error: 4xx or 5xx"));
                            } else {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error"));
                            }
                        }
                );

        DefaultResponse defaultResponse = response.block();
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getListOfAllReservedBooks() {
        WebClient webClient =
                webClientRequestBuilder.buildGetWebClientRequest(null);

        Mono<DefaultResponse> response = webClient.get()
                .uri(UriConstants.GET_ALL_RESERVED_BOOKS)
                .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                                return clientResponse.bodyToMono(DefaultResponse.class);
                            } else if (clientResponse.statusCode().is4xxClientError() ||
                                    clientResponse.statusCode().is5xxServerError()) {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error: 4xx or 5xx"));
                            } else {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error"));
                            }
                        }
                );

        DefaultResponse defaultResponse = response.block();
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Object> getCountOfTotalBooks() {
        WebClient webClient =
                webClientRequestBuilder.buildGetWebClientRequest(null);


        Mono<DefaultResponse> response = webClient.get()
                .uri(UriConstants.GET_TOTAL_BOOKS_COUNT)
                .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                                return clientResponse.bodyToMono(DefaultResponse.class);
                            } else if (clientResponse.statusCode().is4xxClientError() ||
                                    clientResponse.statusCode().is5xxServerError()) {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error: 4xx or 5xx"));
                            } else {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error"));
                            }
                        }
                );

        DefaultResponse defaultResponse = response.block();
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getCountOfAvailableBooks() {
        WebClient webClient =
                webClientRequestBuilder.buildGetWebClientRequest(null);


        Mono<DefaultResponse> response = webClient.get()
                .uri(UriConstants.GET_AVAILABLE_BOOK_COUNT)
                .exchangeToMono(clientResponse -> {
                            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                                return clientResponse.bodyToMono(DefaultResponse.class);
                            } else if (clientResponse.statusCode().is4xxClientError() ||
                                    clientResponse.statusCode().is5xxServerError()) {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error: 4xx or 5xx"));
                            } else {
                                return Mono.just(ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                                        "Reporting module error"));
                            }
                        }
                );

        DefaultResponse defaultResponse = response.block();
        return new ResponseEntity<>(defaultResponse, HttpStatus.OK);
    }
}
