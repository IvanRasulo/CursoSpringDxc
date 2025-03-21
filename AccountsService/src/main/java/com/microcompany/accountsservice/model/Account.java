package com.microcompany.accountsservice.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo de cuenta no puede ser nulo")
    @Size(min = 3, max = 50, message = "El tipo de cuenta debe tener entre 3 y 50 caracteres")
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de apertura no puede ser nula")
    private Date openingDate;

    @Min(value = 0, message = "El saldo no puede ser negativo")
    private int balance;

    @NotNull(message = "El ID del propietario no puede ser nulo")
    private Long ownerId;

    @JsonIgnore
    @Transient
    private Customer owner;
}