package com.example.p3portaillocataireback.messages;

import com.example.p3portaillocataireback.rentals.Rentals;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Messages {
    @Id
    @GeneratedValue
    private Integer id;
    private String message;
    private Date created_at;
    private Date updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentals_id")
    @JsonBackReference
    public Rentals rentals;
}
