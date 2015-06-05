DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS reponses;
DROP TABLE IF EXISTS categorie;
DROP TABLE IF EXISTS scores;

CREATE TABLE categorie(
        nom_cat Varchar (30) NOT NULL ,
        PRIMARY KEY (nom_cat )
)ENGINE=InnoDB;

CREATE TABLE reponses(
        reponse1 Varchar (50) NOT NULL ,
        reponse2 Varchar (50) NOT NULL ,
        nom_cat  Varchar (30) ,
        INDEX (reponse2),
        PRIMARY KEY (reponse1 ,reponse2 )
)ENGINE=InnoDB;

CREATE TABLE questions(
        intitule    Varchar (150) NOT NULL ,
        num_reponse TinyINT ,
        reponse1    Varchar (50) ,
        reponse2    Varchar (50) ,
        PRIMARY KEY (intitule,reponse1,reponse2 )
)ENGINE=InnoDB;

CREATE TABLE scores(
        login Varchar (20) NOT NULL ,
        score Int ,
        PRIMARY KEY (login )
)ENGINE=InnoDB;

ALTER TABLE reponses ADD CONSTRAINT FK_Reponses_nom_cat FOREIGN KEY (nom_cat) REFERENCES categorie(nom_cat) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE questions ADD CONSTRAINT FK_Questions_reponse2 FOREIGN KEY (reponse2) REFERENCES reponses(reponse2) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE questions ADD CONSTRAINT FK_Questions_reponse1 FOREIGN KEY (reponse1) REFERENCES reponses(reponse1) ON DELETE CASCADE ON UPDATE CASCADE;
