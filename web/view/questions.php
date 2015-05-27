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
}catch(Exception $e) {
	echo json_encode(array("erreur" => $e->getMessage()));
}
