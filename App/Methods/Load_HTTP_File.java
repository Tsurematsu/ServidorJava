package App.Methods;

import java.io.File;
import App.Models.ObjFile;
import App.Config.TypeMIME;
public class Load_HTTP_File extends TypeMIME{
    public static ObjFile assocFile(String URL){
        ObjFile retorno = new ObjFile();
        if (URL.length() > 0) {
            for (ObjFile item : Extensiones) {
                if (TextTools.txt_split(URL, ".").length <= 1) {
                    if (new File(item.baseDirectory + URL + "." + item.extension).exists()) {
                        retorno = item;
                        retorno.Ruta = item.baseDirectory + URL + "." + item.extension; 
                        break;
                    }
                }else if(TextTools.txt_split(URL, ".")[1].equals(item.extension)){
                    if (new File(item.baseDirectory + URL).exists()) {
                        retorno = item; 
                        retorno.Ruta = item.baseDirectory + URL; 
                        break;
                    }
                }
            }
            if (retorno.Ruta == null) {retorno.Ruta = Extensiones[Default_Type].baseDirectory + Default_ErrorPage;}
        }else{retorno.Ruta = Extensiones[Default_Type].baseDirectory + Default_HomePage;}
        return retorno;
    }
}
