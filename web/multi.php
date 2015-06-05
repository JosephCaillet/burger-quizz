<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Burger</title>
        <style type="text/css">
          .good-answer {
            color:green;
          }
          .wrong-answer {
            color:red;
          }
        </style>
    </head>
    <body>
        <div id="game">
          Connexion au serveur...
        </div>
        <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
        <?php
          $params = file_get_contents("./params.cfg");
    		  preg_match_all('/node_host\: (.+)/', $params, $matches);
          echo '<script src="http://'.$matches[1][0].':8000/socket.io/socket.io.js"></script>';
         ?>
         <script src="js/multi.js"></script>
         <script>
          init();
          </script>
    </body>
</html>
