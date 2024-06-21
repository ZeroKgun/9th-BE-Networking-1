package com.cotato.networking1.service;


import com.cotato.networking1.dto.PropertyListResponse;
import com.cotato.networking1.dto.PropertyRegisterRequest;
import com.cotato.networking1.entity.Property;
import com.cotato.networking1.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;


    public List<PropertyListResponse> getPropertieswithZipCode(String zipCode) {

        List<Property> propertyList = propertyRepository.findAllByZipCode(zipCode);
        List<PropertyListResponse> propertyListResponses = new ArrayList<>();

        for (Property property : propertyList) {
            propertyListResponses.add(new PropertyListResponse(
                    property.getId(), property.getZipCode(), property.getRoadNameAddress(), property.getLandLotNameAddress()
            ));
        }
        return propertyListResponses;
    }

    public Long registerProperty(PropertyRegisterRequest registerRequest) {
        Property property = Property.builder()
                .zipCode(registerRequest.zipCode())
                .roadNameAddress(registerRequest.roadNameAddress())
                .landLotNameAddress(registerRequest.landLotNameAddress())
                .build(); //record는 자동으로 필드명의 메소드들을 제공, 필드를 꺼내올 수 있다.
        propertyRepository.save(property);
        return property.getId();
    }

    public void deleteByroadNameAddress(String roadNameAddress) {
        Property property = propertyRepository.findByRoadNameAddress(roadNameAddress)
                .orElseThrow(() -> new NoSuchElementException("No property found with road name address: " + roadNameAddress));
        propertyRepository.delete(property);
    }
}
