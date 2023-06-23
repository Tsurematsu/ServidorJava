package App.Models;
import java.util.function.Function;
public class ObjFile {
    public String extension;
    public String type;
    public Function<String, Void> ejecute;
    public String baseDirectory;
    public String Ruta;
    public ObjFile() {}
    public ObjFile(String extension, String type, Function<String, Void> ejecute, String baseDirectory) {
        this.extension = extension;
        this.type = type;
        this.ejecute = ejecute;
        this.baseDirectory = baseDirectory;
    }
}
