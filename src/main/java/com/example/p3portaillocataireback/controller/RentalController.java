package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.services.interfaces.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalSrv;

    @GetMapping
    public List<RentalResponseDto> getAllRentals() {
        return rentalSrv.getAllRentals();
    }
//
//    @GetMapping("/{rental-id}")
//    public RentalResponseDto getRentalById(@PathVariable("rental-id") Integer id) {
//        return rentalServiceImpl.getRentalById(id);
//    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponseDto create(
            @RequestBody RentalDto rentalDto)
            {
        return rentalSrv.create(rentalDto).orElseThrow(() -> new RuntimeException("Creation failed"));
    }
}
