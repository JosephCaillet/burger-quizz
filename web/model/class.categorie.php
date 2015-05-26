<?php

class Categorie {
	private $bdd;
	private $nomCat;
	private $questsets;
	
	function __construct($nomCat) {
		$this->bdd = new Connector();
		$this->selectCat($nomCat);
		$this->questsets = array();
		$this->load();
	}

	private function selectCat($nomCat) {
		$options = array(
			"where" => array(
				array("nom_cat", "=", $nomCat)
			)
		);

		if(!is_null($this->bdd->Select('*', 'categorie', $options))) {
			$this->nomCat = $nomCat;
		} else {
			throw new Exception('Catégorie introuvable');
		}
	}

	private function load() {
		$options = array(
			"where" => array(
				array("nom_cat", "=", $this->nomCat)
			)
		);

		$resp = $this->bdd->Select('*', 'reponses', $options);
		foreach($resp as $questset) {
			array_push($this->questsets, new Questset(array($questset['reponse1'], $questset['reponse2'])));
		}
	}

	public static function randSelect() {
		$bdd = new Connector();
		$arrayCat = $bdd->Select("*", "categorie");
		$return = array();
		for($i = 0; $i < 2; $i++) {
			$catIndex = rand(0, sizeof($arrayCat)-1);
			array_push($return, new Categorie($arrayCat[$catIndex]['nom_cat']));
		}
		return $return;
	}

	public function getArray() {
		$questsets = array();
		foreach($this->questsets as $questset) {
			array_push($questsets, $questset->getArray());
		}
		return array(
			"nom_cat" => $this->nomCat,
			"themes" => $questsets
		);
	}
}
