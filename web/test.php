<?php

// Fichier de tests du modèle

require_once('model/classes.php');

try{
	$categories = Categorie::randSelect();
	var_dump($categories);
}catch(Exception $e) {
	echo("<strong>Erreur : </strong>".$e->getMessage());
}
