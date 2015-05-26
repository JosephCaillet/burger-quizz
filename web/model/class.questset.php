<?php

class Questset {
	private $reponse1;
	private $reponse2;
	private $questions;

	function __construct($reponses) {
		if(is_array($reponses)) {
			$this->questions = array();
			$this->reponse1 = $reponses[0];
			$this->reponse2 = $reponses[1];
			try {
				$bdd = new Connector();

				$options = array(
					"where" => array(
						array("reponse1", "=", $this->reponse1),
						array("reponse2", "=", $this->reponse2)
					)
				);

				$questions = $bdd->Select('*', 'questions', $options);

				foreach($questions as $question) {
					array_push($this->questions, new Question($question['intitule']));
				}

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

	function getLength() {
		return sizeof($this->questions);
	}

	function getArray() {
		$questions = array();
		foreach($this->questions as $question) {
			array_push($questions, $question->getArray());
		}

		return array(
			"reponse1" => utf8_encode($this->reponse1),
			"reponse2" => utf8_encode($this->reponse2),
			"questions" => $questions
		);
	}
}
