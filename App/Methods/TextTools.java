package App.Methods;

public class TextTools {
    public static String Clean_TEXT_HTML(String text){
        text = text.split("HTTP/1.1")[0];
        text = text.split("/")[1];
        text = text.replace(" ", "");
        text = text.replace("?", "%not%");
        text = text.split("%not%")[0];
        return text;
    }

    public static String Clean_PARAMETER_HTML(String text){
        try {
            text = text.replace("?", "%res%");
            text = text.split("%res%")[1];
            text = text.split("HTTP/1.1")[0];
            text = text.replace(" ", "");
        } catch (Exception e) {
            text = "";
        }
        return text;
    }

    public static String Clean_METHOD_HTML (String text){
        try {
            text = text.split("/")[0];
            text = text.replace(" ", "");
        } catch (Exception e) {
            text = "";
        }
        return text;
    }

    public static String Get_Remove_JSON_Element(String Dato_entrada, String propiedad){
        String propiedadDetectar = "\"" + propiedad + "\":";
        String retorno= "";
        String Param = null;
        for (String propiedades : Dato_entrada.split(",")) {
            if (propiedades.contains(propiedadDetectar)) {
                Param = propiedades;
                break;
            }
        }
        Param = Param.replace("{", "");   
        Param = Param.replace("}", "");

        String dataInput = Dato_entrada.replace(Param, "");                
        dataInput = dataInput.replace("{,", "{");                
        dataInput = dataInput.replace(",}", "}");
        dataInput = dataInput.replace(",,", ",");

        String ruta = Param.split(":")[1];
        ruta = ruta.replace("\"", "");
        ruta = ruta.replace("\"", "");
        // ruta = Root + ruta;
        
        return retorno;
    }
    
    public static String[] txt_split(String str, String regex) {
        return str.replace(regex, ",").split(",");
    }
}
