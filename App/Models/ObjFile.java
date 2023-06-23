package App.Models;

import java.util.function.Function;

public class ObjFile {
    public String param; // El parámetro del archivo (quizás es una especie de configuración secreta)
    public String typeFile; // El tipo de archivo (tal vez sea "documento", "imagen" u otro)
    public String extension; // La extensión del archivo (como "txt", "jpg", "html", etc.)
    public String root; // La ruta raíz del archivo (donde se encuentra ubicado)

    public Function<Route, Boolean> action; // La acción a realizar en función de la ruta del archivo

    public ObjFile(String typeFile, String extension, Function<Route, Boolean> action) {
        this.typeFile = typeFile;
        this.extension = extension;
        this.action = action;
    }

    public ObjFile(String typeFile, String extension, Function<Route, Boolean> action, String root) {
        this.typeFile = typeFile;
        this.extension = extension;
        this.action = action;
        this.root = root;
    }

    public ObjFile() {}
}
