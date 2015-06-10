<?php

/*******************************************************************************
* Classe Score									     																					 *
*	Auteur : Brendan Abolivier																									 *
* Fonction : Gérer l'affichage et l'insertion de scores 											 *
*																																							 *
*	Attribut :																																	 *
*		$bdd : Connector																												   *
*		$login : String																												 	   *
*		$score : Int                 																							 *
*																																							 *
* Méthodes :																																	 *
*		__construct()																															 *
*		getLogin()																										             *
*		getScore()																										             *
*		getScores() [statique]																										 *
*		add()	[statique]																													 *
*******************************************************************************/
class Score {
  private $bdd;
  private $login;
  private $score;

  /*****************************************************************************
	* Méthode __construct()                                                      *
	*	Fonction : Charge un score d'après le login de son auteur            			 *
	*																																						 *
	*	Paramètres :																															 *
	*		$login (String) : Login de l'auteur du score														 *
	*																																						 *
	*	Retour : Aucun																														 *
	*****************************************************************************/
  function __construct($login) {
    $this->bdd = new Connector();

    $options = array(
      "where" => array(
        array("login", "=", $login)
      )
    );

    $data = $this->bdd->Select("*", "scores", $options);
    $this->login = $data[0]['login'];
    $this->score = $data[0]['score'];
  }


  /*****************************************************************************
  * Méthode getLogin()                                                         *
  *	Fonction : Renvoie le login associé au score courant                			 *
  *																																						 *
  *	Paramètres : Aucun																												 *
  *																																						 *
  *	Retour :      																														 *
  *   Chaîne de caractères contenant le login associé au score courant         *
  *****************************************************************************/
  function getLogin() {
    return $this->login;
  }

  /*****************************************************************************
  * Méthode getScore()                                                         *
  *	Fonction : Renvoie le score de l'objet courant                      			 *
  *																																						 *
  *	Paramètres : Aucun																												 *
  *																																						 *
  *	Retour :      																														 *
  *   Entier contenant le score de l'objet courant                             *
  *****************************************************************************/
  function getScore() {
    return $this->score;
  }

  /*****************************************************************************
  * Méthode getScores() [statique]                                             *
  *	Fonction : Sélectionne un nombre donné dans les meilleurs ou les pires     *
  *            scores                                                   			 *
  *																																						 *
  *	Paramètres :      																												 *
  *   $nRows (Int) : Nombre de lignes à sélectionner (nombre de scores à       *
  *                  retourner)                                                *
  *   $direction (String) : Si vaut "desc", sélectionne par ordre décroissant  *
  *                         (les n meilleurs scores) ; si vaut "asc",          *
  *                         sélectionne par ordre croissant (les n pires       *
  *                         scores)                                            *
  *																																						 *
  *	Retour :      																														 *
  *   Tableau de n objets Score                                                *
  *****************************************************************************/
  public static function getScores($nRows, $direction = "desc") {
    $bdd = new Connector();

    $options = array(
      "order by" => array("score", $direction),
      "limit" => array($nRows)
    );

    $array = $bdd->Select("*", "scores", $options);
    $scores = array();
    foreach($array as $score) {
      array_push($scores, new Score($score['login']));
    }

    return $scores;
  }

  /*****************************************************************************
  * Méthode add() [statique]                                                   *
  *	Fonction : Ajoute un score dans la base de données                   			 *
  *																																						 *
  *	Paramètres :      																												 *
  *   $login (String) : Login de l'auteur du score à ajouter                   *
  *   $score (Int) : Score à ajouter                                           *
  * 																																					 *
  *	Retour : Aucun                                                             *
  * 																																					 *
  * Exceptions :                                                               *
  *   higher_score_present : Un score supérieur existe déjà pour ce login dans *
  *                          la base de données                                *
  *****************************************************************************/
  public static function add($login, $score) {
    $bdd = new Connector();

    $options = array(
      "where" => array(
        array("login", "=", $login)
      )
    );

    if(!$scores = $bdd->Select("*", "scores", $options)) {
      $values = array(
        "login" => $login,
        "score" => $score
      );

      $bdd->Insert("scores", $values);
    } else {
      if($score > $scores[0]['score']) {
        $update = array(
          "where" => array(
            array("login", "=", $login)
          ),
          "set" => array("score" => $score)
        );
        $bdd->Update("scores", $update);
      } else {
        throw new Exception('higher_score_present');
      }
    }
  }
}
