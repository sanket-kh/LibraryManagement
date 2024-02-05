package com.librarymanagement.LibraryApplication.utils;

import com.librarymanagement.LibraryApplication.jwtconfigs.JwtServiceReportingModule;
import com.librarymanagement.LibraryApplication.services.serviceImpls.ClientDetailsService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
@Component
@RequiredArgsConstructor
public class WebClientRequestBuilder {
    private final JwtServiceReportingModule jwtServiceReportingModule;
    private final ClientDetailsService clientDetailsService;
    private final HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    public WebClient buildGetWebClientRequest() {
        String token =
                jwtServiceReportingModule.generateToken(clientDetailsService.loadUserByUsername(
                        "LibraryApplication"));
        return WebClient.builder()
                .baseUrl(UriConstants.REPORTING_STATISTIC_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization","Bearer "+token)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }


}
