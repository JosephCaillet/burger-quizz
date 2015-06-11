Burger Quizz
============

Créé dans le cadre du projet de fin d'année de la promo 2018 de CIR2 de l'ISEN Brest/Rennes, le Burger Quizz est une adaptation numérique du jeu télévisé éponyme, plus précisément d'une épreuve spécifique de ce jeu : le "Sel ou Poivre".

Dans cette épreuve de rapidité, les joueurs sont confrontés à des jeux de questions correspondant à plusieurs thèmes sur différentes catégories. Pour plus d'informations, consulter la rubrique "Règles" du site une fois ce dernier installé.

Installation
------------

### Pré-requis

Cette application a été conçue pour fonctionner avec PHP 5.6, MySQL 5.5 et Node.js. Les tests ont été réalisés avec le serveur Web Apache 2.4 ; il est conseillé pour des résultats optimisés, mais libre à vous d'utiliser le serveur Web de votre choix.

Sous Debian (et ses dérivés), la commande permettant l'installation de ces pré-requis est :

`# apt-get install apache2 php5 mysql nodejs npm`

### Installation de l'application

Pour installer le Burger Quizz sur votre hébergement, déplacez tout le contenu du présent dossier dans le dossier dans lequel vous souhaitez installer le jeu.

### Installation de la base de données

Le dossier `sql/` situé à la racine du présent dossier contient trois fichiers SQL :

* `CTbq.sql` contient le squelette de la base de données
* `ITbq.sql` contient le contenu de la base de données, sans son squelette
* `CTITbq.sql` contient le squelette et le contenu de la base de données

En fonction de vos besoins, exécutez l'un des scripts sur votre serveur MySQL (via PhpMyAdmin, par exemple). Il vous faudra au préalable créer une base de données (que vous pouvez nommer à votre guise) en utilisant le jeu de caractères (collation) `utf8_general_ci`.

### Installation du mode multijoueur

Si vous souhaitez héberger le serveur Node.js permettant le mode de jeu multijoueur sur un autre hébergement, ou à un autre emplacement sur l'hébergement courant, il vous suffit d'y déplacer le dossier `multi/` présent à la racine du présent dossier.

Pour l'installation des composants nécessaires au mode multijoueur, ouvrez un terminal et placez-vous dans le dossier contenant le serveur multijoueur (par défaut, `multi/server/`) puis exécutez la commande :

`# npm install`

Configuration
-------------

Vous trouverez à la racine du présent dossier un fichier nommé `params.cfg`. Ce dernier contient différents paramètres permettant la configuration du Burger Quizz sur votre serveur :

* `node_host` vous permet de définir l'adresse de l'hôte sur lequel est hébergé le serveur Node.js (qui peut être le même que celui hébergeant le Burger Quizz).
* `db_host` vous permet de définir l'hôte auquel se connecter afin d'accéder à la base de données.
* `db_name` vous permet d'indiquer la base de données dans laquelle sont stockées les données du Burger Quizz.
* `db_user` vous permet de définir le nom de l'utilisateur se connectant à la base de données.
* `db_pass` vous permet de définir le mot de passe de l'utilisateur se connectant à la base de données.

Utilisation
-----------

Pour jouer au Burger Quizz, une fois les étapes précédentes terminées, connectez-vous à l'aide de votre navigateur à l'adresse correspondant à l'emplacement dans lequel vous l'avez installé.

Pour activer le jeu multijoueur, ouvrez un terminal, déplacez-vous jusqu'au dossier `multi/` présent à la racine du présent dossier (si vous ne l'avez pas déplacé), et lancez le serveur Node.js :

`$ nodejs server.js`

ou

`$ node server.js`

selon votre installation.

Vous pouvez maintenant profiter en toute tranquilité du Burger Quizz.

Feedback
--------

Pour tout retour sur l'application, n'hésitez pas à envoyer un e-mail à <contact@brendanabolivier.com>
