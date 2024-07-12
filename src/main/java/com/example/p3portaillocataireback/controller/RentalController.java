package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.services.RentalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalServiceImpl rentalServiceImpl;

    @GetMapping
    public List<RentalResponseDto> getAllRentals() {
        return rentalServiceImpl.getAllRentals();
    }

    @GetMapping("/{rental-id}")
    public RentalResponseDto getRentalById(@PathVariable("rental-id") Integer id) {
        return rentalServiceImpl.getRentalById(id);
    }

    @PostMapping("/create")
    public RentalResponseDto create(
            @RequestBody RentalDto rentaldto) {
        return rentalServiceImpl.create(rentaldto).get();
    }
}
