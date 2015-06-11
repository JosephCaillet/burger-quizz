// IDs
var id_cat = 0, id_theme = 0, id_quest = 0;
// Shortcuts
var json, category, theme;
// Timer
var timing = 5, secRestantes, timer;
var questions = 0, currentQuestion = 1;
var baseWidth;

var score = 0;
var reponseUser = -1, bonneReponse;
var canClick = true;

function play() {
  apiReq();
  console.log(json);
  $("#play").remove();
  if(json.status != 1) {
    var message;
    switch(json.source) {
      case 'PDO':
        message = "Erreur lors de la connexion à la base de donnée : "+json.message;
        break;
      case 'Connector':
        message = "Erreur de requête SQL : "
        switch(json.message) {
          case 'wrong_arg_nmbr_where':
            message += "Mauvais nombre d'arguments dans la clause WHERE.";
            break;
          case 'wrong_arg_nmbr_order_by':
            message += "Mauvais nombre d'arguments dans la clause ORDER BY.";
            break;
          case 'wrong_arg_numbr_limit':
            message += "Mauvais nombre d'arguments dans la clause LIMIT.";
            break;
          case 'unknown_arg':
            message += "Argument inconnu détecté.";
            break;
        }
        break;
      case 'Questset':
        message = "Erreur dans le chargement du jeu de questions : "
        if(json.message == 'expected_questset_array') {
          message += "Un tableau de réponses est attendu."
        }
        break;
      case 'Categorie':
        if(json.message == 'cant_find_cat') {
          message = "Erreur dans le chargement de la catégorie : Impossible de trouver la catégorie.";
        }
        break;
    }

    $("#game").addClass("error");
    $("#game").html(message);
  } else {
    $("#navbar .current").html("<div id=\"score\">Score : "+score+" miam</div>");
    $("#responsive-navbar .name a").css("display", "inline");
    $(".name").append("<div id=\"score-responsive\">"+score+" miam</div>");
    loadCat(id_cat);
  }
}

function apiReq() {
  $.ajax({
    async: false,
    url: "./api/",
    dataType: 'json',
    success: function(data) {
      json = data;
      json.cat1.themes.forEach(function(theme) {
        questions += theme.questions.length;
      });
      json.cat2.themes.forEach(function(theme) {
        questions += theme.questions.length;
      });
    }
  });
}

function loadCat(id) {
  if(id === 0) category = json.cat1;
  if(id === 1) category = json.cat2;
  console.log(category);
  $("#game").html("<div id=\"timer\"></div>");
  $("#game").append("<div id=\"category\">Catégorie : "+category.nom_cat+"</div>");
  $("#game").append("<div id=\"theme\"></div>");
  loadTheme(id_theme);
}

function loadTheme(id) {
  theme = category.themes[id];
  $("#theme").html("<p id=\"question\"></p>");
  $("#theme").append("<ul id=\"answers\"><li id=\"rep1\">"+theme.reponse1+"</li>"
    +"<li id=\"rep2\">"+theme.reponse2+"</li><li id=\"both\">Les deux</li></ul>"
    +"<p id=\"question-count\">Question 0/0</p>");
  quest(id_quest);
}

function quest(id) {
  $("#question").html(theme.questions[id].intitule);
  startTimer();
  bonneReponse = parseInt(theme.questions[id].bonneReponse);
  console.info('Question ' + currentQuestion + '/' + theme.questions.length + ' : '
    +theme.questions[id].intitule);
  $("#question-count").html("Question "+currentQuestion+"/"+questions);
  if(canClick) {
    $("#rep1").off('click');
    $("#rep1").one("click", function() { reponseUser = 1; checkAnswer(); });
    $("#rep2").off('click');
    $("#rep2").one("click", function() { reponseUser = 2; checkAnswer(); });
    $("#both").off('click');
    $("#both").one("click", function() { reponseUser = 0; checkAnswer(); });
  }
}

function checkAnswer() {
  canClick = false;
  $("#rep1").off('click');
  $("#rep2").off('click');
  $("#both").off('click');
  stopTimer();
  console.log(reponseUser == bonneReponse);
  if(reponseUser == bonneReponse) {
    score += secRestantes+1;
  }
  console.log(score);
  if(score > 1) {
    $("#score").html("Score : "+score+" miams");
    $("#score-responsive").html(score+" miams");
  } else {
    $("#score").html("Score : "+score+" miam");
    $("#score-responsive").html(score+" miam");
  }
  switch(bonneReponse) {
    case 0:   $("#rep1").addClass("wrong-answer");
              $("#rep2").addClass("wrong-answer");
              $("#both").addClass("good-answer");
              break;
    case 1:   $("#rep1").addClass("good-answer");
              $("#rep2").addClass("wrong-answer");
              $("#both").addClass("wrong-answer");
              break;
    case 2:   $("#rep1").addClass("wrong-answer");
              $("#rep2").addClass("good-answer");
              $("#both").addClass("wrong-answer");
              break;
  }
  window.setTimeout(nextQuestion, 2000);
}

