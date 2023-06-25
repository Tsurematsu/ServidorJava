package App.Config;

import java.util.ArrayList;
import java.util.function.Function;

import App.Models.ObjFile;
import App.Models.Route;

public class TypeMIME extends AppConfig {
    // Lista de extensiones de archivo
    public static ArrayList<ObjFile> Extensiones = new ArrayList<>();

    // Método auxiliar para actualizar la ruta con la extensión correspondiente
    private static Route ReAsync(Route ruta) {
        return ReAsync(ruta, Extensiones.get(ruta.Item).extension);
    }

    private static Route ReAsync(Route ruta, String extension) {
        // Actualizar el tipo de archivo, extensión y ruta de la URL
        ruta.type = Extensiones.get(ruta.Item).typeFile;
        ruta.extension = extension;
        ruta.root = Extensiones.get(ruta.Item).root;
        ruta.route = ruta.route.replace(".bjs", ".js"); 
        return ruta;
    }

    // Método para cargar las extensiones de archivo
    public static void LoadExtencion() {
        // Agregar extensiones de archivo a la lista Extensiones

        // Archivos de texto HTML en la carpeta FrontEnd
        Extensiones.add(new ObjFile("text", "html", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_TEXT.apply(ReAsync(ruta));
            }
        }, "FrontEnd/"));

        // Archivos de texto PHP en la carpeta FrontEnd
        Extensiones.add(new ObjFile("text", "php", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_PHP.apply(ReAsync(ruta, "html"));
            }
        }, "FrontEnd/"));

        // Archivos de texto JS en la carpeta FrontEnd
        Extensiones.add(new ObjFile("text", "js", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_TEXT.apply(ReAsync(ruta));
            }
        }, "FrontEnd/"));

        // Archivos de texto JS en la carpeta FrontEnd
        Extensiones.add(new ObjFile("text", "bjs", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_backend_JS.apply(ReAsync(ruta));
            }
        }, "FrontEnd/"));

        // Archivos de texto CSS en la carpeta FrontEnd
        Extensiones.add(new ObjFile("text", "css", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_TEXT.apply(ReAsync(ruta));
            }
        }, "FrontEnd/"));

        // Imágenes JPG en la carpeta Images
        Extensiones.add(new ObjFile("image", "jpg", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMAGE.apply(ReAsync(ruta, "jpeg"));
            }
        }, "Images/"));

        // Imágenes PNG en la carpeta Images
        Extensiones.add(new ObjFile("image", "png", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMG.apply(ReAsync(ruta));
            }
        }, "Images/"));

        // Imágenes GIF en la carpeta Images
        Extensiones.add(new ObjFile("image", "gif", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMG.apply(ReAsync(ruta));
            }
        }, "Images/"));

        // Iconos ICO en la carpeta Icons
        Extensiones.add(new ObjFile("image", "ico", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_IMAGE.apply(ReAsync(ruta, "x-icon"));
            }
        }, "Icons/"));

        // Archivos de texto Java en la carpeta BackEnd
        Extensiones.add(new ObjFile("text", "java", new Function<Route, Boolean>() {
            public Boolean apply(Route ruta) {
                return HTTP_JAVA.apply(ReAsync(ruta, "html"));
            }
        }, "BackEnd/"));

    }
}
