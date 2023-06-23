package App.Tools;
import java.io.UnsupportedEncodingException;

import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSON {
    

    public static Object Parse(Class clase, String JSON){
        Object data = null;
        try {
            data = new Gson().fromJson(JSON, clase);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public static String Strinify(Object objeto){
        String retorno = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            retorno = mapper.writeValueAsString(objeto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }


    public static String JSON_GET(String getParams) throws UnsupportedEncodingException {
        JsonObject json = new JsonObject();

        // Separar los pares clave-valor por el carácter "&"
        String[] paramPairs = getParams.split("&");

        // Recorrer cada par clave-valor y agregarlo al objeto JSON
        for (String pair : paramPairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                // Decodificar los caracteres especiales en el valor
                value = java.net.URLDecoder.decode(value, "UTF-8");

                // Agregar el par clave-valor al objeto JSON
                json.addProperty(key, value);
            }
        }

        // Convertir el objeto JSON a una cadena de texto en formato JSON
        Gson gson = new Gson();
        return gson.toJson(json);
    }

    public static boolean isJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
