Burger Quizz: Interface Administrateur JAVA
===========================================

Créé dans le cadre du projet de fin d'année de la promo 2018 de CIR2 de l'ISEN Brest/Rennes, le Burger Quizz est une adaptation numérique du jeu télévisé éponyme, plus précisément d'une épreuve spécifique de ce jeu : le "Sel ou Poivre".

Dans cette épreuve de rapidité, les joueurs sont confrontés à des jeux de questions correspondant à plusieurs thèmes sur différentes catégories. Pour plus d'informations, consulter la rubrique "Règles" du site une fois ce dernier installé.

Cette application est composée d'une application Web en PHP et Javascript, ainsi que d'une application Java permettant d'administrer la base de questions.

Structure du dossier
--------------------

* `conf.xml` contient la configuration de la connexion à la base de données. Peut être modifié "à la main", ou depuis l'application. S'il n'existe pas, l'application le créé et le remplis avec une configuration par défaut.
* `lib` contient le .jar jdbc de connexion à mysql.
* `rsc` contient les ressources graphiques.
* `src` contient les sources de l'application.
* `BGAdmin.jar` le jar executable.

Execution depuis le jar
-----------------------

Le jar executable contient le driver jdbc. Pour l'executer:
`java -jar BGAdmin.jar`

Compilation et execution depuis les sources
-------------------------------------------

###Compilation depuis les sources

0. Avoir un JDK Java installé
1. Allez dans le repertoire `src`
2. Y copier le fichier `lib/mysql-connector-java-5.1.35-bin.jar`
3. Lancer la commande `javac -cp mysql-connector-java-5.1.35-bin.jar:. MainAdminBddBurgerQuizz.java`

###Execution depuis les sources compilées

0. Avoir un JRE Java installé
1. Allez dans le repertoire `src`
3. Y copier le dossier `rsc`
4. Lancer `java -cp mysql-connector-java-5.1.35-bin.jar:. MainAdminBddBurgerQuizz`

Génération de la Javadoc
------------------------
0. Avoir un JDK Java installé
1. Allez dans le repertoire `src`
2. Lancer la commande `javadoc -private -d ./REPERTOIRE_OU_STOCKER_LA_DOCUMENTATION -sourcepath . -subpackages GestionBddDAO InterfaceGraphique InterfaceGraphique.DialogBoxes GestionErreurs Modele MainAdminBddBurgerQuizz.java -encoding "utf8" -docencoding "utf8" -charset "utf8"`
3. Ouvrir le fichier REPERTOIRE_OU_STOCKER_LA_DOCUMENTATION/index.html
