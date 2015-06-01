// IDs
var id_cat = 0, id_theme = 0, id_quest = 0;
// Shortcuts
var json, category, theme;
// Timer
var timing = 5, secRestantes;

var score = 0;
var reponseUser = -1, bonneReponse;

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
  loadTheme(id_theme);
}

function loadTheme(id) {
  theme = category.themes[id];
  $("#theme").html("<p id=\"question\"></p>");
  $("#theme").append("<ul id=\"answers\"><li id=\"rep1\">"+theme.reponse1+"</li>"
    +"<li id=\"rep2\">"+theme.reponse2+"</li><li id=\"both\">Les deux</li></ul>");
  $("#theme").append("<div id=\"timer\" style=\"width:100%\"></p>");
  quest(id_quest);
}

function quest(id) {
  $("#question").html(theme.questions[id].intitule);
  startTimer();
  bonneReponse = parseInt(theme.questions[id].bonneReponse);
  console.info('Question ' + (id_quest + 1) + '/' + theme.questions.length + ' : '
    +theme.questions[id].intitule);
  $("#rep1").off('click');
  $("#rep1").on("click", function() { reponseUser = 1; checkAnswer(); });
  $("#rep2").off('click');
  $("#rep2").on("click", function() { reponseUser = 2; checkAnswer(); });
  $("#both").off('click');
  $("#both").on("click", function() { reponseUser = 0; checkAnswer(); });
}

function checkAnswer() {
  stopTimer();
  if(reponseUser == bonneReponse) {
    score += secRestantes+1;
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
  apiReq();
  loadCat(id_cat);
}

function startTimer() {
  $("#timer").animate('{width : 0%}', timing*1000);
  window.setTimeout(checkAnswer, timing*1000);
}

function stopTimer() {
  secRestantes = Math.round($("#timer").width/100*timing);
  console.log(secRestantes);
}

function endGame() {
  $("#game").html("End of line. Score : "+score);
}
