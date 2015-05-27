<?php

require_once('model/classes.php');

//header("Content-Type:application/json");

if(!empty($_GET['page'])) {
  if($_GET['page'] == "palmares") {
    include("view/palmares.php");
    echo json_encode($scArray);
  }
} else {
  include("view/questions.php");
	echo json_encode($categories);
}
