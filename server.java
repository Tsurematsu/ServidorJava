import java.io.File;
import java.net.ServerSocket;
import App.Config.TypeMIME;
import App.Methods.TextTools;
import App.Models.Data_Client;
import App.Models.ObjFile;
import App.Models.Route;
public class server extends TypeMIME {
    public static ServerSocket MyServer;

    public static void main(String[] args) {

        TypeMIME.LoadExtencion();
        try {
            //Inicia el servidor en el puerto configurado
            MyServer = new ServerSocket(Puerto);
            while (true) {
                // Aceptar una conexión de cliente entrante
                Data_Client Data_Package = new Data_Client(MyServer.accept());

                // Crear una ruta con los datos del cliente y las solicitudes GET y POST
                Route RUTA_TMP = new Route(Data_Package, "", Data_Package.GET, Data_Package.POST);
                
                // Variable booleana para verificar si se ejecutó correctamente la asociación del archivo
                boolean Event = false;

                if (Data_Package.URL.equals("")) {
                    // Si la URL está vacía, asignar la página de inicio predeterminada
                    RUTA_TMP.route = Default_HomePage;
                    RUTA_TMP.Item = Default_Type;
                    // Ejecutar la acción asociada al tipo MIME predeterminado
                    Event = TypeMIME.Extensiones.get(Default_Type).action.apply(RUTA_TMP);
                } else {
                    ObjFile AssocExtension = null;
                    String If_FileExist = null;
                    int count = 0;
                    
                    for (ObjFile element : TypeMIME.Extensiones) {
                        String[] urlSplit = TextTools.txt_split(Data_Package.URL, ".");
                        int lastIndex = urlSplit.length - 1;
                        if (urlSplit.length >= 2 && urlSplit[lastIndex].equals(element.extension)) {
                            // Si la extensión del archivo coincide con el elemento actual
                            AssocExtension = element;
                            RUTA_TMP.Item = count;
                            // Verificar si el archivo existe
                            if (new File(element.root + Data_Package.URL).exists()) {
                                If_FileExist = Data_Package.URL;
                            }
                            break;
                        }else if(urlSplit.length < 2){
                            String fileWithExtension = Data_Package.URL + "." + element.extension;
                            File temp1 = new File(element.root + fileWithExtension);
                            File temp2 = new File(element.root + Data_Package.URL);
                            if (temp1.exists() && temp1.isFile()) {
                                // Si el archivo con la extensión actual existe
                                AssocExtension = element;
                                RUTA_TMP.Item = count;
                                If_FileExist = fileWithExtension;
                                break;
                            }else if (temp1.isDirectory() || (temp2.exists() && temp2.isDirectory())) {
                                File temp_index_html = new File(element.root + Data_Package.URL + "/index.html");
                                File temp_index_php = new File(element.root + Data_Package.URL + "/index.php");
                                String result = null;
                                if (temp_index_html.exists() && temp_index_html.isFile()) {
                                    result = Data_Package.URL + "/index.html";
                                }else if (temp_index_php.exists() && temp_index_php.isFile()) {
                                    result = Data_Package.URL + "/index.php";
                                }
                                // Si el archivo con la extensión actual existe
                                AssocExtension = element;
                                RUTA_TMP.Item = count;
                                If_FileExist = result;
                                break;
                            }
                        }
                        count++;
                    }


                    if (AssocExtension != null && If_FileExist != null) {
                        // Si se encontró una extensión coincidente y el archivo existe
                        RUTA_TMP.route = If_FileExist;
                        // Ejecutar la acción asociada al tipo MIME correspondiente
                        Event = AssocExtension.action.apply(RUTA_TMP);
                    } else if (AssocExtension != null && AssocExtension.extension.equals("java")) {
                        // Si la extensión del archivo es "java"
                        RUTA_TMP.route = Data_Package.URL;
                        // Ejecutar la acción asociada al tipo MIME "java"
                        Event = AssocExtension.action.apply(RUTA_TMP);
                    } else {
                        // Si no se encontró una extensión coincidente o el archivo no existe
                        RUTA_TMP.route = Default_ErrorPage;
                        RUTA_TMP.Item = Default_Type;
                        // Ejecutar la acción asociada al tipo MIME predeterminado
                        Event = TypeMIME.Extensiones.get(Default_Type).action.apply(RUTA_TMP);
                    }
                }

                if (!Event) {
                    // Si no se pudo ejecutar correctamente la asociación del archivo
                    RUTA_TMP.route = Default_ErrorPage;
                    RUTA_TMP.Item = Default_Type;
                    // Ejecutar la acción asociada al tipo MIME predeterminado
                    Event = TypeMIME.Extensiones.get(Default_Type).action.apply(RUTA_TMP);
                }

                // Cerrar la conexión del cliente
                Data_Package.client.close();
            }
        } catch (Exception e) {
            // Manejo de excepciones
        }
    }
}
