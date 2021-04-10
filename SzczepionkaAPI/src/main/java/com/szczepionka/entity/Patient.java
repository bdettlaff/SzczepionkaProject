package com.szczepionka.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false, nullable = false, unique = true)
    @NotNull
    private UUID uuid = UUID.randomUUID();

    @NotNull
    private String pesel;

    @NotNull
    private String idNumber;

    @NotNull
    private String postalCode;

    @NotNull
    private String referralId;

    @NotNull
    private String email;
}
