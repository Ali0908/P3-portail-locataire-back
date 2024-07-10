package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.services.RentalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalServiceImpl rentalServiceImpl;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<RentalResponseDto> getAllRentals() {
        return rentalServiceImpl.getAllRentals();
    }

    @GetMapping("/{rental-id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public RentalResponseDto getRentalById(@PathVariable("rental-id") Integer id) {
        return rentalServiceImpl.getRentalById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public RentalResponseDto create(
            @RequestBody RentalDto rentaldto) {
        return rentalServiceImpl.create(rentaldto).get();
    }
}
