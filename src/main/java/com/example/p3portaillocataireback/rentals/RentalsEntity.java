package com.example.p3portaillocataireback.rentals;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RentalsEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private Date created_at;
    private Date updated_at;

}
