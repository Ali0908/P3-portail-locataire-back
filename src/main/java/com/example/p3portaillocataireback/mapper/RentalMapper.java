package com.example.p3portaillocataireback.mapper;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.dto.response.Status;
import com.example.p3portaillocataireback.entity.Rental;
import com.example.p3portaillocataireback.entity.User;
import com.example.p3portaillocataireback.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Service
public class RentalMapper {
    @Value("${storage.server.base-url}")
    private String baseUrl;
    private final Path rootLocation = Paths.get("uploads");

    public Rental toRental(RentalDto dto) {
      var rental = new Rental();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setPicture(generateUrlFromFile(dto.getPicture()));
        rental.setDescription(dto.getDescription());
        rental.setCreated_at(dto.getCreated_at());
        rental.setUpdated_at(dto.getUpdated_at());
        var user = new User();
        user.setId(dto.getOwner_id());
        rental.setUser(user);
        return rental;
    }

    public String generateUrlFromFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
            return  baseUrl + "/uploads/" + filename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.");
        }
    }

    public MessageResponseDto toRentalsResponseCreatedDto() {
        return new MessageResponseDto(
                Status.CREATED.getStatus()
        );
    }
    public MessageResponseDto toRentalsResponseUpdatedDto() {
        return new MessageResponseDto(
                Status.UPDATED.getStatus()
        );
    }
    public RentalResponseDto toRentalsResponseDto(Rental rental) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String createdAt = rental.getCreated_at().format(formatter);
        String updatedAt = rental.getUpdated_at().format(formatter);
        return new RentalResponseDto(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                createdAt,
                updatedAt,
                rental.getUser().getId()
        );
    }
}
