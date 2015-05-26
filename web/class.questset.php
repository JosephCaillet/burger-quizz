<?php

class Questset {
	private $categorie;
	private $reponse1;
	private $reponse2;
	private $intitules;
	private $bdd;

	function __construct($reponses) {
		if(is_array($reponses)) {
			$this->reponse1 = $reponse[0];
			$this->reponse2 = $reponse[1];
			try {
				$this->bdd = new Connector();

				$options = array(
					"where" => array(
						array("reponse1", "=", $reponse1),
						array("reponse2", "=", $reponse2)
					)
				);


			} catch(Exception $e) {
				throw $e;
			}
		} else {
			throw new Exception('Un tableau de réponses est attendu dans le constructeur.');
		}
	}
}
