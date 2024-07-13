package com.example.p3portaillocataireback.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    CREATED("Rental created"),
    FAILED("failed"),
    UPDATED("Rental updated");
    private final String status;
}
