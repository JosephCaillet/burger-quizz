<?php

/*******************************************************************************
* Classe Connector																														 *
*	Auteur : Brendan Abolivier																									 *
* Fonction : Permettre une gestion plus facile et plus claire de la connexion  *
*						 au serveur MySQL																									 *
*																																							 *
*	Attribut :																																	 *
*		$bdd : objet PDO																													 *
*																																							 *
* Méthodes :																																	 *
*		__construct()																															 *
*		Select()																																	 *
*		Insert()																																	 *
*		Update()																																	 *
*******************************************************************************/
class Connector {

	private $bdd;

	/*****************************************************************************
	* Méthode __construct()                                                      *
	*	Fonction : Constructeur, initie la connexion à la base de données via PDO, *
	*					   en utilisant les paramètres définis dans le fichier params.cfg  *
	*						 par l'utilisateur                                               *
	*																																						 *
	*	Paramètres : Aucun																												 *
	*																																						 *
	*	Retour : Aucun																														 *
	*****************************************************************************/
	function __construct() {
		$params = file_get_contents("../params.cfg");
		preg_match_all('/db_(.+)\: (.+)/', $params, $matches);
		$dbconnect = array();
		for($i = 0; $i < sizeof($matches[0]); $i++) {
			$dbconnect[$matches[1][$i]] = $matches[2][$i];
		}

		$options = array(
		    PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8',
		);

		$this->bdd = new PDO("mysql:host=".$dbconnect["host"].";dbname="
								.$dbconnect["dbname"], $dbconnect["user"], $dbconnect["pass"],
																																		$options);
		$this->bdd->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	}

	/*****************************************************************************
	*	Méthode Select()																													 *
	*	Fonction : Effectue une insertion dans la base de données									 *
	*																																						 *
	* Paramètres :																															 *
	* 	$fields (String) : Champs à sélectionner, séparés par une virgule				 *
	*		$tables (String) : Tables dans lesquelles effectuer les champs, séparées *
	*											 par une virgule																			 *
	*		$options (Array (mixed)) : Tableau contenant les différentes clauses de  *
	*		 (mixed : Array, String) | la requête.																	 *
	*															 Exemple de tableau :													 *
	*																$options = array(														 *
	*																	"where" => array(													 *
	*																		array("foo", "=", "bar"),								 *
	*																		array("blbl", ">", 5)										 *
	*																	),																				 *
	*																	"order by" => array("foo", "desc"),				 *
	*																	"limit" => array(0, 10) // Ou array(10)		 *
	*																);																					 *
	*											 Si cet argument n'est pas passé, la méthode s'exécute *
	*											 avec un tableau vide.																 *
	*																																						 *
	*	Retour :																																	 *
	*		Tableau contenant les résultats renvoyés par la base de données					 *
	*		null si la requête a renvoyé un ensemble vide														 *
	*																																						 *
	*	Exceptions :																															 *
	*		wrong_arg_nmbr_where : Nombre d'arguments insuffisant sur une partie de  *
	*													 la clause WHERE																	 *
	*		wrong_arg_nmbr_order_by : Nombre d'arguments insuffisant sur la clause   *
	*															ORDER BY																			 *
	*		wrong_arg_numbr_limit : Nombre d'arguments insuffisant sur la clause     *
	*														LIMIT                                            *
	*		unknown_arg : Le tableau contient une clause inconnue ou non supportée   *
	*****************************************************************************/
	function Select($fields, $tables, $options = array()) {
		$request = "SELECT $fields FROM $tables ";
		$arrayVerif = array();
		foreach($options as $name=>$value) {
			if(($upName = strtoupper($name)) == "WHERE") {
				$whereClause = " $upName ";
				foreach($value as $array) {
					if(sizeof($array) != 3 && sizeof($array) != 4) {
						throw new Exception('wrong_arg_nmbr_where');
					}
					if(sizeof($array) == 3) {
						$whereClause .= $array[0]." ".$array[1]." ? AND ";
						array_push($arrayVerif, $array[2]);
					} else {
						$whereClause .= $array[0]." ".$array[1]." ".$array[2]." AND ";
					}
				}
				$request .= substr($whereClause, 0, -5);
			} else if(($upName = strtoupper($name)) == "ORDER BY") {
				if(sizeof($value) != 2 && substr($value[0], -2) != "()") {
					throw new Exception('wrong_arg_nmbr_order_by');
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
					throw new Exception('wrong_arg_numbr_limit');
				}
			} else {
				throw new Exception('unknown_arg');
			}
		}

		$stmt = $this->bdd->prepare($request);

		if($stmt->execute($arrayVerif)) {
			return $stmt->fetchAll();
		} else {
			return null;
		}
	}

	/*****************************************************************************
	*	Méthode Insert()																													 *
	*	Fonction : Effectue une insertion dans la base de données									 *
	*																																						 *
	*	Paramètres :																															 *
	* 	$table (String) : Table dans laquelle effectuer l'insertion							 *
	*		$values (Array (String)) : Valeurs à insérer dans la table (tableau   	 *
	*															 associatif)																	 *
	*																																						 *
	*	Retour : Aucun																														 *
	*****************************************************************************/
	function Insert($table, $values) {
		$request = "INSERT INTO $table(";
		$valeurs = "VALUES(";
		$arrayVerif = array();
		foreach($values as $name=>$value) {
			$request .= $name.",";
			$valeurs .= "?,";
				array_push($arrayVerif, $value);
		}

		$request = substr($request, 0, -1).") ".substr($valeurs, 0, -1).")";

		$stmt = $this->bdd->prepare($request);

		$stmt->execute($arrayVerif);
	}

	/*****************************************************************************
	*	Méthode Update()																													 *
	*	Fonction : Modifie une ou plusieurs valeur(s) dans la base de données			 *
	*																																						 *
	*	Paramètres :																															 *
	* 	$table (String) : Table dans laquelle effectuer la mise à jour					 *
	*		$update (Array (mixed)) : Tableau contenant la (ou les)	valeur(s) à	  	 *
	*  	 (mixed : Array, String)| modifier et la clause WHERE correspondante		 *
	*																																						 *
	*	Retour : Aucun																														 *
	*****************************************************************************/
	function Update($table, $update) {
		$request = "UPDATE $table SET ";
		$arrayVerif = array();
		foreach($update['set'] as $name=>$value) {
			$request .= $name."=?,";
			array_push($arrayVerif, $value);
		}
		$request = substr($request, 0, -1)." WHERE ";
		foreach($update['where'] as $value) {
			$request .= $value[0].$value[1]."? AND ";
			array_push($arrayVerif, $value[2]);
		}
		$request = substr($request, 0, -5);

		$stmt = $this->bdd->prepare($request);
		$stmt->execute($arrayVerif);
	}

	/*****************************************************************************
	*	Méthode beginTransaction()																								 *
	*	Fonction : Initie une transaction MySQL																		 *
	*																																						 *
	*	Paramètres : Aucun																												 *
	*																																						 *
	* Retour : Aucun																														 *
	*****************************************************************************/
	function beginTransaction() {
		$this->bdd->beginTransaction();
	}

	/*****************************************************************************
	*	Méthode commit()																								 					 *
	*	Fonction : Publie les modifications sur le serveur MySQL et ferme la			 *
	*						 transaction en cours																						 *
	*																																						 *
	*	Paramètres : Aucun																												 *
	*																																						 *
	* Retour : Aucun																														 *
	*****************************************************************************/
	function commit() {
		$this->bdd->commit();
	}
}
