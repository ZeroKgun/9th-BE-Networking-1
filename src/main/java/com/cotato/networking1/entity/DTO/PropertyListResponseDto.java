package com.cotato.networking1.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class PropertyListResponseDto {

    private Long id;
    private String zipCode;
    private String roadNameAddress;
    private String landLotNameAddress;

}
