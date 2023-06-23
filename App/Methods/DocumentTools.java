package App.Methods;

public class DocumentTools {
    public static String AddHeader(String document, String value) {
        return document.replace("</head>", value + "\n</head>");
    }
    public static String AddBody(String document, String value) {
        return document.replace("</body>", value + "\n</body>");
    }

    public static String ChangeCall_JS(String document){
        String resultado = document;
        try {
            boolean salto = false;
            for (String elemento : document.split("Java\\.")) {
                if (salto) {
                    String item = elemento.split("\\}\\)")[0] + "}";
                    String clase = item.split("\\(\\{")[0];
                    String metodo = clase.split("\\.")[clase.split("\\.").length-1];
                    clase = clase.substring(0, clase.length() - metodo.length()-1);
                    String parametro = item.split("\\(")[1];
                    resultado = resultado.replace( "Java." + item + ")", "(() => call(\"" + clase + ".java\", \"" + metodo + "\", " + parametro + "))()");
                
                }else{
                    salto = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
}
