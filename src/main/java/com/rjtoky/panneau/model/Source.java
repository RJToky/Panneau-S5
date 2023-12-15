package com.rjtoky.panneau.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sources")
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @Column(name = "puissance_panneau")
    private double puissancePanneau;

    @Column(name = "capacite_batterie")
    private double capaciteBatterie;

    @Column(name = "pourcentage_limite_batterie")
    private double pourcentageLimiteBatterie;

    @OneToMany(mappedBy = "source")
    private List<Salle> salles;

    public Source() {
    }

    public Source(int id) {
        this.id = id;
    }

    public String getSallesText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Salle salle : salles) {
            stringBuilder.append(salle.getNom()).append(" - ");
        }
        stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(),
                "");

        return stringBuilder.toString();
    }

    public List<Coupure> getCoupuresAnterieurs(EntityManager entityManager, String date) {
        String sql = """
                select
                    *
                from
                    coupures
                where
                    date_heure < cast(? as date)
                    and id_source = ?
                order by
                    date_heure desc
                        """;
        Query query = entityManager.createNativeQuery(sql, Coupure.class);
        query.setParameter(1, date);
        query.setParameter(2, this.getId());

        return query.getResultList();
    }

    public static List<Source> getAll(EntityManager entityManager) {
        String sql = "select * from sources";
        Query query = entityManager.createNativeQuery(sql, Source.class);
        return query.getResultList();
    }

    public double[] getMoyennePersonneMatinSoir(EntityManager entityManager, String date) {
        String sql = """
                select
                    avg(po.nombre_personne_matin), avg(po.nombre_personne_soir)
                from
                    pointages po
                join v_sources_salles vss on vss.id_salle = po.id_salle
                where
                    vss.id_source = ?
                    and extract(dow from po.date) = extract(dow from to_date(?, 'YYYY-MM-DD'))
                            """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, this.getId());
        query.setParameter(2, date);
        List<Object[]> resultList = query.getResultList();

        if (resultList != null && !resultList.isEmpty()) {
            Object[] row = resultList.get(0);
            BigDecimal moyenneMatin = (BigDecimal) row[0];
            BigDecimal moyenneSoir = (BigDecimal) row[1];
            return new double[] { moyenneMatin.doubleValue(), moyenneSoir.doubleValue() };
        } else {
            return new double[] {};
        }
    }

    public double[] getNombrePersonneMatinSoir(EntityManager entityManager, String date) {
        String sql = """
                select
                    sum(po.nombre_personne_matin), sum(po.nombre_personne_soir)
                from
                    pointages po
                join v_sources_salles vss on vss.id_salle = po.id_salle
                where
                    vss.id_source = ?
                    and po.date = cast(? as date)
                            """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, this.getId());
        query.setParameter(2, date);
        List<Object[]> resultList = query.getResultList();

        if (resultList != null && !resultList.isEmpty()) {
            Object[] row = resultList.get(0);
            Number nombrePersonneMatin = (Number) row[0];
            Number nombrePersonneSoir = (Number) row[1];
            return new double[] { nombrePersonneMatin.doubleValue(), nombrePersonneSoir.doubleValue() };
        } else {
            return new double[] {};
        }
    }

    public Coupure predireCoupure(EntityManager entityManager, String date) {
        Coupure coupure = new Coupure();
        coupure.setSource(this);

        double[] nombreMoyennePersonneMatinSoir = this.getMoyennePersonneMatinSoir(entityManager, date);
        double nombrePersonneMatin = nombreMoyennePersonneMatinSoir[0];
        double nombrePersonneSoir = nombreMoyennePersonneMatinSoir[1];

        double consommationMoyenne = this.getConsommationMoyennePersonne(entityManager, date);
        double consommationTotaleMatin = consommationMoyenne * nombrePersonneMatin;
        double consommationTotaleSoir = consommationMoyenne * nombrePersonneSoir;

        System.out.println("Nombre personne matin : " + nombrePersonneMatin);
        System.out.println("Nombre personne soir : " + nombrePersonneSoir);

        System.out.println("Consommation totale matin : " + consommationTotaleMatin);
        System.out.println("Consommation totale soir : " + consommationTotaleSoir);

        double capaciteMaxBatterie = this.getCapaciteBatterie();
        double capaciteLimiteBatterie = capaciteMaxBatterie * this.getPourcentageLimiteBatterie() / 100.0;

        double puissancePanneau = 0.0;
        double capaciteBatterieHeure = capaciteLimiteBatterie;
        double consommationTotale = 0.0;
        LocalTime heureCoupure = null;

        ArrayList<DetailCoupure> detailsCoupure = new ArrayList<DetailCoupure>();
        DetailCoupure detailCoupure = new DetailCoupure();

        List<Meteo> meteos = Meteo.getAllByDate(entityManager, date);
        for (Meteo meteo : meteos) {
            detailCoupure = new DetailCoupure();
            detailCoupure.setHeure(LocalTime.of(meteo.getDateHeure().getHour(), 0));
            detailCoupure.setCapaciteBatterie(capaciteBatterieHeure);

            if (meteo.estMatin()) {
                consommationTotale = consommationTotaleMatin;
            } else {
                consommationTotale = consommationTotaleSoir;
            }
            puissancePanneau = this.getPuissancePanneau() * meteo.getLuminosite() / 10.0;
            if (consommationTotale > puissancePanneau) {
                double reste = consommationTotale - puissancePanneau;
                double capaciteBatterieAvantSoustraction = capaciteBatterieHeure;
                capaciteBatterieHeure -= reste;
                if (capaciteBatterieHeure <= 0) {
                    double tempADepenser = (capaciteBatterieAvantSoustraction * 60.0) / reste;

                    int heure = meteo.getDateHeure().getHour();
                    int minute = (int) tempADepenser;
                    heureCoupure = LocalTime.of(heure, minute);

                    System.out.println("Estimation coupure : " + heureCoupure);
                    break;
                }
            } else {
                capaciteBatterieHeure += puissancePanneau - consommationTotale;
                if (capaciteBatterieHeure > capaciteLimiteBatterie) {
                    capaciteBatterieHeure = capaciteLimiteBatterie;
                }
            }
            detailCoupure.setConsommationTotal(consommationTotale);
            detailCoupure.setPuissancePanneau(puissancePanneau);
            detailsCoupure.add(detailCoupure);
        }

        LocalDate parseDate = LocalDate.parse(date);
        if (heureCoupure != null) {
            detailCoupure.setConsommationTotal(consommationTotale);
            detailCoupure.setPuissancePanneau(puissancePanneau);
            detailsCoupure.add(detailCoupure);
            coupure.setDateHeure(heureCoupure.atDate(parseDate));
        }
        coupure.setConsommationMoyenne(consommationMoyenne);
        coupure.setDetails(detailsCoupure);
        return coupure;
    }

    public double getConsommationMoyennePersonne(EntityManager entityManager, String date) {
        List<Coupure> coupures = this.getCoupuresAnterieurs(entityManager, date);

        ArrayList<Double> sommeConsommationPersonne = new ArrayList<>();
        for (Coupure coupure : coupures) {
            sommeConsommationPersonne.add(coupure.getConsommationPersonne(entityManager));
        }

        double consommationMoyenne = sommeConsommationPersonne.stream().mapToDouble(Double::doubleValue).average()
                .orElse(0.0);
        System.out.println("Consommation moyenne : " + consommationMoyenne);

        return consommationMoyenne;
    }

    public static Source getById(EntityManager entityManager, int id) {
        String sql = "select * from sources where id = ?";
        Query query = entityManager.createNativeQuery(sql, Source.class);
        query.setParameter(1, id);
        return (Source) query.getSingleResult();
    }

}