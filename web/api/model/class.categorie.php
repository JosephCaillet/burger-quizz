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
			throw new Exception('cant_find_cat');
		}
	}

	private function load() {
		$options = array(
			"where" => array(
				array("nom_cat", "=", $this->nomCat)
			),
			"order by" => array("rand()"),
			"limit" => array("2")
		);

		$resp = $this->bdd->Select('*', 'reponses', $options);
		foreach($resp as $questset) {
			array_push($this->questsets, new Questset(array($questset['reponse1'], $questset['reponse2'])));
		}
	}

	public static function randSelect() {
		$bdd = new Connector();
		$options = array(
			"order by" => array("rand()"),
		);
		$arrayCat = $bdd->Select("*", "categorie", $options);
		$return = array();
		$catIndex = -1;
		$previousIndex = -1;
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
		for($i = 0; $i < 2; $i++) {
			array_push($return, new Categorie($arrayCat[$i]['nom_cat']));
		}

		return $return;
	}

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
