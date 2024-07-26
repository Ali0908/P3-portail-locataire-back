package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.requests.RentalUpdateDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalListResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.entity.User;
import com.example.p3portaillocataireback.exceptions.UnauthorizedRequestException;
import com.example.p3portaillocataireback.services.interfaces.RentalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalSrv;

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public RentalListResponseDto getAllRentals() {
        List<RentalResponseDto> rentals = rentalSrv.getAllRentals();
        return  new RentalListResponseDto(rentals);
    }

    @GetMapping("/{rental-id}")
    @SecurityRequirement(name = "bearerAuth")
    public Optional<RentalResponseDto> getRentalById(@PathVariable("rental-id") Integer id) {
        return rentalSrv.getRentalById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public Optional<MessageResponseDto> create(
            @ModelAttribute("name") String name,
            @ModelAttribute("surface") Float surface,
            @ModelAttribute("price") Float price,
            @ModelAttribute("picture") MultipartFile picture,
            @ModelAttribute("description") String description) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Integer owner_id = ((User) userDetails).getId();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String created_at = date.format(formattedDate);
        String updated_at = date.format(formattedDate);
        RentalDto rentalDto = new RentalDto(name, surface, price, picture.getOriginalFilename(), description, created_at, updated_at, owner_id);

        return rentalSrv.create(rentalDto);
    }

    @PutMapping("/{rental-id}")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public Optional<MessageResponseDto> update(
            @PathVariable("rental-id") Integer id,
            @ModelAttribute("name") String name,
            @ModelAttribute("surface") Float surface,
            @ModelAttribute("price") Float price,
            @ModelAttribute("description") String description) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Integer owner_id = ((User) userDetails).getId();

        // Vérifier si c'est le même que celui de loaderUserName que owner_id

        RentalResponseDto rental = rentalSrv.getRentalById(id).orElseThrow(() -> new UnauthorizedRequestException("Unauthorized request"));
        if(!Objects.equals(owner_id, rental.getOwner_id())) {
            throw new UnauthorizedRequestException("Unauthorized request");
        }
        LocalDate date = LocalDate.now();
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String created_at = String.valueOf(rental.getCreated_at());
        String updated_at = date.format(formattedDate);

        RentalUpdateDto rentalDto = new RentalUpdateDto(name, surface, price, description, created_at, updated_at, owner_id);
        return rentalSrv.update(id, rentalDto);
    }
}
