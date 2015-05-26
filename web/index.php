<?php

require_once('model/classes.php');

header("Content-Type:application/json");

echo json_encode(array("Message" => "Hello world;"));

