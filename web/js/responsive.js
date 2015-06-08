var open = false;

$(document).on("ready", function() {

  var li = document.getElementById("responsive-navbar").getElementsByTagName("li");
  for(var i = 1; i < li.length; i++) {
    li[i].style.display = "none";
  }

  $('.name').on("click", function() {

    for(var i = 1; i < li.length; i++) {
      if(open) {
        li[i].style.display = "none";
      } else {
        li[i].style.display = "block";
      }
    }

    open = !open;
  });
});
