package com.example.p3portaillocataireback.rentals;

import com.example.p3portaillocataireback.messages.Messages;
import com.example.p3portaillocataireback.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rentals {

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

    @OneToMany(mappedBy = "rentals")
    private List<Messages> messages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    public User user;

}
