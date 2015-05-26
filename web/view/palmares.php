<?php

require_once('../model/classes.php');

$bdd = new Connector();

$options = array(
	"order by" => array("score", "desc"),
	"limit" => array(10)
);

try {
	$res = $bdd->Select("*", "scores", $options);
} catch(Exception $e) {
	echo("Erreur : ".$e->getMessage());
}

// $res contient les résultats
