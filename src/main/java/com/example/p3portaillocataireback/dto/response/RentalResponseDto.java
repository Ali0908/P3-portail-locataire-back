package com.example.p3portaillocataireback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalResponseDto {
        private Integer id;
        private String name;
        private Float surface;
        private Float price;
        private String picture;
        private String description;
        private String created_at;
        private String updated_at;
        private Integer owner_id;
}