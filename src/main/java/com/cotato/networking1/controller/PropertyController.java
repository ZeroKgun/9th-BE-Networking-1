package com.cotato.networking1.controller;


import com.cotato.networking1.dto.PropertyListResponse;
import com.cotato.networking1.dto.PropertyRegisterRequest;
import com.cotato.networking1.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping("/properties")
    public List<PropertyListResponse> getPropertieswithZipCode(
            @RequestParam("zip-code") String zipCode
    ){
        return propertyService.getPropertieswithZipCode(zipCode);
    }

    @PostMapping("/properties")
    public Long registerProperty(
            @RequestBody PropertyRegisterRequest request
    ){
        return propertyService.registerProperty(request);
    }

    @DeleteMapping("/properties")
    public ResponseEntity<?> deleteProperty(
            @RequestParam("road-name-address") String roadNameAddress
    ){
        propertyService.deleteByroadNameAddress(roadNameAddress);
        return ResponseEntity.status(HttpStatus.OK).body("property deleted");
    }

}
