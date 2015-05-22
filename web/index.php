<?php

require_once('classes.php');

//	header("Content-Type:application/json");

//	echo json_encode(array("Message" => "Hello world;"));


$bdd = new Connector();

$options = array(
	"where" => array(
		array("login", "=", "Dragavnir"),
	),
	"order by" => array("score", "desc"),
	"limit" => array(0, 10) // Ou array(10)
);

var_dump($bdd->Select("*", "scores", $options));
