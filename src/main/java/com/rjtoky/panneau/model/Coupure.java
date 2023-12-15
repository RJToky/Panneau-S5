package com.rjtoky.panneau.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "coupures")
public class Coupure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_source")
    private Source source;

    @Column(name = "date_heure")
    private LocalDateTime dateHeure;

    @Transient
    private ArrayList<DetailCoupure> details;

    @Transient
    private double consommationMoyenne;

    public Coupure() {
    }

    public Coupure(Source source, LocalDateTime dateHeure) {
        this.source = source;
        this.dateHeure = dateHeure;
    }

    public LocalTime getHeure() {
        try {
            return LocalTime.of(this.getDateHeure().getHour(), this.getDateHeure().getMinute());
        } catch (Exception e) {
            return null;
        }
    }

    public double getConsommationPersonne(EntityManager entityManager) {
        double consommationPersonne = 0.0;

        System.out.println(this.getDateHeure().toString());
        List<Meteo> meteos = Meteo.getAllByDate(entityManager, this.getDateHeure().toString());

        double[] nombrePersonneMatinSoir = this.getSource().getNombrePersonneMatinSoir(entityManager,
                this.getDateHeure().toString());
        double nombrePersonneMatin = nombrePersonneMatinSoir[0];
        double nombrePersonneSoir = nombrePersonneMatinSoir[1];

        double capaciteMaxBatterie = this.getSource().getCapaciteBatterie();
        double capaciteLimiteBatterie = capaciteMaxBatterie * this.getSource().getPourcentageLimiteBatterie() / 100.0;

        double puissancePanneau = 0.0;
        double consommationTotale = 0.0;

        double min = 0.0;
        double max = 1000.0;
        double nombreAIterer = 0.0;

        while (true) {
            if (max - min < 0.1) {
                consommationPersonne = nombreAIterer;
                break;
            }

            double capaciteBatterieHeure = capaciteLimiteBatterie;
            LocalTime estimationCoupure = null;
            nombreAIterer = (max + min) / 2.0;

            System.out.println("Exact heure coupure : " + this.getDateHeure() + "\n");

            for (Meteo meteo : meteos) {
                System.out.println(
                        "Meteo : " + meteo.getDateHeure());

                if (meteo.estMatin()) {
                    consommationTotale = nombreAIterer * nombrePersonneMatin;
                } else {
                    consommationTotale = nombreAIterer * nombrePersonneSoir;
                }
                puissancePanneau = this.getSource().getPuissancePanneau() * meteo.getLuminosite() / 10.0;

                System.out.println(
                        "Consommation totale : " + consommationTotale);
                System.out
                        .println("Capacite batterie : " + capaciteBatterieHeure + " ||| Puissance Panneau : "
                                + puissancePanneau + "\n");

                if (consommationTotale > puissancePanneau) {
                    double reste = consommationTotale - puissancePanneau;
                    double capaciteBatterieAvantSoustraction = capaciteBatterieHeure;
                    capaciteBatterieHeure -= reste;

                    if (capaciteBatterieHeure <= 0) {
                        double tempADepenser = (capaciteBatterieAvantSoustraction * 60.0) / reste;

                        int heure = meteo.getDateHeure().getHour();
                        int minute = (int) tempADepenser;
                        estimationCoupure = LocalTime.of(heure, minute);

                        System.out.println("Estimation coupure : " + estimationCoupure);
                        break;
                    }
                } else {
                    capaciteBatterieHeure += puissancePanneau - consommationTotale;
                    if (capaciteBatterieHeure > capaciteLimiteBatterie) {
                        capaciteBatterieHeure = capaciteLimiteBatterie;
                    }
                }
            }

            if (estimationCoupure == null) {
                min = nombreAIterer;
            } else {
                if (this.compareTo(estimationCoupure) == 0) {
                    System.out.println("OKEEEEE");
                    System.out.println("\n\n=============================================\n\n");

                    consommationPersonne = nombreAIterer;
                    break;

                } else if (this.compareTo(estimationCoupure) == 1) {
                    System.out.println("MOINS -");
                    System.out.println("\n\n=============================================\n\n");

                    max = nombreAIterer;

                } else if (this.compareTo(estimationCoupure) == -1) {
                    System.out.println("PLUS +");
                    System.out.println("\n\n=============================================\n\n");

                    min = nombreAIterer;
                }
            }
        }
        return consommationPersonne;
    }

    public int compareTo(LocalTime heureMinute) {
        LocalTime heureMinuteCoupure = LocalTime.of(this.getDateHeure().getHour(), this.getDateHeure().getMinute());
        return heureMinuteCoupure.compareTo(heureMinute);
    }

    public boolean estMatin() {
        int heure = this.getDateHeure().getHour();

        int debutMatin = 0;
        int finMatin = 12;

        return heure >= debutMatin && heure < finMatin;
    }

}
