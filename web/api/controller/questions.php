<?php

$categoriesObj = Categorie::randSelect();
$categories = array();
$i = 0;
foreach($categoriesObj as $categorie) {
	$i++;
	$categories["cat$i"] = $categorie->getArray();
}
