package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.entity.Rental;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import org.springframework.stereotype.Service;


@Service
public class RentalMapper {
    public Rental toRental(RentalDto dto) {
      var rental = new Rental();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(dto.getPicture());
        rental.setDescription(dto.getDescription());
        return rental;

    }

    public RentalResponseDto toRentalsResponseDto(Rental rental) {
        return new RentalResponseDto(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription()
        );
    }
}
