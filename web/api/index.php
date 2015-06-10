<?php

header("Content-Type:application/json");

include("controller/routes.php");

echo json_encode($api);
