package App.Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import App.Methods.TextTools;

public class Data_Client{
    public PrintWriter Output;
    public OutputStream Output_Stream;
    public BufferedReader Input;
    public Socket client=null;

    public String DATA; //Entrada en bruto de el input
    public String URL; //Ruta obtenida de la entrada
    public String Method; //El método que se esta manejando [GET POST]
    public String Head; //El header de el elemento
    public String GET; //Datos obtenidos por GET
    public String POST; //Datos obtenidos pos POST

    public Data_Client(Socket Client){
        this.client = Client;
        try {
            Output = new PrintWriter(client.getOutputStream(), true);
            Output_Stream = client.getOutputStream();
            Input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            DATA = Input.readLine();

            Method = TextTools.Clean_METHOD_HTML(DATA);
            URL = TextTools.Clean_TEXT_HTML(DATA);
            GET = TextTools.Clean_PARAMETER_HTML(DATA);

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
            
            // Leer el header
            Head = headers.toString();

            // Leer el cuerpo de la solicitud POST
            POST = getRequestBody(Input, contentLength);

        } catch (Exception e) {}
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
