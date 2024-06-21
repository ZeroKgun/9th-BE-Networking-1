package com.cotato.networking1.controller;


import com.cotato.networking1.service.ExcelDataService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-data")
public class ExcelDataController {

    private final ExcelDataService excelDataService;

    @PostMapping("")
    public ResponseEntity<?> insertExcelData() throws IOException, InvalidFormatException {
        excelDataService.saveExcelData("C:\\exceldata\\properties_big.xlsx");
        return ResponseEntity.status(HttpStatus.OK).body("property data insertion successful");
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> insertBulkExcelData() throws IOException, InvalidFormatException {
        excelDataService.saveBigExcelData("C:\\exceldata\\properties_big.xlsx");
        return ResponseEntity.status(HttpStatus.OK).body("property data bulk insertion successful");
    }

}
