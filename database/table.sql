create database panneau;
\c panneau

drop table sources cascade;
drop table salles cascade;
drop table pointages cascade;
drop table meteos cascade;
drop table coupures cascade;

create table sources(
    id serial primary key,
    nom varchar(50),
    puissance_panneau double precision not null,
    capacite_batterie double precision not null,
    pourcentage_limite_batterie double precision not null
);

create table salles(
    id serial primary key,
    nom varchar(50),
    id_source int references sources(id)
);

create table pointages(
    id serial primary key,
    id_salle int references salles(id),
    nombre_personne_matin int not null,
    nombre_personne_soir int not null,
    "date" date
);

create table meteos(
    id serial primary key,
    date_heure timestamp not null,
    luminosite int not null
);

create table coupures(
    id serial primary key,
    id_source int references sources(id),
    date_heure timestamp not null
);