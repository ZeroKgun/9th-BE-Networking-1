package com.cotato.networking1.dto;

public record PropertyRegisterRequest(
        String zipCode,
        String roadNameAddress,
        String landLotNameAddress
) {
}
