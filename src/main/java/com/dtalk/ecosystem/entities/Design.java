package com.dtalk.ecosystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "designs")
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDesign;
    private String name;
    private String description;
    private String imagePath;
    private String originFilePath;
    private Boolean isPublished;
    private Boolean isAccepted;
    private double price;

    @Transient
    private MultipartFile imageFile;
    @Transient
    private MultipartFile originFile;

    @ManyToOne
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Tag> tags;

}
