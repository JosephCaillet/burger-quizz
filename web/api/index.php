<?php

require_once('model/classes.php');

header("Content-Type:application/json");

try {
	if(!empty($_GET['page'])) {
		if($_GET['page'] == "palmares") {
			if(include("controller/palmares.php")) {
				$scArray["status"] = 1;
				echo json_encode($scArray);
			} else {
				echo json_encode(array(
					"status" => 0,
					"source" => "root",
					"message" => "Erreur d'inclusion"
				));
			}
		}
	} else {
		if(include("controller/questions.php")) {
			$categories["status"] = 1;
			echo json_encode($categories);
		} else {
			echo json_encode(array(
				"status" => 0,
				"source" => "root",
				"message" => "Erreur d'inclusion"
			));
		}
	}
} catch(Exception $e) {
	echo json_encode(array(
		"status" => 0,
		"source" => $e->getTrace()[0]["class"],
		"message" => $e->getMessage()
	));
}
