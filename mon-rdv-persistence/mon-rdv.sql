DROP TABLE IF EXISTS creneau;
DROP TABLE IF EXISTS rendez_vous;
DROP TABLE IF EXISTS lieu;
DROP TABLE IF EXISTS specialite_praticien;
DROP TABLE IF EXISTS specialite;
DROP TABLE IF EXISTS motif;
DROP TABLE IF EXISTS utilisateur;
DROP TABLE IF EXISTS adresse;

CREATE TABLE IF NOT EXISTS adresse (
	id bigserial NOT NULL,
	rue character varying(255),
	complement character varying(255),
	code_postal character varying(10),
    ville character varying(255),
	CONSTRAINT adresse_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS utilisateur
(
    id bigserial NOT NULL,
	disc character varying(20) NOT NULL,
	nom character varying(100),
    prenom character varying(100),
	email character varying(255),
    mot_de_passe character varying(100),
	matricule character varying(10),
	civilite character varying(5),
	number_ss character varying(20),
	age integer,
	telephone character varying(15),
    CONSTRAINT utilisateur_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS motif
(
    id bigserial NOT NULL,
	titre character varying(100),
	duree integer,
	praticien_id bigint,
    CONSTRAINT motif_pkey PRIMARY KEY (id),
	CONSTRAINT motif_praticien_fk FOREIGN KEY (praticien_id) REFERENCES utilisateur (id)
);

CREATE TABLE IF NOT EXISTS specialite
(
    id bigserial NOT NULL,
	nom character varying(100),
	description character varying(255),
    CONSTRAINT specialite_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS specialite_praticien
(
    specialite_id bigint NOT NULL,
	praticien_id bigint  NOT NULL,
    CONSTRAINT specialite_praticien_pkey PRIMARY KEY (specialite_id, praticien_id),
	CONSTRAINT specialite_praticien_specialite_fk FOREIGN KEY (specialite_id) REFERENCES specialite (id),
	CONSTRAINT specialite_praticien_praticien_fk FOREIGN KEY (praticien_id) REFERENCES utilisateur (id)
);

CREATE TABLE IF NOT EXISTS lieu
(
    id bigserial NOT NULL,
	nom character varying(100),
	commentaires character varying(255),
	adresse_id bigint,
	praticien_id bigint,
    CONSTRAINT lieu_pkey PRIMARY KEY (id),
	CONSTRAINT lieu_adresse_fk FOREIGN KEY (adresse_id) REFERENCES adresse (id),
	CONSTRAINT lieu_praticien_fk FOREIGN KEY (praticien_id) REFERENCES utilisateur (id)
);

CREATE TABLE IF NOT EXISTS rendez_vous
(
    id bigserial NOT NULL,
	statut character varying(20),
	patient_id bigint,
	motif_id bigint,
    CONSTRAINT rendez_vous_pkey PRIMARY KEY (id),
	CONSTRAINT rendez_vous_praticien_fk FOREIGN KEY (patient_id) REFERENCES utilisateur (id),
	CONSTRAINT rendez_vous_motif_fk FOREIGN KEY (motif_id) REFERENCES motif (id)
);

CREATE TABLE IF NOT EXISTS creneau
(
    id bigserial NOT NULL,
	date timestamp without time zone,
	duree integer,
	praticien_id bigint,
	lieu_id bigint,
	rendez_vous_id bigint,
    CONSTRAINT creneau_pkey PRIMARY KEY (id),
	CONSTRAINT creneau_praticien_fk FOREIGN KEY (praticien_id) REFERENCES utilisateur (id),
	CONSTRAINT creneau_lieu_fk FOREIGN KEY (lieu_id) REFERENCES lieu (id),
	CONSTRAINT creneau_rendez_vous_fk FOREIGN KEY (rendez_vous_id) REFERENCES rendez_vous (id)
);

