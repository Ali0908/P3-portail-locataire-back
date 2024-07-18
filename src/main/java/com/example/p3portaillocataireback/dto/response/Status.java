package com.example.p3portaillocataireback.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    CREATED("Rental created"),
    FAILED("failed"),
    UPDATED("Rental updated"),
    SUCCESS("Message send with success");
    private final String status;
}
