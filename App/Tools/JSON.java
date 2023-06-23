package App.Tools;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.Gson;

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
}
