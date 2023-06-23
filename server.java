import java.io.File;
import java.net.ServerSocket;

import App.Config.TypeMIME;
import App.Methods.TextTools;
import App.Models.Data_Client;
import App.Models.ObjFile;
import App.Models.Route;

public class server extends TypeMIME{
    public static ServerSocket MyServer;
    public static void main(String[] args) {
        TypeMIME.LoadExtencion();
        try {
            MyServer = new ServerSocket(Puerto);
            while (true) {
                Data_Client Data_Package = new Data_Client(MyServer.accept());

                Route RUTA_TMP = new Route(Data_Package, "", Data_Package.GET, Data_Package.POST);
                boolean Event = false;
                if (Data_Package.URL.equals("")) {
                    RUTA_TMP.route = Default_HomePage;
                    RUTA_TMP.Item = Default_Type;
                    Event = TypeMIME.Extensiones.get(Default_Type).action.apply(RUTA_TMP);
                }else{
                    ObjFile AssocExtension = null;
                    String If_FileExist = null;
                    Integer count = 0;
                    for (ObjFile element : TypeMIME.Extensiones) {
                        try {
                            if (TextTools.txt_split(Data_Package.URL, ".")[1].equals(element.extension)) {
                                AssocExtension = element;
                                RUTA_TMP.Item = count;
                                if (new File(element.root + Data_Package.URL).exists()) {If_FileExist = Data_Package.URL;}
                                break;
                            }
                        } catch (Exception e) {
                            if (new File(element.root + Data_Package.URL + "." + element.extension).exists()) {
                                AssocExtension = element;
                                RUTA_TMP.Item = count;
                                If_FileExist = Data_Package.URL + "." + element.extension;
                                break;
                            }
                        }
                        count++;
                    }
                    if (AssocExtension!=null && If_FileExist!=null) {
                        RUTA_TMP.route = If_FileExist;
                        Event = AssocExtension.action.apply(RUTA_TMP);
                    }else if(AssocExtension!=null && AssocExtension.extension.equals("java")){
                        RUTA_TMP.route = Data_Package.URL;
                        Event = AssocExtension.action.apply(RUTA_TMP);
                    }else{
                        RUTA_TMP.route = Default_ErrorPage;
                        RUTA_TMP.Item = Default_Type;
                        Event = TypeMIME.Extensiones.get(Default_Type).action.apply(RUTA_TMP);
                    }
                }
                if (Event==false) {
                    RUTA_TMP.route = Default_ErrorPage;
                    RUTA_TMP.Item = Default_Type;
                    Event = TypeMIME.Extensiones.get(Default_Type).action.apply(RUTA_TMP);
                }
                Data_Package.client.close();
            }
        } catch (Exception e) {
        }
    }
}