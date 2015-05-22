<?php 

class Connector() {

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
			);
			"order by" => array(0, 10, "desc");
		);
	*/
	function Select($fields, $table, $options) {
		$request = "SELECT $fields FROM $table ";
		$arrayVerif = array();
		foreach($options as $name=>$value) {
/*			$request += " $name :$name";
			$arrayVerif[":$name"] = $value;*/
		}
	}
}
