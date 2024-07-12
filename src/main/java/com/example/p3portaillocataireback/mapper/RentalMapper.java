package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.entity.Rental;
import com.example.p3portaillocataireback.entity.User;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;


@Service
public class RentalMapper {
    public Rental toRental(RentalDto dto) {
      var rental = new Rental();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(dto.getPicture());
        rental.setDescription(dto.getDescription());
        rental.setCreated_at(Timestamp.from(Instant.now()));
        rental.setUpdated_at(Timestamp.from(Instant.now()));
        var user = new User();
        user.setId(dto.getOwner_id());
        rental.setUser(user);
        return rental;
    }

    public RentalResponseDto toRentalsResponseDto(Rental rental) {
        return new RentalResponseDto(
                rental.getStatus()
        );
    }
}
