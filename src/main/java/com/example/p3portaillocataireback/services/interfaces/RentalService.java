package com.example.p3portaillocataireback.services.interfaces;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    /**
     * Create new rental
     *
     * @param rentalDto {@link RentalDto}
     * @return {@link RentalResponseDto}
     */
    public Optional<RentalResponseDto> create(RentalDto rentalDto);
    /**
     * Get all rentals
     *
     * @return {@link List<RentalResponseDto>}
     */
    public List<RentalResponseDto> getAllRentals();
}
