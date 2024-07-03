package com.example.p3portaillocataireback.rentals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
