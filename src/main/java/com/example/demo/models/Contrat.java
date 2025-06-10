package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "contrat")
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @ManyToOne
    @JoinColumn(name = "user_app_id")
    private UserApp userApp;

    public Contrat(LocalDate dateDebut, LocalDate dateFin, UserApp userApp) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.userApp = userApp;
    }
}
