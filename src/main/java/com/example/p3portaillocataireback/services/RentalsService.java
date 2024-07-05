package com.example.p3portaillocataireback.services;

import com.example.p3portaillocataireback.dto.requests.RentalsDto;
import com.example.p3portaillocataireback.dto.response.RentalsResponseDto;
import com.example.p3portaillocataireback.mapper.RentalsMapper;
import com.example.p3portaillocataireback.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalsService {
    private final RentalsMapper rentalsMapper;
    private final RentalsRepository rentalsRepository;

    @Autowired
    public RentalsService(RentalsMapper rentalsMapper, RentalsRepository rentalsRepository) {
        this.rentalsMapper = rentalsMapper;
        this.rentalsRepository = rentalsRepository;
    }
    public RentalsResponseDto create(RentalsDto rentalsDto) {
        var rental = rentalsMapper.toRental(rentalsDto);
        rentalsRepository.save(rental);
        return rentalsMapper.toRentalsResponseDto(rental);
    }
    public  List<RentalsResponseDto> getAllRentals() {
        return rentalsRepository.findAll()
                .stream()
                .map(rentalsMapper::toRentalsResponseDto)
                .collect(Collectors.toList());
    }
    public RentalsResponseDto getRentalById(Integer id) {
        return rentalsRepository.findById(id)
                .map(rentalsMapper::toRentalsResponseDto)
                .orElseThrow();
    }

}
