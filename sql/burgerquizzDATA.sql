-- phpMyAdmin SQL Dump
-- version 4.2.12deb2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Sam 23 Mai 2015 à 15:15
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

--
-- Contenu de la table `categorie`
--

INSERT INTO `categorie` (`nom_cat`) VALUES
('Dessin Animés'),
('ISEN'),
('Jeux Vidéos');

--
-- Contenu de la table `questions`
--

INSERT INTO `questions` (`intitule`, `num_reponse`, `reponse1`, `reponse2`) VALUES
('2 d''entre eux ont tapé ce texte:', 2, 'Les CSI', 'Les CIR'),
('A été diffusée pendant les années 80:', 0, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('C''est un héros de Nintendo:', 0, 'Mario', 'Link'),
('Elle est accompagnée d’animaux:', 1, 'Gigi', 'Magical DoReMi'),
('Elle possède une fée nommée Dodo:', 2, 'Gigi', 'Magical DoReMi'),
('Il à révé sur l'' île de Cocolint:', 2, 'Mario', 'Link'),
('Il protège le royaume champignon:', 1, 'Mario', 'Link'),
('Ils passent en colle:', 1, 'Les CSI', 'Les CIR'),
('Les clubs y sont situés:', 1, 'Rez de chaussée', '1er étage'),
('L’héroïne possède des pouvoirs magique:', 0, 'Gigi', 'Magical DoReMi'),
('On y affronte une tortue à trois tête:', 1, 'A Link to the Past', 'Ocarina of Time'),
('On y aperçoit un bateau nommé Solaris:', 1, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('On y combat des Skulltulas:', 2, 'A Link to the Past', 'Ocarina of Time'),
('On y ramasse des fragment de cœur:', 0, 'A Link to the Past', 'Ocarina of Time'),
('On y trouve des salles de cours:', 0, 'Rez de chaussée', '1er étage'),
('Ont des cours de Maths/Physique:', 0, 'Les CSI', 'Les CIR'),
('Un certain Shreder y intervient:', 2, 'Les Mystérieuses Cités d''Or', 'Les Tortues Ninjas'),
('Une salle consacrée aux devoir s''y situe:', 2, 'Rez de chaussée', '1er étage');

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

--
-- Contenu de la table `scores`
--

INSERT INTO `scores` (`login`, `score`) VALUES
('Jean', 85),
('kikou37', 14),
('Micheline', 85),
('Patrick', 24),
('thefifou', 56);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
