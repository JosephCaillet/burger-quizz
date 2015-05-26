<?php

class Questset {
	private $categorie;
	private $reponse1;
	private $reponse2;
	private $questions;

	function __construct($reponses) {
		if(is_array($reponses)) {
			$this->intitules = array();
			$this->reponse1 = $reponse[0];
			$this->reponse2 = $reponse[1];
			try {
				$bdd = new Connector();

				$options = array(
					"where" => array(
						array("reponse1", "=", $reponse1),
						array("reponse2", "=", $reponse2)
					)
				);

				$questions = $bdd->Select('*', 'questions', $options);

				foreach($questions as $question) {
					array_push($this->questions, new Question($question['intitule']));
				}

				$this->categorie = $bdd->Select('*', 'reponses', $options)['nom_cat'];
			} catch(Exception $e) {
				throw $e;
			}
		} else {
			throw new Exception('Un tableau de réponses est attendu dans le constructeur.');
		}
	}

	function getQuestions() {
		return $this->questions;
	}
}
