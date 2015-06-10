<?php

require_once('model/classes.php');

try {
	if(!empty($_GET['page'])) {
		if($_GET['page'] == "palmares") {
			if(include("controller/palmares.php")) {
        $api = $scArray;
				$api["status"] = 1;
			} else {
				$api = array(
					"status" => 0,
					"source" => "root",
					"message" => "Erreur d'inclusion"
				);
			}
		}
	} else {
		if(include("controller/questions.php")) {
			$api = $categories;
      $api["status"] = 1;
		} else {
			$api = array(
				"status" => 0,
				"source" => "root",
				"message" => "Erreur d'inclusion"
			);
		}
	}
} catch(Exception $e) {
	$api = array(
		"status" => 0,
		"source" => $e->getTrace()[0]["class"],
		"message" => $e->getMessage()
	);
}
