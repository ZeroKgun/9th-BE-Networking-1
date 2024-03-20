package com.cotato.networking1.service;


import com.cotato.networking1.entity.DTO.PropertyListResponseDto;
import com.cotato.networking1.entity.DTO.PropertyRegisterDto;
import com.cotato.networking1.entity.Property;
import com.cotato.networking1.repository.PropertyRepository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public void readExcelandUpdate(MultipartFile file) throws IOException{

        List<String> propertyList = new ArrayList<>();

        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        Sheet worksheet = workbook.getSheetAt(0);

        int rows = worksheet.getPhysicalNumberOfRows();

        for(int r = 1 ; r<rows; r++){
            System.out.println(r);
            propertyList.clear();
            System.out.println(propertyList);
            Row row = worksheet.getRow(r);
            if(row!=null){

                for(int c =0; c<9;c++){
                    Cell cell = row.getCell(c);
                    String value = "";
                    if(cell != null) {
                        switch (cell.getCellType()) {
                            case STRING -> value = cell.getStringCellValue();
                            case NUMERIC -> value = String.format("%.0f",cell.getNumericCellValue());
                        }
                    }
                    propertyList.add(value);
                }
            }
            System.out.println(propertyList);
            propertyRepository.save(parsePropertyList(propertyList));
        }
        return;
    }

    public Property parsePropertyList(List<String> propertyList){

        //0
        String zipCode = null;

        //1 2 3 4-5
        //5는 빈칸일수도 있다.
        String roadNameAddress = null;

        //1 2 6 7-8
        String landLotNameAddress = null;

        zipCode = propertyList.get(0);

        if(propertyList.get(5) == "") {
            roadNameAddress = propertyList.get(1) + " " + propertyList.get(2) + " " + propertyList.get(3) + " " + propertyList.get(4);
        }
        else {
            roadNameAddress = propertyList.get(1) + " " + propertyList.get(2) + " " + propertyList.get(3) + " " + propertyList.get(4) + "-" + propertyList.get(5);
        }
        landLotNameAddress = propertyList.get(1) + " " + propertyList.get(2) + " " +propertyList.get(6) + " " + propertyList.get(7) + "-" +propertyList.get(8);

        return Property.builder()
                .zipCode(zipCode)
                .roadNameAddress(roadNameAddress)
                .landLotNameAddress(landLotNameAddress)
                .build();
    }

    public List<PropertyListResponseDto> getPropertieswithZipCode(String zipCode) {

        List<Property> propertyList = propertyRepository.findAllByZipCode(zipCode);
        List<PropertyListResponseDto> responseDtoList = new ArrayList<>();
        for(Property p : propertyList){
            responseDtoList.add(PropertyListResponseDto.builder()
                    .id(p.getId())
                    .zipCode(p.getZipCode())
                    .roadNameAddress(p.getRoadNameAddress())
                    .landLotNameAddress(p.getLandLotNameAddress())
                    .build());
        }
        return responseDtoList;
    }

    public Long save(PropertyRegisterDto property) {

        Property newProperty =Property.builder()
                .zipCode(property.getZipCode())
                .roadNameAddress(property.getRoadNameAddress())
                .landLotNameAddress(property.getLandLotNameAddress())
                .build();
        propertyRepository.save(newProperty);
        return newProperty.getId();
    }

    public void deleteByRoadname(String roadNameAddress) {
        Property deleteProp = propertyRepository.findByRoadNameAddress(roadNameAddress).orElseThrow();
        propertyRepository.delete(deleteProp);
        return;
    }
}
