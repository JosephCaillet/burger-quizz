<?php

require_once('classes.php');

$bdd = new Connector();

$options = array(
	"order by" => array("score", "desc"),
	"limit" => array(0, 10) // Ou array(10)
);

try {
	$res = $bdd->Select("*", "scores", $options);
} catch(Exception $e) {
	echo("Erreur : ".$e->getMessage());
}

echo("<ol>");

foreach($res as $row) {
	echo(utf8_encode("<li>".$row["login"]." (".$row["score"].")</li>"));
}

echo("</ol>");
