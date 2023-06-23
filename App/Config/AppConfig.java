package App.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import App.Methods.TextTools;
import App.Models.Route;
import App.Tools.computer;

public class AppConfig{
    // Configuraciones generales
    public static Integer Puerto = 89;
    public static String Default_HomePage = "index.html";
    public static String Default_ErrorPage = "error.html";
    public static Integer Default_Type = 0;

    // Script JS para llamadas asíncronas
    public static String ScriptJS = "<script> async function call(classe, methodo, parametro) {let retorno; await fetch(classe + \"?\" + methodo, { method: \"POST\", headers: { \"Content-Type\": \"application/json\" }, body: JSON.stringify(parametro), }).then(response => response.text()).then(data => { retorno = data; }).catch(err => console.error(err)); return retorno;}; </script>\r\n        ";

    // Método privado para obtener la ruta de ejecución
    private static String Exec_Route(Route Data_Route){
        return Data_Route.root + Data_Route.route;
    };

    // Método privado para imprimir la cabecera HTTP
    private static Void PrintHeader(Route Data_Route){
        PrintWriter Output = Data_Route.dataPacket.Output;
        Output.println("HTTP/1.1 200 OK");
        Output.println("Content-Type:" + Data_Route.type + "/" + Data_Route.extension);
        Output.println("");
        return null;
    };

    // Función para procesar archivos Java
    public static Function<Route, Boolean> HTTP_JAVA = new Function<Route, Boolean>() {
        public Boolean apply(Route Data_Route) {
            if (Data_Route.POST.length() == 0) {
                return false;
            }

            PrintWriter Output = Data_Route.dataPacket.Output;
            String clase = Data_Route.root.replace("/", "") + "." + TextTools.txt_split(Data_Route.URL, ".")[0];
            String metodo = Data_Route.GET;
            String param = Data_Route.POST;
            
            // Lógica para llamar a la función Java y obtener el resultado
            App.Tools.Reflex.retorno retorno = App.Tools.Reflex.CallFunction(clase, metodo, param);
            if (retorno.error == false) {
                PrintHeader(Data_Route);
                Output.println(retorno.resultado);
            }
            return (!retorno.error);
        }
    };

    // Función para procesar archivos de texto
    public static Function<Route, Boolean> HTTP_TEXT = new Function<Route, Boolean>() {
        public Boolean apply(Route Data_Route) {
            PrintWriter Output = Data_Route.dataPacket.Output;
            
            PrintHeader(Data_Route);
            String ReadFile = (String)computer.Read_File(Exec_Route(Data_Route));
            
            // Lógica adicional para archivos HTML y JS
            if (Data_Route.extension.equals("html")) {
                ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, ScriptJS);
            } else if (Data_Route.extension.equals("js")) {
                ReadFile = App.Methods.DocumentTools.ChangeCall_JS(ReadFile);
            }
            Output.println(ReadFile);
            return true;
        }
    };

    // Función para procesar archivos PHP
    public static Function<Route, Boolean> HTTP_PHP = new Function<Route, Boolean>() {
        public Boolean apply(Route Data_Route) {
            PrintHeader(Data_Route);
            PrintWriter Output = Data_Route.dataPacket.Output;
            String ReadFile = (String)computer.Command_File(
                                "php\\php.exe", 
                                Exec_Route(Data_Route) + " " + 
                                Data_Route.GET.replace("&", " ") + " _ " + 
                                Data_Route.POST
                            );
            
            // Lógica adicional para archivos PHP y JS
            if (Data_Route.extension.equals("php")) {
                ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, ScriptJS);
            } else if (Data_Route.extension.equals("js")) {
                ReadFile = App.Methods.DocumentTools.ChangeCall_JS(ReadFile);
            }
            Output.println(ReadFile);
            return true;
        }
    };

    // Función para procesar imágenes
    public static Function<Route, Boolean> HTTP_IMAGE = new Function<Route, Boolean>() {
        public Boolean apply(Route Data_Route) {
            OutputStream Output_Stream = Data_Route.dataPacket.Output_Stream;
            PrintHeader(Data_Route);
            try {
                File file = new File(Exec_Route(Data_Route));
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                Output_Stream.write(data);
                Output_Stream.flush();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    };

    // Función para procesar imágenes en formato texto
    public static Function<Route, Boolean> HTTP_IMG = new Function<Route, Boolean>() {
        public Boolean apply(Route Data_Route) {
            PrintWriter Output = Data_Route.dataPacket.Output;
            PrintHeader(Data_Route);
            try {
                File file = new File(Exec_Route(Data_Route));
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                Output.println(new String(data, StandardCharsets.ISO_8859_1));
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    };
}
