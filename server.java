
import java.io.*;
import java.net.*;
import App.Config.TypeMIME;
import App.Models.*;
import App.Methods.*;

public class server extends TypeMIME{
    public static ServerSocket MyServer;
    public static void main(String[] args) {
        try {
            MyServer = new ServerSocket(Pueto);
            while (true) {

                client = MyServer.accept();
                Output = new PrintWriter(client.getOutputStream(), true);
                Input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                outputStream = client.getOutputStream();
                
                String entrada = Input.readLine();
                Method = TextTools.Clean_METHOD_HTML(entrada);
                URL = TextTools.Clean_TEXT_HTML(entrada);
                parametros = TextTools.Clean_PARAMETER_HTML(entrada);
                

                // ---------------------------------------------------------------

                // Leer el cuerpo de la solicitud POST
                StringBuilder headers = new StringBuilder();
                int contentLength = 0;
                String line;
                while ((line = Input.readLine()) != null && line.length() > 0) {
                    headers.append(line).append("\n");
                    if (line.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(line.substring(15).trim());
                    }
                }

                // Leer el cuerpo de la solicitud POST
                requestBody = getRequestBody(Input, contentLength);

                // System.out.println("Headers:\n" + headers.toString());
                // System.out.println("=> " + entrada);
                // System.out.println("Método: " + Method);
                // System.out.println("Body: " + requestBody);
                // ---------------------------------------------------------------
                
                ObjFile AssocObj = Load_HTTP_File.assocFile(URL);

                Output.println("HTTP/1.1 200 OK");
                Output.println(AssocObj.type);
                Output.println("");
                AssocObj.ejecute.apply(AssocObj.Ruta);
                client.close();
            }
        } catch (Exception e) {
        }
    }

    private static String getRequestBody(BufferedReader inputReader, int contentLength) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        int read;
        char[] buffer = new char[contentLength];
        while ((read = inputReader.read(buffer)) != -1) {
            requestBody.append(buffer, 0, read);
            if (requestBody.length() == contentLength) {
                break;
            }
        }
        return requestBody.toString();
    }
}