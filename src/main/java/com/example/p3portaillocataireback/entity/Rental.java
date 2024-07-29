package com.example.p3portaillocataireback.entity;

import com.example.p3portaillocataireback.dto.response.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rental {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Float surface;
    private Float price;
    private String picture;
    private String description;
    private LocalDate created_at;
    private LocalDate updated_at;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "rental")
    // TODO :A rental can have only one message
    private List<Message> messages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    public User user;

}
