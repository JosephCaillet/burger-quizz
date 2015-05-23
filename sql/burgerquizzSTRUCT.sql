-- phpMyAdmin SQL Dump
-- version 4.2.12deb2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Sam 23 Mai 2015 à 15:14
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `intitule` varchar(150) NOT NULL,
  `num_reponse` tinyint(4) NOT NULL,
  `reponse1` varchar(50) NOT NULL DEFAULT '',
  `reponse2` varchar(50) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `reponses`
--

CREATE TABLE IF NOT EXISTS `reponses` (
  `reponse1` varchar(50) NOT NULL,
  `reponse2` varchar(50) NOT NULL,
  `nom_cat` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `scores`
--

CREATE TABLE IF NOT EXISTS `scores` (
  `login` varchar(20) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
