package App.Config;

import java.util.ArrayList;
import java.util.function.Function;

import App.Models.ObjFile;
import App.Models.Route;

public class TypeMIME extends AppFonfig{
    public static ArrayList<ObjFile> Extensiones = new ArrayList<>();


    private static Route ReAsync(Route ruta){return ReAsync(ruta, Extensiones.get(ruta.Item).extension);}
    private static Route ReAsync(Route ruta, String extension){
        ruta.type = Extensiones.get(ruta.Item).typeFile;
        ruta.extension= extension;
        ruta.root = Extensiones.get(ruta.Item).root;
        return ruta;
    }

    public static void LoadExtencion(){
        
        Extensiones.add(new ObjFile("text", "html", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_TEXT.apply(ReAsync(ruta));
            }
        }, "FrontEnd/"));

        Extensiones.add(new ObjFile("text", "php", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_PHP.apply(ReAsync(ruta, "html"));
            }
        }, "FrontEnd/"));

        Extensiones.add(new ObjFile("text", "js", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_TEXT.apply(ReAsync(ruta));
            }
        }, "FrontEnd/"));

        Extensiones.add(new ObjFile("text", "css", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_TEXT.apply(ReAsync(ruta));
            }
        }, "FrontEnd/"));

        Extensiones.add(new ObjFile("image", "jpg", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMAGE.apply(ReAsync(ruta, "jpeg"));
            }
        }, "Images/"));

        Extensiones.add(new ObjFile("image", "png", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMG.apply(ReAsync(ruta));
            }
        }, "Images/"));

        Extensiones.add(new ObjFile("image", "gif", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMG.apply(ReAsync(ruta));
            }
        }, "Images/"));

        Extensiones.add(new ObjFile("image", "ico", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMAGE.apply(ReAsync(ruta, "x-icon"));
            }
        }, "Icons/"));

        Extensiones.add(new ObjFile("text", "java", new Function<Route,Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_JAVA.apply(ReAsync(ruta, "html"));
            }
        }, "BackEnd/"));

    }
}
