<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Burger Quizz - Jeu multi</title>
    <link rel="stylesheet" href="css/game.css">
    <link rel="stylesheet" href="css/main.css">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Lato:100,400,300,700' rel='stylesheet' type='text/css'>
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/responsive.js"></script>
    <meta name=viewport content="width=device-width, initial-scale=1"/>
    <script>
      var refresh;
    </script>
  </head>
  <body>
    <header>
    </header>
    <section id="navbar">
      <ul>
        <li><a href="index.htm">Accueil</a></li>
        <li><a href="rules.htm">Règles</a></li>
        <li><a href="palmares.htm">Meilleurs scores</a></li>
        <li class="current"><a href="play.htm">Jouer</a></li>
      </ul>
    </section>
    <section id="responsive-navbar">
      <ul>
        <li class="name"><a href="#">Menu</a></li>
        <li><a href="index.htm">Accueil</a></li>
        <li><a href="rules.htm">Règles</a></li>
        <li><a href="palmares.htm">Meilleurs scores</a></li>
        <li class="current"><a href="play.htm">Jouer</a></li>
      </ul>
    </section>
    <section id="game">
      <p id="conDenied" class="error">
        Erreur :<br />
        Connexion au serveur multijoueur impossible
        <script>
          refresh = window.setTimeout(function() {
            window.location = "";
          }, 5000);
        </script>
      </p>
    </section>
      <?php
        $params = file_get_contents("./params.cfg");
    	   preg_match_all('/node_host\: (.+)/', $params, $host);
         preg_match_all('/node_port\: (.+)/', $params, $port);
         echo '<script src="http://'.$host[1][0].':'.$port[1][0].'/socket.io/socket.io.js"></script>';
      ?>
    <script src="js/multi.js"></script>
    <script>
      init();
    </script>
  </body>
</html>
