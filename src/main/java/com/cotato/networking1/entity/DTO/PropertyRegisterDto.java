package com.cotato.networking1.entity.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyRegisterDto {

    private String zipCode;
    private String roadNameAddress;
    private String landLotNameAddress;

}
