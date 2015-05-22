<?php 

class Connector {

	private $bdd;
	
	function __construct() {
		$host = "localhost";
		$db = "burgerquizz";
		$user = "alain";
		$pass = "chabat";

		$this->bdd = new PDO("mysql:host=$host;dbname=$db", $user, $pass);
	}

	/*
		Exemple de $options :

		$options = array(
			"where" => array(
				array("foo", "=", "bar"),
				array("blbl", ">", 5)
			),
			"order by" => array("foo", "desc"),
			"limit" => array(0, 10) // Ou array(10)
		);
	*/
	function Select($fields, $tables, $options) {
		$request = "SELECT $fields FROM $tables ";
		$arrayVerif = array();
		foreach($options as $name=>$value) {
			if(($upName = strtoupper($name)) == "WHERE") {
				$whereClause = " $upName ";
				foreach($value as $array) {
					if(sizeof($array) != 3) {
						throw new Exception('Nombre de paramètres incorrect (WHERE). Les paramètres passés sont : '
							.implode(',',$array));
					}

					$whereClause .= $array[0]." ".$array[1]." :".$array[0]." AND ";
					$arrayVerif[":".$array[0]] = $array[2];
				}
				if($substring = substr($whereClause, 0, -5)) {
					$request .= $substring;
				} else {
					throw new Exception('Problème lors de la création du substring');
				}
			} else if(($upName = strtoupper($name)) == "ORDER BY") {
				if(sizeof($value) != 2) {
					throw new Exception('Nombre de paramètres incorrects (ORDER BY). Les paramètres passés sont : '
						.implode(',', $value));
				}

				$request .= " ".$upName." ".implode(' ', $value);
			} else if(($upName = strtoupper($name)) == "LIMIT") {
				if(sizeof($value) == 1) {
					// La colonne "limit" ne contient qu'un nombre de champs
					$request .= " $upName ".$value[0];
				} else if(sizeof($value) == 2) {
					// La colonne "limit" contient un index de départ et un nombre de champs
					$request .= " $upName ".$value[0].",".$value[1];
				} else {
					throw new Exception('Nombre de paramètres incorrects (LIMIT). Les paramètres passés sont : '
						.implode(',', $value));
				}
			} else {
				throw new Exception('Argument '.strtoupper($name).' inconnu');
			}
		}

		return $bdd->prepare($request)->execute($arrayVerif)->fetchAll();
	}
}
