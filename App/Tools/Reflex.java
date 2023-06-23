package App.Tools;
import java.lang.reflect.Method;

public class Reflex {
    public static String CallFunction(String ruta, String dataInput){
        String resultado = "";
        try {
            Class<?> clase = Class.forName(ruta.split("/")[0]);
            Method method = clase.getMethod(ruta.split("/")[1], dataInput.getClass());
            resultado = (String) method.invoke(null, dataInput);
        } catch (Exception e) {}
        return resultado;
    }
}