function nextQuestion() {
  $("#rep1").removeClass();
  $("#rep2").removeClass();
  $("#both").removeClass();
  canClick = true;
  currentQuestion++;
  // Dernière question du thème en cours
  if((id_quest+1) == theme.questions.length)  {
    // Dernier thème de la catégorie en cours
    if((id_theme+1) == category.themes.length) {
      // Dernière catégorie
      if((id_cat+1) == 2) {
        endGame();
      } else {
        id_quest = 0;
        id_theme = 0;
        id_cat++;
        loadCat(id_cat);
      }
    } else {
      id_quest = 0;
      id_theme++;
      loadTheme(id_theme);
    }
  } else {
    id_quest++;
    quest(id_quest);
  }
}

function startTimer() {
  baseWidth = $("#timer").width();
  $("#timer").removeClass();
  var elm = document.getElementById("timer"),
      newone = elm.cloneNode(true);
  elm.parentNode.replaceChild(newone, elm);
  $("#timer").addClass("timer");
  timer = window.setTimeout(checkAnswer, timing*1000);
}

function stopTimer() {
  window.clearTimeout(timer);
  $("#timer").addClass("pause");
  secRestantes = Math.round($("#timer").width()/baseWidth*timing);
  console.log(secRestantes);
}

function endGame() {
  $("#game").html("<h2 id=\"score\">Vous avez marqué "+score+" miams</h2>"
  +"<p id=\"registerScore\">Enregistrez votre score : <input type=\"text\" id=\"login\" placeholder=\"Nom ou pseudonyme\" />"
  +"<input type=\"submit\" id=\"sendScore\" value=\"Valider\" /></p>");
  $("#sendScore").on('click', scoreConfirm);
  $("#login").on('keypress', function(event) {
    if(event.which == 13) {
      scoreConfirm();
    }
  });
}

function scoreConfirm() {
  $("#registerScore").fadeOut();
  if($("#login").val() != "") {
    addScore($("#login").val(), score);
    var message = json.message;
    if(message == "score_add_success") {
      window.setTimeout(function() {
        $("#registerScore").removeClass();
        $("#registerScore").addClass("success");
        $("#registerScore").html("Votre score a bien été enregistré.<br />"+
        "<a href=\"palmares.htm\">Voir les meilleurs scores</a>");
      }, 400);
    } else {
      if(message === "higher_score_present") {
        window.setTimeout(function() {
          $("#registerScore").removeClass();
          $("#registerScore").addClass("error");
          $("#registerScore").html("Un score supérieur ou égal existe déjà avec ce pseudonyme.<br />"
          +"Essayez avec un autre pseudonyme :<br />"
          +"<input type=\"text\" id=\"login\" placeholder=\"Nom ou pseudonyme\" />"
          +"<input type=\"submit\" id=\"sendScore\" value=\"Valider\" /><br />"
          +"<a href=\"palmares.htm\">Voir les meilleurs scores</a>");
          $("#sendScore").on('click', scoreConfirm);
          $("#login").on('keypress', function(event) {
            if(event.which == 13) {
              scoreConfirm();
            }
          });
        }, 400);
      } else {
        window.setTimeout(function() {
          $("#registerScore").removeClass();
          $("#registerScore").addClass("error");
          $("#registerScore").html("Une erreur est survenue ("+status.message+")<br /> Réessayer :"
          +"<input type=\"text\" id=\"login\" placeholder=\"Nom ou pseudonyme\" />"
          +"<input type=\"submit\" id=\"sendScore\" value=\"Valider\" /><br />"
          +"<a href=\"palmares.htm\">Voir les meilleurs scores</a>");
        }, 400);
      }
    }
  } else {
    window.setTimeout(function() {
      $("#registerScore").removeClass();
      $("#registerScore").addClass("error");
      $("#registerScore").html("Merci de renseigner un pseudonyme : <br />"
      +"<input type=\"text\" id=\"login\" placeholder=\"Nom ou pseudonyme\" />"
      +"<input type=\"submit\" id=\"sendScore\" value=\"Valider\" /><br />"
      +"<a href=\"palmares.htm\">Voir les meilleurs scores</a>");
      $("#sendScore").on('click', scoreConfirm);
      $("#login").on('keypress', function(event) {
        if(event.which == 13) {
          scoreConfirm();
        }
      });
    }, 400);
  }
  $("#registerScore").fadeIn();
}

function addScore(userLogin, userScore) {
  $.ajax({
    async: false,
    url: "./api/?page=palmares",
    type: "POST",
    dataType: 'json',
    data: {login: userLogin, score: userScore},
    success: function(data) {
      json = data;
    }
  });
}

function displayScores() {
  $.get("./api/?page=palmares", function(data) {
    var list = "<ol>";
    for(var i = 0; i < 10; i++) {
      list += "<li>"+data[i].login+" - "+data[i].score+"</li>";
    }
    list += "</ol>";
    $("#palmares").html(list);
  });
}
