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
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Column(unique = true)
    @Size(min = 11, max = 11)
    private String pesel;

    @NotNull
    private String idNumber;

    @NotNull
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
    private String postalCode;

    @NotNull
    private String referralId;

    @NotNull
    @Email
    private String email;
}
