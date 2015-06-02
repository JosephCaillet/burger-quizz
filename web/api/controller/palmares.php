<?php

if(!empty($_POST['login']) && !empty($_POST['score'])) {
	Score::add($_POST['login'], $_POST['score']);
	$scArray = array(
		"status" => 1,
		"message" => "score_add_success"
	);
} else {
	$scores = Score::getScores(10);
	$scArray = array();
	for($i = 0; $i < sizeof($scores); $i++) {
		array_push($scArray, array(
			"login" => utf8_encode($scores[$i]->getLogin()),
			"score" => $scores[$i]->getScore()
		));
	}
}
