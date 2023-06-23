<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <?php 
        // $parámetros = json_decode($argv[1]);
        // print_r(array_keys($parámetros["0"]));
        echo "\n    hola a todos, este es un archivo de php\n";
        for ($i=0; $i < 10; $i++) { 
            echo "        <p>numero" . $i . "</p>\n";
        }
    ?>
</body>
</html>