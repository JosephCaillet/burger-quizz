DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS reponses;
DROP TABLE IF EXISTS categorie;
DROP TABLE IF EXISTS scores;


CREATE TABLE IF NOT EXISTS `categorie` (
  `nom_cat` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `categorie` (`nom_cat`) VALUES
('Dessin Animés'),
('ISEN'),
('Jeux Vidéos');


CREATE TABLE IF NOT EXISTS `questions` (
  `intitule` varchar(150) NOT NULL,
  `num_reponse` tinyint(4) NOT NULL,
  `reponse1` varchar(50) NOT NULL,
  `reponse2` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `questions` (`intitule`, `num_reponse`, `reponse1`, `reponse2`) VALUES
('2 d''entre eux ont tapé ce texte', 2, 'Les CSI', 'Les CIR'),
('A été diffusée pendant les années 80', 0, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('C''est un héros de Nintendo', 0, 'Mario', 'Link'),
('Elle est accompagnée d’animaux', 1, 'Gigi', 'Magical DoReMi'),
('Elle possède une fée nommée Dodo', 2, 'Gigi', 'Magical DoReMi'),
('Il a rêvé sur l''île de Cocolint', 2, 'Mario', 'Link'),
('Il protège le royaume champignon', 1, 'Mario', 'Link'),
('Ils passent en colle', 1, 'Les CSI', 'Les CIR'),
('Les clubs y sont situés', 1, 'Rez de chaussée', '1er étage'),
('L''héroïne possède des pouvoirs magique', 0, 'Gigi', 'Magical DoReMi'),
('On y affronte une tortue à trois tête', 1, 'A Link to the Past', 'Ocarina of Time'),
('On y aperçoit un bateau nommé Solaris', 1, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('On y combat des Skulltulas', 2, 'A Link to the Past', 'Ocarina of Time'),
('On y ramasse des fragment de cœur', 0, 'A Link to the Past', 'Ocarina of Time'),
('On y trouve des salles de cours', 0, 'Rez de chaussée', '1er étage'),
('Ont des cours de Maths/Physique', 0, 'Les CSI', 'Les CIR'),
('Un certain Shreder y intervient', 2, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('Une salle consacrée aux devoir s''y situe', 2, 'Rez de chaussée', '1er étage');


CREATE TABLE IF NOT EXISTS `reponses` (
  `reponse1` varchar(50) NOT NULL,
  `reponse2` varchar(50) NOT NULL,
  `nom_cat` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `reponses` (`reponse1`, `reponse2`, `nom_cat`) VALUES
('Gigi', 'Magical DoReMi', 'Dessin Animés'),
('Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas', 'Dessin Animés'),
('Les CSI', 'Les CIR', 'ISEN'),
('Rez de chaussée', '1er étage', 'ISEN'),
('A Link to the Past', 'Ocarina of Time', 'Jeux Vidéos'),
('Mario', 'Link', 'Jeux Vidéos');


CREATE TABLE IF NOT EXISTS `scores` (
  `login` varchar(20) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `scores` (`login`, `score`) VALUES
('azerty', 28),
('David', 19),
('Dragavnir', 666),
('Finistère', 29),
('Joseph', 42),
('K.S.', 20),
('Loic', 10),
('Marc F.', 0),
('Patrick', 24),
('Thomas', 33);


ALTER TABLE `categorie`
 ADD PRIMARY KEY (`nom_cat`);

ALTER TABLE `questions`
 ADD PRIMARY KEY (`intitule`,`reponse1`,`reponse2`), ADD KEY `FK_Questions_reponse2` (`reponse2`), ADD KEY `FK_Questions_reponse1` (`reponse1`);

ALTER TABLE `reponses`
 ADD PRIMARY KEY (`reponse1`,`reponse2`), ADD KEY `reponse2` (`reponse2`), ADD KEY `FK_Reponses_nom_cat` (`nom_cat`);

ALTER TABLE `scores`
 ADD PRIMARY KEY (`login`);

ALTER TABLE `questions`
ADD CONSTRAINT `FK_Questions_reponse1` FOREIGN KEY (`reponse1`) REFERENCES `reponses` (`reponse1`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `FK_Questions_reponse2` FOREIGN KEY (`reponse2`) REFERENCES `reponses` (`reponse2`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `reponses`
ADD CONSTRAINT `FK_Reponses_nom_cat` FOREIGN KEY (`nom_cat`) REFERENCES `categorie` (`nom_cat`) ON DELETE CASCADE ON UPDATE CASCADE;
