<?php 
    function LoadParam_to_this() {
        global $GET;
        global $POST;
        global $_GET;
        global $_POST;
        function Formateo_JSON($valor) : string {
            $valor = str_replace("{", '{"', $valor);
            $valor = str_replace("}", '"}', $valor);
            $valor = str_replace(':', '":"', $valor);
            $valor = str_replace(',', '","', $valor);
            return $valor;
        }
        $parametros = $_SERVER['argv'];
        array_shift($parametros);
        $_GET = json_decode(Formateo_JSON($parametros[0]), true);
        $_POST = json_decode(Formateo_JSON($parametros[1]), true);
        $GET = (object) $_GET;
        $POST = (object)$_POST;
    }
    LoadParam_to_this();
?>