package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.RentalsDto;
import com.example.p3portaillocataireback.dto.response.RentalsResponseDto;
import com.example.p3portaillocataireback.services.RentalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals/")
@RequiredArgsConstructor
public class RentalsController {
    private final RentalsService rentalsService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<RentalsResponseDto> getAllRentals() {
        return rentalsService.getAllRentals();
    }
    @GetMapping("/{rental-id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public RentalsResponseDto getRentalById(@PathVariable("rental-id") Integer id) {
        return rentalsService.getRentalById(id);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<RentalsResponseDto> create(
            @RequestBody RentalsDto rentalsdto) {
        return ResponseEntity.ok(rentalsService.create(rentalsdto));
    }

}
