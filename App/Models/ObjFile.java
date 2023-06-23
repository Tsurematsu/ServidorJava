package App.Models;

import java.util.function.Function;

public class ObjFile {
    public String param;
    public String typeFile;
    public String extension;
    public String root;
    public Function<Route, Boolean> action;
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
