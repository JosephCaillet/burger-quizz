// IDs
var id_cat = 0, id_theme = 0, id_quest = 0;
// Shortcuts
var json, category, theme;
// Timer
var timing = 5, secRestantes, timer;
var baseWidth;

var score = 0;
var reponseUser = -1, bonneReponse;
var canClick = true;

function apiReq() {
  $.ajax({
    async: false,
    url: "./api/",
    dataType: 'json',
    success: function(data) {
      json = data;
    }
  });
}

function loadCat(id) {
  if(id === 0) category = json.cat1;
  if(id === 1) category = json.cat2;
  console.log(category);
  $("#game").html("<p id=\"category\">Catégorie : "+category.nom_cat+"</p>");
  $("#game").append("<div id=\"theme\"></div>");
  $("#game").append("<div id=\"timer\" style=\"width:100%;height:20px;background:green\"></div>");
  $("#game").append("<div id=\"score\">Score : "+score+"</div>");
  loadTheme(id_theme);
}

function loadTheme(id) {
  theme = category.themes[id];
  $("#theme").html("<p id=\"question\"></p>");
  $("#theme").append("<ul id=\"answers\"><li id=\"rep1\">"+theme.reponse1+"</li>"
    +"<li id=\"rep2\">"+theme.reponse2+"</li><li id=\"both\">Les deux</li></ul>");
  quest(id_quest);
}

function quest(id) {
  $("#question").html(theme.questions[id].intitule);
  startTimer();
  bonneReponse = parseInt(theme.questions[id].bonneReponse);
  console.info('Question ' + (id_quest + 1) + '/' + theme.questions.length + ' : '
    +theme.questions[id].intitule);
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
  if(reponseUser == bonneReponse) {
    score += secRestantes+1;
  }
  $("#score").html("Score : "+score);
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

function play() {
  $("#play").remove();
  $("#multi").remove();
  apiReq();
  loadCat(id_cat);
}

function startTimer() {
  $("#timer").css("width", "100%");
  baseWidth = $("#timer").width();
  $("#timer").animate({'width' : '0%'}, timing*1000);
  timer = window.setTimeout(checkAnswer, timing*1000);
}

function stopTimer() {
  window.clearTimeout(timer);
  $("#timer").stop();
  secRestantes = Math.round($("#timer").width()/baseWidth*timing);
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
  console.log("qsfd");
  addScore($("#login").val(), score);
  var message = json.message;
  $("#registerScore").fadeOut();
  console.log(message);
  if(message == "score_add_success") {
    window.setTimeout(function() {
      $("#registerScore").addClass("success");
      $("#registerScore").html("Votre score a bien été enregistré<br />"+
      "<a href=\"palmares.htm\">Voir les meilleurs scores</a>");
    }, 400);
  } else {
    if(message === "higher_score_present") {
      window.setTimeout(function() {
        $("#registerScore").addClass("error");
        $("#registerScore").html("Un score supérieur ou égal existe déjà avec ce pseudonyme<br />"
        +"Essayez avec un autre pseudonyme : <input type=\"text\" id=\"login\" placeholder=\"Nom ou pseudonyme\" />"
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
        $("#registerScore").addClass("error");
        $("#registerScore").html("Une erreur est survenue ("+status.message+")<br />"+
        "<a href=\"palmares.htm\">Voir les meilleurs scores</a>");
      }, 400);
    }
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
