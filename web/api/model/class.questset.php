<?php

/*******************************************************************************
* Classe Questset																														 *
*	Auteur : Brendan Abolivier																									 *
* Fonction : Créer un objet représentatif d'un jeu de questions (thème)				 *
*																																							 *
*	Attribut :																																	 *
*		$reponse1 : String																												 *
*		$reponse2 : String																												 *
*		$questions : Array (Question)																							 *
*																																							 *
* Méthodes :																																	 *
*		__construct()																															 *
*		getArray()																																 *
*******************************************************************************/
class Questset {
	private $reponse1;
	private $reponse2;
	private $questions;

	/*****************************************************************************
	* Méthode __construct()                                                      *
	*	Fonction : Constructeur, crée un objet Questset (thème) à partir de ses 	 *
	*						 réponses   																										 *
	*																																						 *
	*	Paramètres :																															 *
	*		$reponses (Array (String)) : Tableau associatif contenant les deux   		 *
	*												 				 réponses du thème													 *
	*																																						 *
	*	Retour : Aucun																														 *
	*																																						 *
	*	Exceptions :																															 *
	*		expected_questset_array : Le paramètre n'est pas un tableau							 *
	*****************************************************************************/
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
					),
					"order by" => array("rand()")
				);

				$questions = $bdd->Select('*', 'questions', $options);

				foreach($questions as $question) {
					array_push($this->questions, new Question($question['intitule']));
				}

			} catch(Exception $e) {
				throw $e;
			}
		} else {
			throw new Exception('expected_questset_array');
		}
	}

	/*****************************************************************************
	* Méthode getArray()                                               		       *
	*	Fonction : Renvoie un tableau associatif représentant l'objet courant  		 *
	*																																						 *
	*	Paramètres : Aucun																												 *
	*																																						 *
	*	Retour :																														 			 *
	*		Tableau contenant les deux réponses identifiant le thème ainsi qu'un		 *
	*			tableau de questions (voir classe Question)														 *
	*****************************************************************************/
	function getArray() {
		$questions = array();
		foreach($this->questions as $question) {
			array_push($questions, $question->getArray());
		}

		return array(
			"reponse1" => $this->reponse1,
			"reponse2" => $this->reponse2,
			"questions" => $questions
		);
	}
}
