package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.entity.User;
import com.example.p3portaillocataireback.exceptions.UnauthorizedRequestException;
import com.example.p3portaillocataireback.services.interfaces.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponseDto create(
            @RequestParam("name") String name,
            @RequestParam("surface") Float surface,
            @RequestParam("price") Float price,
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("description") String description) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Integer owner_id = ((User) userDetails).getId();

        Timestamp created_at = Timestamp.from(Instant.now());
        Timestamp updated_at = Timestamp.from(Instant.now());
        RentalDto rentalDto = new RentalDto(name, surface, price, picture.getOriginalFilename(), description, created_at, updated_at, owner_id);

        return rentalSrv.create(rentalDto).orElseThrow(() -> new UnauthorizedRequestException("Unauthorized request"));
    }
}
