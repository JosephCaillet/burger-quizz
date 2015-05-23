<?php

require_once('classes.php');

header("Content-Type:application/json");

echo json_encode(array("Message" => "Hello world;"));

