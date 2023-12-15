package com.rjtoky.panneau.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "meteos")
public class Meteo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_heure")
    private LocalDateTime dateHeure;

    private int luminosite;

    public static List<Meteo> getAllByDate(EntityManager entityManager, String date) {
        String sql = """
                select
                    *
                from
                    meteos
                where cast(date_heure as date) = cast(? as date)
                    """;
        Query query = entityManager.createNativeQuery(sql, Meteo.class);
        query.setParameter(1, date);
        return query.getResultList();
    }

    public boolean estMatin() {
        int heure = this.getDateHeure().getHour();

        int debutMatin = 0;
        int finMatin = 12;

        return heure >= debutMatin && heure < finMatin;
    }

}
