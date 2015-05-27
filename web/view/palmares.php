<?php

try {
	$scores = Score::getScores(10);
	$scArray = array();
	for($i = 0; $i < sizeof($scores); $i++) {
		array_push($scArray, array(
			"login" => $scores[$i]->getLogin(),
			"score" => $scores[$i]->getScore()
		));
		echo json_encode($scArray);
	}
}catch(Exception $e) {
	echo json_encode(array("erreur" => $e->getMessage()));
}
