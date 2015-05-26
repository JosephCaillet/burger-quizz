<?php

// Fichier de tests du modèle

require_once('model/classes.php');

try{
	$questset = new Questset(array("Les CSI", "Les CIR"));
	var_dump($questset);
}catch(Exception $e) {
	echo("<strong>Erreur : </strong>".$e->getMessage());
}
