<?php

require_once('model/classes.php');

try {
	Score::add("Yolo", 200);
}catch(Exception $e) {
	echo("Erreur : ".$e->getMessage());
}
