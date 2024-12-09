package com.dtalk.ecosystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exceptionType;
    @Lob
    @Column(name="CONTENT", length=512)
    private String message;

    private String endpoint;

    private LocalDateTime timestamp;
}
