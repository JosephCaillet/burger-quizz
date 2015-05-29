var json, cat, theme, i = 0;

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
  if(i === 0) cat = json.cat1;
  if(i === 1) cat = json.cat2;
  $("#game").html("<p id=\"category\">Catégorie : "+category.nom_cat+"</p>");
  loadTheme(1);
}

function loadTheme(id) {
  theme = category.theme[id];
  $("#game").append("<ul id=\"answers\"><li id=\"rep1\">"+theme.reponse1+"</li><li id=\"rep2\">"+theme.reponse2+"</li><li id=\"both\">Les deux</li></ul>");
  $("#game").append("<p id=\"question\"></p>")
  quest();
}

function quest() {
  while(i < theme.questions.length) {
    $("#question").html(theme.questions[i]);
    $("#answers").click(function() {
      
    });
  }
}

function play() {
  $("#play").remove();
  apiReq();

}
