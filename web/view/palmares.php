<?php

try {
	$scores = Score::getScores(10);
	$scArray = array();
	for($i = 0; $i < sizeof($scores); $i++) {
		array_push($scArray, array(
			"login" => utf8_encode($scores[$i]->getLogin()),
			"score" => $scores[$i]->getScore()
		));
	}
}catch(Exception $e) {
	echo json_encode(array("erreur" => $e->getMessage()));
}
