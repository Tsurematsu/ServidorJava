package App.Tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class computer {
    // public static String Directory = "";
    
    public static String Read_File(String path) {
        // path = Directory + path;
        String retorno = "";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                retorno += myReader.nextLine();
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            retorno = null;
        }
        return retorno;
    }

    public static boolean Write_File(String path, String content) {
        // path = Directory + path;
        boolean retorno = true;
        try {
            FileWriter Fichero = new FileWriter(path);
            PrintWriter ProcesPrint = new PrintWriter(Fichero);
            ProcesPrint.println(content);
            Fichero.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            retorno = false;
        }
        return retorno;
    }    

    public static String Command_File(String path, String param) {
        // param = Directory + param;
        // System.out.println(path);
        String retorno = "";
        try {
            String comando = path + " " + param;
            Process proceso = Runtime.getRuntime().exec(comando);
            // System.out.println(comando);
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String Linea;
            while ((Linea = reader.readLine()) != null) {
                retorno += Linea + "\n";
            }
            // int res = proceso.waitFor();
            // System.out.println(res);
            // retorno = Linea;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno.substring(0, retorno.length()-1);
    }
}
