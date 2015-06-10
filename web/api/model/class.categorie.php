<?php

/*******************************************************************************
* Classe Categorie																														 *
*	Auteur : Brendan Abolivier																									 *
* Fonction : Créer un objet représentatif d'une catégorie											 *
*																																							 *
*	Attribut :																																	 *
*		$bdd : Connector																												   *
*		$nomCat : String																												 	 *
*		$questsets : Array (Questset)																							 *
*																																							 *
* Méthodes :																																	 *
*		__construct()																															 *
*		randSelect() [statique]																										 *
*		getArray()																																 *
*******************************************************************************/
class Categorie {
	private $bdd;
	private $nomCat;
	private $questsets;

	/*****************************************************************************
	* Méthode __construct()                                                      *
	*	Fonction : Constructeur, crée un objet Categorie à partir de son nom			 *
	*																																						 *
	*	Paramètres :																															 *
	*		$nomCat (String) : Nom de la catégorie																	 *
	*																																						 *
	*	Retour : Aucun																														 *
	*																																						 *
	*	Exceptions :																															 *
	*		cant_find_cat : La catégorie est introuvable dans la base de données		 *
	*****************************************************************************/
	function __construct($nomCat) {
		$this->bdd = new Connector();

		$options = array(
			"where" => array(
				array("nom_cat", "=", $nomCat)
			)
		);

		if(!is_null($this->bdd->Select('*', 'categorie', $options))) {
			$this->nomCat = $nomCat;
		} else {
			throw new Exception('cant_find_cat');
		}

		$this->questsets = array();

		$options = array(
			"where" => array(
				array("nom_cat", "=", $this->nomCat)
			),
			"order by" => array("rand()"),
			"limit" => array("2")
		);

		$resp = $this->bdd->Select('*', 'reponses', $options);
		foreach($resp as $questset) {
			array_push($this->questsets, new Questset(array($questset['reponse1'],
																											$questset['reponse2'])));
		}
	}

	/*****************************************************************************
	* Méthode randSelect() [statique]                                            *
	*	Fonction : Sélectionne aléatoirement deux catégories valides (au moins 		 *
	*						 deux thèmes avec chacun au moins trois questions)			 				 *
	*																																						 *
	*	Paramètres : Aucun																												 *
	*																																						 *
	*	Retour :																																	 *
	*		Tableau contenant deux objets Categorie valides, sélectionnés						 *
	*			aléatoirement																													 *
	*****************************************************************************/
	public static function randSelect() {
		$bdd = new Connector();
		$options = array(
			"order by" => array("rand()"),
		);
		$arrayCat = $bdd->Select("*", "categorie", $options);
		$return = array();
		$catIndex = -1;
		$previousIndex = -1;
		// On retire du tableau les catégories invalides
		for($i = 0; $i < sizeof($arrayCat); $i++) {
			$category = new Categorie($arrayCat[$i]['nom_cat']);
			if(sizeof($category->getArray()['themes']) >=2) {
				foreach($category->getArray()['themes'] as $theme) {
					if(sizeof($theme['questions']) < 3) {
						array_splice($arrayCat, $i, 1);
					}
				}
			} else {
				array_splice($arrayCat, $i, 1);
			}
		}
		// On sélectionne deux catégories dans celles restantes
		for($i = 0; $i < 2; $i++) {
			array_push($return, new Categorie($arrayCat[$i]['nom_cat']));
		}

		return $return;
	}

	/*****************************************************************************
	* Méthode getArray()						                                             *
	*	Fonction : Renvoie un tableau représentant la catégorie courante	 				 *
	*																																						 *
	*	Paramètres : Aucun																												 *
	*																																						 *
	*	Retour :																																	 *
	*		Tableau contenant le nom de la catégorie, ainsi qu'un tableau des thèmes *
	*			inclus dans cette dernière (méthode getArray() de la classe Questset)	 *
	*****************************************************************************/
	public function getArray() {
		$questsets = array();
		foreach($this->questsets as $questset) {
			array_push($questsets, $questset->getArray());
		}
		return array(
			"nom_cat" => utf8_encode($this->nomCat),
			"themes" => $questsets
		);
	}
}
