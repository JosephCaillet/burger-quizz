<?php

// Fichier de tests du modèle

require_once('model/classes.php');
header("Content-Type:application/json");

try{
	$categoriesObj = Categorie::randSelect();
	$categories = array();
	foreach($categoriesObj as $categorie) {
		array_push($categories, $categorie->getArray());
	}
	echo json_encode($categories);
}catch(Exception $e) {
	echo("<strong>Erreur : </strong>".$e->getMessage());
}
