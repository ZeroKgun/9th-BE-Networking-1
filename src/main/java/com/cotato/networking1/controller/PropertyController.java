package com.cotato.networking1.controller;


import com.cotato.networking1.entity.DTO.PropertyListResponseDto;
import com.cotato.networking1.entity.DTO.PropertyRegisterDto;
import com.cotato.networking1.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PropertyController {

    private final PropertyService propertyService;


    @PostMapping("/test-api")
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) throws IOException{
        propertyService.readExcelandUpdate(file);
        return ResponseEntity.status(HttpStatus.OK).body("property upload successful");
    }

    @GetMapping("/properties")
    public List<PropertyListResponseDto> getPropertieswithZipCode(
            @RequestParam("zip-code") String zipCode
    ){
        return propertyService.getPropertieswithZipCode(zipCode);
    }

    @PostMapping("/properties")
    public Long registerProperty(
            @RequestBody PropertyRegisterDto property
    ){
        return propertyService.save(property);
    }

    @DeleteMapping("/properties")
    public ResponseEntity<?> deletePropertyByRoadName(
            @RequestParam("road-name-address") String roadNameAddress
    ){
        propertyService.deleteByRoadname(roadNameAddress);
        return ResponseEntity.status(HttpStatus.OK).body("property deleted");
    }
}
