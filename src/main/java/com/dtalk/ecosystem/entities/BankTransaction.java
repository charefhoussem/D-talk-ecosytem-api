package com.dtalk.ecosystem.entities;

import com.dtalk.ecosystem.entities.users.Designer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTransaction;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Bank Name must be less than 100 characters")
    private String bankName;

    @Size(max = 255, message = "rib must be less than 255 characters")
    private String rib;

    @Enumerated(EnumType.STRING)
    private StatusPayment status = StatusPayment.ENATTENTE;

    private LocalDate date = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private ModePayment modePayment ;

    private String problem;
   @ManyToOne
   Designer designer;
}
