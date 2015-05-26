<?php

class Question {
	private $reponse1;
	private $reponse2;
	private $intitule;
	private $bonneReponse;

	function __construct($intitule) {
		try{
			$this->intitule = $intitule;

			$bdd = new Connector();

			$options = array(
				"where" => array(
					array("intitule", "=", $intitule)
				)
			);
			$question = $bdd->Select('*', 'questions', $options);

			$this->reponse1 = $question[0]['reponse1'];
			$this->reponse2 = $question[0]['reponse2'];
			
			$this->bonneReponse = $question[0]['num_reponse'];
		} catch(Exception $e) {
			throw $e;
		}
	}

	function getIntitule() {
		return $this->intitule;
	}

	function getReponses() {
		return array(
			"reponse1" => $this->reponse1,
			"reponse2" => $this->reponse2
		);
	}

	function getBonneReponse() {
		return $this->bonneReponse;
	}

	function getArray() {
		return array(
			"intitule" => utf8_encode($this->intitule),
			"bonneReponse" => utf8_encode($this->bonneReponse)
		);
	}
}
