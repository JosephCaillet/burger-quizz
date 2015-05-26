<?php

// Fichier de tests du modèle

require_once('model/classes.php');

try{
	$categories = Categorie::randSelect();
	foreach($categories as $categorie) {
		json_encode($categorie->getArray());
	}
}catch(Exception $e) {
	echo("<strong>Erreur : </strong>".$e->getMessage());
}
