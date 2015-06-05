<?php
header("Content-Type:application/json");

$array = array(
  "numéro" => 2,
  "lettre" => "a",
  "string" => "qqfssdfq"
);

echo json_encode($array);
