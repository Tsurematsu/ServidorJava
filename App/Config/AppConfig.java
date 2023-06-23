package App.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import App.Models.Route;
import App.Tools.computer;

public class AppConfig{
    // call(classe, methodo, parametro)
    // Configuraciones generales
    public static Integer Puerto = 89;
    public static String Default_HomePage = "index.html";
    public static String Default_ErrorPage = "error.html";
    public static Integer Default_Type = 0;

    // Script JS para llamadas asíncronas
    public static String JS_CallScript = "<script>\n"
        + "async function call(classe, methodo, parametro) {\n"
        + "    let retorno;\n"
        + "    await fetch(classe + \"?\" + methodo, {\n"
        + "        method: \"POST\",\n"
        + "        headers: {\n"
        + "            \"Content-Type\": \"application/json\"\n"
        + "        },\n"
        + "        body: JSON.stringify(parametro),\n"
        + "    })\n"
        + "    .then(response => response.text())\n"
        + "    .then(data => {\n"
        + "        retorno = data;\n"
        + "    })\n"
        + "    .catch(err => console.error(err));\n"
        + "    return retorno;\n"
        + "}\n"
        + "</script>";

    public static String JS_FormAction = "<script> \n "
        +   "        window.addEventListener(\"load\", () => { \n"
        +   "            let formularios = document.getElementsByTagName(\"form\"); \n"
        +   "            [...formularios].forEach(form => { \n"
        +   "                removeAllEventListeners(form); \n"
        +   "                form.setAttribute(\"data-value\", form.action); \n"
        +   "                form.addEventListener(\"submit\", async(event) => { \n"
        +   "                    form.action = form.getAttribute(\"data-value\"); \n"
        +   "                    if (event.submitter.getAttribute('type') != \"submit\") { \n"
        +   "                        event.preventDefault(); \n"
        +   "                    } \n"
        +   "                    if (event.submitter.getAttribute('action')) { \n"
        +   "                        form.action = event.submitter.getAttribute('action'); \n"
        +   "                    } else { \n"
        +   "                        event.preventDefault(); \n"
        +   "                    } \n"
        +   "                    const formData = new FormData(form); \n"
        +   "                    const body = {}; \n"
        +   "                    for (let [key, value] of formData.entries()) { \n"
        +   "                        body[key] = value; \n"
        +   "                    } \n"
        +   " \n"
        +   "                    if (event.submitter.getAttribute('type')) { \n"
        +   "                        if ((event.submitter.getAttribute('type') != \"submit\") && (event.submitter.getAttribute('type').split(\".\")[0] != \"java\")) { \n"
        +   "                            var functionToExecute = window[event.submitter.getAttribute('type')]; \n"
        +   "                            if (typeof functionToExecute === \"function\") { \n"
        +   "                                functionToExecute(form, body); \n"
        +   "                            } \n"
        +   "                        } else if (event.submitter.getAttribute('type').split(\".\")[0] == \"java\") { \n"
        +   "                            let retorno_W = await call(form.action.split(\"?\")[0], form.action.split(\"?\")[1], body); \n"
        +   "                            var functionToExecute = window[event.submitter.getAttribute('type').split(\".\")[1]]; \n"
        +   "                            if (typeof functionToExecute === \"function\") { \n"
        +   "                                functionToExecute(retorno_W, form, body); \n"
        +   "                            } else { \n"
        +   "                                console.error(\"La función [\" + event.submitter.getAttribute('type').split(\".\")[1] + \"()] no existe\"); \n"
        +   "                            } \n"
        +   "                        } \n"
        +   "                    } \n"
        +   "                }) \n"
        +   "            }) \n"
        +   "        }); \n"
        +   "</script> \n";



    public static String JS_RemoveEvents = "<script>\n      "
        + "function removeAllEventListeners(element) {\n"
        + "    const listeners = element.cloneNode().eventListenerList || [];\n"
        + "    listeners.forEach((listener) => {\n"
        + "        const {\n"
        + "            type,\n"
        + "            listener: eventListener,\n"
        + "            options\n"
        + "        } = listener;\n"
        + "        element.removeEventListener(type, eventListener, options);\n"
        + "    });\n"
        + "}\n"
        + "</script>";




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
            String clase = Data_Route.root.replace("/", "") + "." + Data_Route.URL.substring(0, Data_Route.URL.length() - Data_Route.URL.split("\\.")[Data_Route.URL.split("\\.").length-1].length()-1);
            String metodo = Data_Route.GET; 
            
            String param = Data_Route.POST;

            if (!App.Tools.JSON.isJson(param)) {
                try {param = App.Tools.JSON.JSON_GET(param);} catch (Exception e) {}
            }

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
            
            if (!Data_Route.route.equals(Default_ErrorPage)) {
                if (Data_Route.extension.equals("html") || Data_Route.extension.equals("js")) {
                    
                    ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, JS_CallScript);
                    ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, JS_RemoveEvents);
                    ReadFile = App.Methods.DocumentTools.AddBody(ReadFile, JS_FormAction);

                    ReadFile = App.Methods.DocumentTools.ChangeCall_JS(ReadFile);
                }
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

            if (!Data_Route.route.equals(Default_ErrorPage)) {
                if (Data_Route.extension.equals("php") || Data_Route.extension.equals("js")) {
                    
                    ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, JS_CallScript);
                    ReadFile = App.Methods.DocumentTools.AddHeader(ReadFile, JS_RemoveEvents);
                    ReadFile = App.Methods.DocumentTools.AddBody(ReadFile, JS_FormAction);

                    ReadFile = App.Methods.DocumentTools.ChangeCall_JS(ReadFile);
                }
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
