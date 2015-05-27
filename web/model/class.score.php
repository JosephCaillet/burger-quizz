<?php

class Score {
  private $login;
  private $score;
  private $bdd;

  function __construct($login) {
    $this->bdd = new Connector();

    $options = array(
      "where" => array(
        array("login", "=", $login);
      )
    );

    $data = $bdd->Select("*", "scores", $options);
    $this->login = $data['login'];
    $this->score = $data['score'];
  }

  function getLogin() {
    return $this->login;
  }

  function getScore() {
    return $this->score;
  }

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

  public static function add($login, $score) {
    $bdd = new Connector();

    $values = array(
      "login" => $login,
      "score" => $score
    );

    $bdd->Insert("score", $values);
  }
}
