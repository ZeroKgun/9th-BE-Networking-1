package com.cotato.networking1.service;


import com.cotato.networking1.dto.PropertyListResponse;
import com.cotato.networking1.entity.Property;
import com.cotato.networking1.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelDataService {

    private final PropertyRepository propertyRepository;

    public void saveExcelData(String path) throws IOException, InvalidFormatException {
        File file = new File(path);
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        List<Property> propertyList = new ArrayList<>();

        Sheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();

        for (int r = 1; r < rows; r++) {
            Row row = sheet.getRow(r);
            String zipCode = row.getCell(0).getStringCellValue(); //우편번호
            String cityProvince = row.getCell(1).getStringCellValue(); // 시도
            String district = row.getCell(2).getStringCellValue(); // 시군구
            String roadName = row.getCell(3).getStringCellValue(); // 도로명

            String buildingMain = String.valueOf((int)row.getCell(4).getNumericCellValue()); //건물번호 본번
            String buildingSub = "";
            if (row.getCell(5) != null) { // 건물번호 부번, 빈칸일 경우 ""
                buildingSub = String.valueOf((int)row.getCell(5).getNumericCellValue());
            }
            String dong = row.getCell(6).getStringCellValue(); //법정동명
            String addressMain = String.valueOf((int) row.getCell(7).getNumericCellValue()); //지번본번
            String addressSub = String.valueOf((int) row.getCell(8).getNumericCellValue()); //지번 부번



            String roadNameAddress = new StringBuilder()
                    .append(cityProvince).append(" ")
                    .append(district).append(" ")
                    .append(roadName).append(" ")
                    .append(buildingMain).toString();
            if(!buildingSub.isEmpty()){
                roadNameAddress = roadNameAddress+ "-" +buildingSub;
            } //
            String landLotNameAddress = new StringBuilder()
                    .append(cityProvince).append(" ")
                    .append(district).append(" ")
                    .append(dong).append(" ")
                    .append(addressMain).append(" ")
                    .append(addressSub).toString();

            Property property = Property.builder()
                    .zipCode(zipCode)
                    .roadNameAddress(roadNameAddress)
                    .landLotNameAddress(landLotNameAddress)
                    .build();

            propertyList.add(property);
        }

        propertyRepository.saveAll(propertyList);
    }

}
