package com.example.p3portaillocataireback.services;

import com.example.p3portaillocataireback.dto.requests.RegisterRequest;
import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.AuthResponse;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.mapper.RentalMapper;
import com.example.p3portaillocataireback.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl {
    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalServiceImpl(RentalMapper rentalMapper, RentalRepository rentalRepository) {
        this.rentalMapper = rentalMapper;
        this.rentalRepository = rentalRepository;
    }
    public Optional<RentalResponseDto> create(RentalDto rentalDto) {
        var rental = rentalMapper.toRental(rentalDto);
        rentalRepository.save(rental);
        return Optional.of(rentalMapper.toRentalsResponseDto(rental));
    }
    public  List<RentalResponseDto> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toRentalsResponseDto)
                .collect(Collectors.toList());
    }
    public RentalResponseDto getRentalById(Integer id) {
        return rentalRepository.findById(id)
                .map(rentalMapper::toRentalsResponseDto)
                .orElseThrow();
    }
    public RentalResponseDto update (Integer id, RentalDto updateRentalDto) {
        var existingRental = rentalRepository.findById(id).orElseThrow();
        existingRental.setName(updateRentalDto.getName());
        existingRental.setSurface(updateRentalDto.getSurface());
        existingRental.setPrice(updateRentalDto.getPrice());
        existingRental.setDescription(updateRentalDto.getDescription());
        rentalRepository.save(existingRental);
        return rentalMapper.toRentalsResponseDto(existingRental);
    }
}
