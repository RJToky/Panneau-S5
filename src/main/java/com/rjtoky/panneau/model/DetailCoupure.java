package com.rjtoky.panneau.model;

import java.time.LocalTime;

import lombok.Data;

@Data
public class DetailCoupure {
    private LocalTime heure;
    private double capaciteBatterie;
    private double puissancePanneau;
    private double consommationTotal;
}
