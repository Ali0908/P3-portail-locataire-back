package com.example.p3portaillocataireback.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RentalListResponseDto {
    private List<RentalResponseDto> rentals;
}
