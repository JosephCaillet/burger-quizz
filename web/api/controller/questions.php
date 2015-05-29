<?php

$categoriesObj = Categorie::randSelect();
$categories = array();
foreach($categoriesObj as $categorie) {
	array_push($categories, $categorie->getArray());
}
