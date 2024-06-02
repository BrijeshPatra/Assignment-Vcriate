package com.project.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private Long ownerId;
    private int version;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
