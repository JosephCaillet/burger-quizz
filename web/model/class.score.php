<?php

class Score {
  private $login;
  private $score;
  private $bdd;

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
      }
    }
  }
}
