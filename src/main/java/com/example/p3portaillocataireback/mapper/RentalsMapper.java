package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.requests.RentalsDto;
import com.example.p3portaillocataireback.entity.Rentals;
import com.example.p3portaillocataireback.dto.response.RentalsResponseDto;
import org.springframework.stereotype.Service;


@Service
public class RentalsMapper {
    public Rentals toRental(RentalsDto dto) {
      var rental = new Rentals();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(dto.getPicture());
        rental.setDescription(dto.getDescription());
        return rental;

    }

    public RentalsResponseDto toRentalsResponseDto(Rentals rental) {
        return new RentalsResponseDto(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription()
        );
    }
}
