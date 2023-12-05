package com.librarymanagement.LibraryApplication.controllers;

import com.librarymanagement.LibraryApplication.services.ReportingStatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportingStatisticController {
    private ReportingStatisticService reportingStatisticService;

    @GetMapping("/all-books")
    public ResponseEntity<Object> getListOfAllBooks(){
    return reportingStatisticService.getListOfAllBooks();
    }
    @GetMapping("/available-books")
    public ResponseEntity<Object> getListOfAllAvailableBooks(){
        return reportingStatisticService.getListOfAllAvailableBooks();
    }
    @GetMapping("/reserved-books")
    public ResponseEntity<Object> getListOfAllReservedBooks(){
        return reportingStatisticService.getListOfAllReservedBooks();
    }
    @GetMapping("/book-count/available")
    public ResponseEntity<Object> getCountOfAvailableBooks(){
        return reportingStatisticService.getCountOfAvailableBooks();
    }
    @GetMapping("/book-count/total")
    public ResponseEntity<Object> getCountOfTotalBooks(){
        return reportingStatisticService.getCountOfTotalBooks();
    }




}
