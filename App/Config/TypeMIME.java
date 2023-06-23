package App.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import App.Models.ObjFile;
import App.Tools.computer;


public class TypeMIME extends AppFonfig{
    public static String URL;
    public static Socket client;
    public static OutputStream outputStream;
    public static PrintWriter Output;
    public static BufferedReader Input;
    public static String parametros;
    public static String requestBody;
    public static String Method; //POST GET
    
    public static ObjFile[] Extensiones = {
                    // Tratamiento de archivo HTML
                    new ObjFile("html", "Content-Type:text/html", new Function<String, Void>() {
                        @Override
                        public Void apply(String ruta) {
                            Output.println(computer.Read_File(ruta));
                            return null;
                        }
                        
                    }, "FrontEnd/"),

                    // Tratamiento de archivo PHP
                    new ObjFile("php", "Content-Type:text/html", new Function<String, Void>() {
                        @Override
                        public Void apply(String ruta) {
                            Output.println(computer.Command_File("php\\php.exe", ruta + " " + parametros.replace("&", " ")));
                            return null;
                        }
                        
                    }, "FrontEnd/"),

                    // Tratamiento de archivo JS
                    new ObjFile("js", "Content-Type:text/js", new Function<String, Void>() {
                        @Override
                        public Void apply(String ruta) {
                            Output.println(computer.Read_File(ruta));
                            return null;
                        }
                        
                    }, "FrontEnd/"),

                    // Tratamiento de archivo CSS
                    new ObjFile("css", "Content-Type:text/css", new Function<String, Void>() {
                        @Override
                        public Void apply(String ruta) {
                            Output.println(computer.Read_File(ruta));
                            return null;
                        }
                        
                    }, "FrontEnd/"),

                    // Tratamiento de archivo JPG
                    new ObjFile("jpg", "Content-Type: image/jpeg", ruta -> {
                        try {
                            File file = new File(ruta);
                            FileInputStream fis = new FileInputStream(file);
                            byte[] data = new byte[(int) file.length()];
                            fis.read(data);

                            outputStream.write(data);
                            outputStream.flush();
                            fis.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }, "Images/"),

                    // Tratamiento de archivo PNG
                    new ObjFile("png", "Content-Type: image/png", new Function<String, Void>() {
                        @Override
                        public Void apply(String ruta) {
                            try {
                                File file = new File(ruta);
                                FileInputStream fis = new FileInputStream(file);
                                byte[] data = new byte[(int) file.length()];
                                fis.read(data);
                                Output.println(new String(data, StandardCharsets.ISO_8859_1));
                                fis.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }, "Images/"),

                    // Tratamiento de archivo GIF
                    new ObjFile("gif", "Content-Type: image/gif", new Function<String, Void>() {
                        @Override
                        public Void apply(String ruta) {
                            try {
                                File file = new File(ruta);
                                FileInputStream fis = new FileInputStream(file);
                                byte[] data = new byte[(int) file.length()];
                                fis.read(data);
                                Output.println(new String(data, StandardCharsets.ISO_8859_1));
                                fis.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }, "Images/"),

                    // Tratamiento de archivo ICO
                    new ObjFile("ico", "Content-Type: image/x-icon", ruta -> {
                        try {
                            File file = new File(ruta);
                            FileInputStream fis = new FileInputStream(file);
                            byte[] data = new byte[(int) file.length()];
                            fis.read(data);

                            outputStream.write(data);
                            outputStream.flush();
                            fis.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }, "Icons/"),


                };
}
