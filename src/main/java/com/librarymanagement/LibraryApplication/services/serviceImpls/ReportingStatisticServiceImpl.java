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
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReportingStatisticServiceImpl implements ReportingStatisticService {
    private final WebClientRequestBuilder webClientRequestBuilder;

    private static Mono<DefaultResponse> getDefaultResponseMono(ClientResponse clientResponse) {
        if (clientResponse.statusCode().equals(HttpStatus.OK)) {
            return clientResponse.bodyToMono(DefaultResponse.class);
        } else if (clientResponse.statusCode().is4xxClientError()) {
            return Mono.just(new DefaultResponse(false,
                    "Reporting module error: 4xx or 5xx",
                    ResponseConstants.SERVER_ERROR, null
                    ));
        } else {
            return Mono.just(new DefaultResponse(false,
                    "Reporting module error: 4xx or 5xx",
                    ResponseConstants.SERVER_ERROR, null
                    ));
        }
    }

    @Override
    public ResponseEntity<Object> getListOfAllBooks() {
        try {
            WebClient webClient = webClientRequestBuilder.buildGetWebClientRequest();


            Mono<DefaultResponse> response = webClient.get()
                    .uri(UriConstants.GET_ALL_BOOKS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<Object> getListOfAllAvailableBooks() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_ALL_AVAILABLE_BOOKS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getListOfAllReservedBooks() {
        try {
            WebClient webClient =
                    webClientRequestBuilder.buildGetWebClientRequest();

            Mono<DefaultResponse> response = webClient.get()
                    .uri(UriConstants.GET_ALL_RESERVED_BOOKS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfTotalBooks() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_OF_TOTAL_BOOKS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfAvailableBooks() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_OF_AVAILABLE_BOOKS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getBookCount() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_UNIQUE_BOOK_COUNT).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfTotalBorrowedBooks() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_BORROWED_BOOKS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfUniqueBorrowedBooks() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_BORROWED_BOOKS_UNIQUE).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfUser() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_USERS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfActiveUser() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_ACTIVE_USERS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfLockedUser() {
        try {

            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_LOCKED_USERS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getCountOfDisabledUser() {
        try {
            Mono<DefaultResponse> response = webClientRequestBuilder
                    .buildGetWebClientRequest().get()
                    .uri(UriConstants.GET_COUNT_DISABLED_USERS).header("Authorization")
                    .exchangeToMono(ReportingStatisticServiceImpl::getDefaultResponseMono
                    );
            return new ResponseEntity<>(response.block(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("ReportingStatisticService :: getListOfAllBooks", e);
            return ResponseUtility.failureResponseWithMessage(ResponseConstants.SERVER_ERROR,
                    "Sorry exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
