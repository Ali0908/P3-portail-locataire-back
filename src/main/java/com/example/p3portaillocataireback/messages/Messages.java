package com.example.p3portaillocataireback.messages;

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
public class Messages {
    @Id
    @GeneratedValue
    private Integer id;
    private String message;
    private Date created_at;
    private Date updated_at;
}
