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

public class AppFonfig{
    public static Integer Puerto = 89;
    public static String Default_HomePage = "index.html";
    public static String Default_ErrorPage = "error.html";
    public static Integer Default_Type = 0;

    public static String ScriptJS = "<script> async function call(classe, methodo, parametro) {let retorno; await fetch(classe + \"?\" + methodo, { method: \"POST\", headers: { \"Content-Type\": \"application/json\" }, body: JSON.stringify(parametro), }).then(response => response.text()).then(data => { retorno = data; }).catch(err => console.error(err)); return retorno;}; </script>\r\n        ";

    private static String Exec_Route(Route Data_Route){
        return Data_Route.root + Data_Route.route;
    };
    private static Void PrintHeader(Route Data_Route){
        // System.out.println("\n-----------------------------------------");
        // System.out.println(Data_Route.route);
        // System.out.println("Content-Type:" + Data_Route.type + "/" + Data_Route.extension);
        // System.out.println("Ruta:" + Data_Route.root + Data_Route.route);
        // System.out.println("Ruta Original:" + Data_Route.URL);        
        // System.out.println("Data POST:" + Data_Route.POST);
        // System.out.println("Data GET:" + Data_Route.GET);
        // System.out.println("-----------------------------------------\n");

        PrintWriter Output = Data_Route.dataPacket.Output;
        Output.println("HTTP/1.1 200 OK");
        Output.println("Content-Type:" + Data_Route.type + "/" + Data_Route.extension);
        Output.println("");
        return null;
    };
    public static Function<Route, Boolean> HTTP_JAVA = new Function<Route, Boolean>() {
        public Boolean apply(Route Data_Route) {
            if (Data_Route.POST.length() == 0) {return false;}

            PrintWriter Output = Data_Route.dataPacket.Output;
            String clase = Data_Route.root.replace("/", "") + "." + TextTools.txt_split(Data_Route.URL, ".")[0];
            String metodo = Data_Route.GET;
            String param = Data_Route.POST;
            
            App.Tools.Reflex.retorno retorno = App.Tools.Reflex.CallFunction(clase, metodo, param);
            if (retorno.error == false) {
                PrintHeader(Data_Route);
                Output.println(retorno.resultado);
            }
            return (!retorno.error);
        }
    };

    public static Function<Route, Boolean> HTTP_TEXT = new Function<Route, Boolean>() {
        public Boolean apply(Route Data_Route) {
            PrintWriter Output = Data_Route.dataPacket.Output;
            
            PrintHeader(Data_Route);
            String ReadFile = (String)computer.Read_File(Exec_Route(Data_Route));
            
            // if (Data_Route.extension.equals("html")) {
            // }else if (Data_Route.extension.equals("js")) {
            // }
            ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, ScriptJS);
            ReadFile = App.Methods.DocumentTools.ChangeCall_JS(ReadFile);

            Output.println(ReadFile);
            return true;
        }
    };

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
            
            if (Data_Route.extension.equals("php")) {
                ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, ScriptJS);
            }else if (Data_Route.extension.equals("js")) {
                ReadFile = App.Methods.DocumentTools.ChangeCall_JS(ReadFile);
            }
            Output.println(ReadFile);
            return true;
        }
    };

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


            // System.out.println("\nMet JAVA--------------->");
            // System.out.println(Data_Route.route);
            // System.out.println("Content-Type:" + Data_Route.type + "/" + Data_Route.extension);
            // System.out.println("Ruta:" + Data_Route.root + Data_Route.route);
            // System.out.println("Ruta Original:" + Data_Route.URL);        
            // System.out.println("Data POST:" + Data_Route.POST);
            // System.out.println("Data GET:" + Data_Route.GET);
            // System.out.println("-----------------------------------------\n");
            // System.out.println("Lamada: " + Data_Route.root.replace("/", "") + "." + Data_Route.GET);
