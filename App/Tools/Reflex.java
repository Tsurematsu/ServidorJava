package App.Tools;
import java.lang.reflect.Method;

public class Reflex {
    public static retorno CallFunction(String ruta, String function, String dataInput){
        retorno ret = new retorno();
        try {
            Class<?> clase = Class.forName(ruta);
            Method method = clase.getMethod(function, dataInput.getClass());
            ret.resultado = (String) method.invoke(null, dataInput);
            ret.error = false;
        } catch (Exception e) {
            ret.error = true;
            ret.MessageError = e.getMessage();
        }
        return ret;
    }

    public static class retorno{
        public String resultado = null;
        public Boolean error = false;
        public String MessageError = "";
    }
}
