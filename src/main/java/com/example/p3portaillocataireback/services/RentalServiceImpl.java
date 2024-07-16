package com.example.p3portaillocataireback.services;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.requests.RentalUpdateDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseCreatedDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseUpdatedDto;
import com.example.p3portaillocataireback.mapper.RentalMapper;
import com.example.p3portaillocataireback.repository.RentalRepository;
import com.example.p3portaillocataireback.services.interfaces.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalServiceImpl(RentalMapper rentalMapper, RentalRepository rentalRepository) {
        this.rentalMapper = rentalMapper;
        this.rentalRepository = rentalRepository;
    }
    public Optional<RentalResponseCreatedDto> create(RentalDto rentalDto) {
        var rental = rentalMapper.toRental(rentalDto);
        rentalRepository.save(rental);
        return Optional.of(rentalMapper.toRentalsResponseCreatedDto(rental));
    }
    public List<RentalResponseDto> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toRentalsResponseDto)
                .collect(Collectors.toList());
    }
    public Optional<RentalResponseDto> getRentalById(Integer id) {
        return Optional.of((rentalRepository.findById(id)
                .map(rentalMapper::toRentalsResponseDto)
                .orElseThrow()));
    }
    public Optional<RentalResponseUpdatedDto> update (Integer id, RentalUpdateDto updateRentalDto) {
        var existingRental = rentalRepository.findById(id).orElseThrow();
        existingRental.setName(updateRentalDto.getName());
        existingRental.setSurface(updateRentalDto.getSurface());
        existingRental.setPrice(updateRentalDto.getPrice());
        existingRental.setDescription(updateRentalDto.getDescription());
        rentalRepository.save(existingRental);
        return Optional.of(rentalMapper.toRentalsResponseUpdatedDto(existingRental));
    }
}
