package com.dtalk.ecosystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Transient
    private MultipartFile imageFile;
    @Transient
    private MultipartFile originFile;

    @ManyToOne
    private User user;



}
