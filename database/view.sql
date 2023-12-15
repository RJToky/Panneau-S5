create or replace view v_sources_salles as (
    select
        so.id id_source,
        so.nom nom_source,
        so.puissance_panneau,
        so.capacite_batterie,
        so.pourcentage_limite_batterie,
        sa.id id_salle,
        sa.nom nom_salle
    from
        sources so
    join salles sa on sa.id_source = so.id
);


select
    avg(po.nombre_personne_matin), avg(po.nombre_personne_soir)
from
    pointages po
join v_sources_salles vss on vss.id_salle = po.id_salle
where
    vss.id_source = 1
    and extract(dow from po.date) = extract(dow from to_date('2023-12-25', 'YYYY-MM-DD'));


select
    sum(po.nombre_personne_matin), sum(po.nombre_personne_soir)
from
    pointages po
join v_sources_salles vss on vss.id_salle = po.id_salle
where
    vss.id_source = 1
    and po.date = cast('2023-12-11' as date);