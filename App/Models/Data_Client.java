package App.Models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import App.Methods.TextTools;

public class Data_Client {
    public PrintWriter Output; // ¡Este es nuestro vaso para servir la salida!
    public OutputStream Output_Stream; // Aquí fluye nuestra salida
    public BufferedReader Input; // Aquí está nuestro vaso para beber la entrada
    public Socket client = null; // Nuestro cliente borracho

    public String DATA; // Entrada en bruto de la bebida
    public String URL; // La ruta obtenida de la bebida
    public String Method; // El método que se está manejando [GET POST]
    public String Head; // La cabeza del elemento (el encabezado)
    public String GET; // Datos obtenidos por GET (lo que recibimos antes de beber demasiado)
    public String POST; // Datos obtenidos por POST (lo que recibimos después de beber demasiado)

    public Data_Client(Socket Client) {
        this.client = Client;
        try {
            // Preparémonos para beber
            Output = new PrintWriter(client.getOutputStream(), true); // ¡Obtenemos nuestro vaso para servir la salida!
            Output_Stream = client.getOutputStream(); // Aquí fluye nuestra salida
            Input = new BufferedReader(new InputStreamReader(client.getInputStream())); // ¡Obtenemos nuestro vaso para beber la entrada!

            DATA = Input.readLine(); // ¡Leemos lo que hay en el vaso!

            Method = TextTools.Clean_METHOD_HTML(DATA); // Filtramos el método que se está manejando (GET o POST)
            URL = TextTools.Clean_TEXT_HTML(DATA); // Filtramos la ruta obtenida de la entrada (lo que queremos acceder)
            GET = TextTools.Clean_PARAMETER_HTML(DATA); // Filtramos los datos obtenidos por GET (lo que recibimos antes de beber demasiado)


            // ¡Es hora de beber la solicitud POST!
            StringBuilder headers = new StringBuilder(); // Aquí almacenaremos los encabezados de nuestra bebida
            int contentLength = 0; // Esta es la longitud del cuerpo de la bebida
            String line;
            while ((line = Input.readLine()) != null && line.length() > 0) {
                headers.append(line).append("\n"); // ¡Vamos bebiendo los encabezados!
                if (line.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(line.substring(15).trim()); // Tomamos la longitud del cuerpo de la bebida
                }
            }
            
            Head = headers.toString(); // Ahora tenemos la cabeza de la bebida

            // ¡Leamos el cuerpo de la solicitud POST mientras seguimos bebiendo!
            POST = getRequestBody(Input, contentLength);

        } catch (Exception e) {
            // Ups, parece que hemos bebido demasiado y hemos caído al suelo
        }
    }

    private static String getRequestBody(BufferedReader inputReader, int contentLength) throws IOException {
        StringBuilder requestBody = new StringBuilder(); // Aquí almacenaremos el cuerpo de nuestra bebida
        int read;
        char[] buffer = new char[contentLength];
        while ((read = inputReader.read(buffer)) != -1) {
            requestBody.append(buffer, 0, read); // ¡Vamos bebiendo el cuerpo de la bebida!
            if (requestBody.length() == contentLength) {
                break;
            }
        }
        return requestBody.toString(); // Devolvemos lo que hemos bebido, el cuerpo de la bebida
    }
}
