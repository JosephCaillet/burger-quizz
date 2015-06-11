<?php

/*******************************************************************************
* Classe Question																														 *
*	Auteur : Brendan Abolivier																									 *
* Fonction : Créer un objet représentatif d'une question											 *
*																																							 *
*	Attribut :																																	 *
*		$reponse1 : String																												 *
*		$reponse2 : String																												 *
*		$intitule : String																												 *
*		$bonneReponse : Int																												 *
*																																							 *
* Méthodes :																																	 *
*		__construct()																															 *
*		getArray()																																 *
*******************************************************************************/
class Question {
	private $reponse1;
	private $reponse2;
	private $intitule;
	private $bonneReponse;

	/*****************************************************************************
	* Méthode __construct()                                                      *
	*	Fonction : Constructeur, crée un objet Question à partir de son intitule   *
	*																																						 *
	*	Paramètres :																															 *
	*		$intitule (String) : Intitulé de la question														 *
	*																																						 *
	*	Retour : Aucun																														 *
	*****************************************************************************/
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

	/*****************************************************************************
	* Méthode getArray()                                               		       *
	*	Fonction : Renvoie un tableau associatif représentant l'objet courant  		 *
	*																																						 *
	*	Paramètres : Aucun																												 *
	*																																						 *
	*	Retour :																														 			 *
	*		Tableau contenant l'intulé et un entier symbolisant la bonne réponse de	 *
	*			la question																														 *
	*****************************************************************************/
	function getArray() {
		return array(
			"intitule" => $this->intitule,
			"bonneReponse" => $this->bonneReponse
		);
	}
}
