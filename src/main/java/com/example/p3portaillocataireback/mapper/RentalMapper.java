package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseUpdatedDto;
import com.example.p3portaillocataireback.dto.response.Status;
import com.example.p3portaillocataireback.entity.Rental;
import com.example.p3portaillocataireback.entity.User;
import com.example.p3portaillocataireback.dto.response.RentalResponseCreatedDto;
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
        rental.setCreated_at(dto.getCreated_at());
        rental.setUpdated_at(dto.getUpdated_at());
        var user = new User();
        user.setId(dto.getOwner_id());
        rental.setUser(user);
        return rental;
    }

    public RentalResponseCreatedDto toRentalsResponseCreatedDto(Rental rental) {
        return new RentalResponseCreatedDto(
                Status.CREATED.getStatus()
        );
    }
    public RentalResponseUpdatedDto toRentalsResponseUpdatedDto(Rental rental) {
        return new RentalResponseUpdatedDto(
                Status.UPDATED.getStatus()
        );
    }
    public RentalResponseDto toRentalsResponseDto(Rental rental) {
        return new RentalResponseDto(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getCreated_at(),
                rental.getUpdated_at(),
                rental.getUser().getId()
        );
    }
}
