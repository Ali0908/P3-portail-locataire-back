package com.example.p3portaillocataireback.controller;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.requests.RentalUpdateDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalListResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.entity.User;
import com.example.p3portaillocataireback.exceptions.UnauthorizedRequestException;
import com.example.p3portaillocataireback.services.interfaces.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalSrv;
    @Value("${storage.image-directory}")
    private String imageDirectory;

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
    @Operation(summary = "Create rental", description = "Create a new rental with a picture.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(implementation = RentalDto.class)))
    public Optional<MessageResponseDto> create(
            @ModelAttribute("name") String name,
            @ModelAttribute("surface") Float surface,
            @ModelAttribute("price") Float price,
            @ModelAttribute("picture") MultipartFile picture,
            @ModelAttribute("description") String description) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Integer owner_id = ((User) userDetails).getId();

        LocalDate created_at = LocalDate.now();
        LocalDate updated_at = LocalDate.now();

//        // Save the picture to the specified directory
//        String fileName = UUID.randomUUID() + "_" + picture.getOriginalFilename();
//
//        Files.copy(picture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//
//        // Generate the URL for the stored image
//        String pictureUrl =  path.toString();

        RentalDto rentalDto = new RentalDto(name, surface, price, picture, description, created_at, updated_at, owner_id);

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
