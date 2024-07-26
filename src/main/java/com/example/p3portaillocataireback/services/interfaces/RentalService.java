package com.example.p3portaillocataireback.services.interfaces;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.requests.RentalUpdateDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    /**
     * Create new rental
     *
     * @param rentalDto {@link RentalDto}
     * @return {@link MessageResponseDto}
     */
    Optional<MessageResponseDto> create(RentalDto rentalDto);

    /**
     * Update rental
     *
     * @param id        {@link Integer}
     * @param rentalDto {@link RentalUpdateDto}
     * @return {@link MessageResponseDto}
     */
    Optional<MessageResponseDto> update(Integer id, RentalUpdateDto rentalDto);

    /**
     * Get all rentals
     *
     * @return {@link List<RentalResponseDto>}
     */
    List<RentalResponseDto> getAllRentals();

    /**
     * Get rental by id
     *
     * @param id {@link Integer}
     * @return {@link RentalResponseDto}
     */
    Optional<RentalResponseDto> getRentalById(Integer id);
}
