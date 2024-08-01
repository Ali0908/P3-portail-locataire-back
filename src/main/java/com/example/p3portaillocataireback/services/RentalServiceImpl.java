package com.example.p3portaillocataireback.services;

import com.example.p3portaillocataireback.dto.requests.RentalDto;
import com.example.p3portaillocataireback.dto.requests.RentalUpdateDto;
import com.example.p3portaillocataireback.dto.response.MessageResponseDto;
import com.example.p3portaillocataireback.dto.response.RentalResponseDto;
import com.example.p3portaillocataireback.exceptions.StorageException;
import com.example.p3portaillocataireback.mapper.RentalMapper;
import com.example.p3portaillocataireback.repository.RentalRepository;
import com.example.p3portaillocataireback.services.interfaces.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;
    @Value("${storage.server.base-url}")
    private String baseUrl;
    private final Path rootLocation = Paths.get("uploads");

    @Autowired
    public RentalServiceImpl(RentalMapper rentalMapper, RentalRepository rentalRepository) {
        this.rentalMapper = rentalMapper;
        this.rentalRepository = rentalRepository;
    }
    public Optional<MessageResponseDto> create(RentalDto rentalDto) {
        var rental = rentalMapper.toRental(rentalDto);
        generateUrlFromFile(rentalDto.getPicture());
        rentalRepository.save(rental);
        return Optional.of(rentalMapper.toRentalsResponseCreatedDto());
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
    public List<RentalResponseDto> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toRentalsResponseDto)
                .collect(Collectors.toList());
    }
    public Optional<RentalResponseDto> getRentalById(Integer id) {
        return Optional.of((rentalRepository.findById(id)
                .map(rentalMapper::toRentalsResponseDto)
                .orElseThrow()));
    }
    public Optional<MessageResponseDto> update (Integer id, RentalUpdateDto updateRentalDto) {
        var existingRental = rentalRepository.findById(id).orElseThrow();
        existingRental.setName(updateRentalDto.getName());
        existingRental.setSurface(updateRentalDto.getSurface());
        existingRental.setPrice(updateRentalDto.getPrice());
        existingRental.setDescription(updateRentalDto.getDescription());
        rentalRepository.save(existingRental);
        return Optional.of(rentalMapper.toRentalsResponseUpdatedDto());
    }
}
