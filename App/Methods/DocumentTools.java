package App.Methods;

public class DocumentTools {
    // Método para agregar contenido en la sección <head> de un documento HTML
    public static String AddHeader(String document, String value) {
        return document.replace("</head>", value + "\n</head>");
    }
    
    // Método para agregar contenido al final de la sección <body> de un documento HTML
    public static String AddBody(String document, String value) {
        return document.replace("</body>", value + "\n</body>");
    }

    // Método para cambiar las llamadas a funciones JavaScript en un documento
    public static String ChangeCall_JS(String document){
        String resultado = document;
        try {
            boolean salto = false;
            
            // Divide el documento en elementos utilizando "Java." como separador
            for (String elemento : document.split("Java\\.")) {
                if (salto) {
                    // Obtiene el fragmento de código entre "(" y ")"
                    String item = elemento.split("\\}\\)")[0] + "}";
                    
                    // Divide la cadena para obtener el nombre de la clase y el método
                    String clase = item.split("\\(\\{")[0];
                    String metodo = clase.split("\\.")[clase.split("\\.").length-1];
                    clase = clase.substring(0, clase.length() - metodo.length()-1);
                    
                    // Obtiene los parámetros de la función
                    String parametro = item.split("\\(")[1];
                    
                    // Reemplaza la llamada a la función Java.XXX() por una llamada a la función "call()"
                    resultado = resultado.replace("Java." + item + ")", "(() => call(\"" + clase + ".java\", \"" + metodo + "\", " + parametro + "))()");
                
                } else {
                    salto = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return resultado;
    }

    public static String eliminarComentarios(String texto) {
        // Expresión regular para buscar y eliminar los comentarios tipo //
        String regex = "//.*$";
        
        // Eliminar los comentarios del texto utilizando replaceAll()
        String textoSinComentarios = texto.replaceAll(regex, "");

        return textoSinComentarios;
    }
}


// AddHeader: Este método recibe un documento HTML y un valor, y agrega ese valor al 
// final de la etiqueta </head> del documento. Luego devuelve el documento modificado.

// AddBody: Este método recibe un documento HTML y un valor, y agrega ese valor al final 
// de la etiqueta </body> del documento. Luego devuelve el documento modificado.

// ChangeCall_JS: Este método recibe un documento y realiza cambios en las llamadas a 
// funciones JavaScript que tienen el formato "Java.XXX()". 
// Reemplaza esas llamadas por una llamada a la función call(). 
// El método busca los fragmentos de código entre "(" y ")" y extrae el nombre de la clase, el método y los parámetros. 
// Luego, construye una nueva llamada a la función call() con los valores extraídos y reemplaza la llamada original en el documento. 
// Finalmente, devuelve el documento modificado.