-- phpMyAdmin SQL Dump
-- version 4.2.12deb2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Sam 23 Mai 2015 à 15:16
-- Version du serveur :  5.5.43-0+deb8u1
-- Version de PHP :  5.6.7-1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `burgerquizz`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE IF NOT EXISTS `categorie` (
  `nom_cat` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `categorie`
--

INSERT INTO `categorie` (`nom_cat`) VALUES
('Dessin Animés'),
('ISEN'),
('Jeux Vidéos');

-- --------------------------------------------------------

--
-- Structure de la table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `intitule` varchar(150) NOT NULL,
  `num_reponse` tinyint(4) NOT NULL,
  `reponse1` varchar(50) NOT NULL DEFAULT '',
  `reponse2` varchar(50) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `questions`
--

INSERT INTO `questions` (`intitule`, `num_reponse`, `reponse1`, `reponse2`) VALUES
('2 d''entre eux ont tapé ce texte', 2, 'Les CSI', 'Les CIR'),
('A été diffusée pendant les années 80', 0, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('C''est un héros de Nintendo', 0, 'Mario', 'Link'),
('Elle est accompagnée d’animaux', 1, 'Gigi', 'Magical DoReMi'),
('Elle possède une fée nommée Dodo', 2, 'Gigi', 'Magical DoReMi'),
('Il à révé sur l'' île de Cocolint', 2, 'Mario', 'Link'),
('Il protège le royaume champignon', 1, 'Mario', 'Link'),
('Ils passent en colle', 1, 'Les CSI', 'Les CIR'),
('Les clubs y sont situés', 1, 'Rez de chaussée', '1er étage'),
('L’héroïne possède des pouvoirs magique', 0, 'Gigi', 'Magical DoReMi'),
('On y affronte une tortue à trois tête', 1, 'A Link to the Past', 'Ocarina of Time'),
('On y aperçoit un bateau nommé Solaris', 1, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('On y combat des Skulltulas', 2, 'A Link to the Past', 'Ocarina of Time'),
('On y ramasse des fragment de cœur', 0, 'A Link to the Past', 'Ocarina of Time'),
('On y trouve des salles de cours', 0, 'Rez de chaussée', '1er étage'),
('Ont des cours de Maths/Physique', 0, 'Les CSI', 'Les CIR'),
('Un certain Shreder y intervient', 2, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('Une salle consacrée aux devoir s''y situe', 2, 'Rez de chaussée', '1er étage');

-- --------------------------------------------------------

--
-- Structure de la table `reponses`
--

CREATE TABLE IF NOT EXISTS `reponses` (
  `reponse1` varchar(50) NOT NULL,
  `reponse2` varchar(50) NOT NULL,
  `nom_cat` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `reponses`
--

INSERT INTO `reponses` (`reponse1`, `reponse2`, `nom_cat`) VALUES
('Gigi', 'Magical DoReMi', 'Dessin Animés'),
('Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas', 'Dessin Animés'),
('Les CSI', 'Les CIR', 'ISEN'),
('Rez de chaussée', '1er étage', 'ISEN'),
('A Link to the Past', 'Ocarina of Time', 'Jeux Vidéos'),
('Mario', 'Link', 'Jeux Vidéos');

-- --------------------------------------------------------

--
-- Structure de la table `scores`
--

CREATE TABLE IF NOT EXISTS `scores` (
  `login` varchar(20) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `scores`
--

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

--
-- Index pour les tables exportées
--

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
 ADD PRIMARY KEY (`nom_cat`);

--
-- Index pour la table `questions`
--
ALTER TABLE `questions`
 ADD PRIMARY KEY (`intitule`,`reponse1`,`reponse2`), ADD KEY `FK_Questions_reponse2` (`reponse2`), ADD KEY `FK_Questions_reponse1` (`reponse1`);

--
-- Index pour la table `reponses`
--
ALTER TABLE `reponses`
 ADD PRIMARY KEY (`reponse1`,`reponse2`), ADD KEY `reponse2` (`reponse2`), ADD KEY `FK_Reponses_nom_cat` (`nom_cat`);

--
-- Index pour la table `scores`
--
ALTER TABLE `scores`
 ADD PRIMARY KEY (`login`);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `questions`
--
ALTER TABLE `questions`
ADD CONSTRAINT `FK_Questions_reponse1` FOREIGN KEY (`reponse1`) REFERENCES `reponses` (`reponse1`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `FK_Questions_reponse2` FOREIGN KEY (`reponse2`) REFERENCES `reponses` (`reponse2`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `reponses`
--
ALTER TABLE `reponses`
ADD CONSTRAINT `FK_Reponses_nom_cat` FOREIGN KEY (`nom_cat`) REFERENCES `categorie` (`nom_cat`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
