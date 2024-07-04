package com.dtalk.ecosystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldFolderStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToMany(mappedBy="fieldStyles", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<FolderStyle> folders;
}
